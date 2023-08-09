package com.winner.trelloimplementation.board.service;

import com.winner.trelloimplementation.board.dto.*;
import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.board.entity.BoardMember;
import com.winner.trelloimplementation.board.entity.MemberRoleEnum;
import com.winner.trelloimplementation.board.repository.BoardMemberRepository;
import com.winner.trelloimplementation.board.repository.BoardRepository;
import com.winner.trelloimplementation.common.redis.RedisUtil;
import com.winner.trelloimplementation.user.entity.User;
import com.winner.trelloimplementation.user.repository.UserRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardMemberRepository boardMemberRepository;

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") // Base64 Encode 한 SecretKey
    private String owner;
    private final RedisUtil redisUtil;

    private final long EXPIRE_TIME = 1 * 60 * 1000L; // 5분

    public BoardServiceImpl (BoardRepository boardRepository, UserRepository userRepository,
                             BoardMemberRepository boardMemberRepository, JavaMailSender javaMailSender,
                             RedisUtil redisUtil) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.boardMemberRepository = boardMemberRepository;
        this.javaMailSender = javaMailSender;
        this.redisUtil = redisUtil;
    }

    @Override
    public void createBoard(User user, CreateBoardRequestDto createBoardRequestDto) {

        userRepository.findById(user.getId()).orElseThrow(
                () -> new NullPointerException("회원이 존재하지 않습니다.")
        );

        // createBoard 받아온 정보로 Board 생성
        Board board = new Board(createBoardRequestDto.getTitle(), createBoardRequestDto.getDescription(), createBoardRequestDto.getColor());

        BoardMember member = new BoardMember(user, board, MemberRoleEnum.ADMIN);

        board.addMember(member);

        board.addUser(user);

        boardRepository.save(board);

        boardMemberRepository.save(member);

    }

    @Override
    @Transactional
    public void modifyBoard(User user, ModifyBoardRequestDto modifyBoardRequestDto, Long boardNo) {

        Board board = boardRepository.findById(boardNo).orElseThrow(
                () -> new NullPointerException("선택한 보드가 존재하지 않습니다.")
        );

        if (!board.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("게시글을 작성한 유저가 아닙니다.");
        }

        board.update(modifyBoardRequestDto);
    }

    @Override
    @Transactional
    public void deleteBoard(User user, Long boardNo) {

        Board board = boardRepository.findById(boardNo).orElseThrow(
                () -> new NullPointerException("선택한 보드가 존재하지 않습니다.")
        );

        if (!board.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("게시글을 작성한 유저가 아닙니다.");
        }

        boardRepository.delete(board);
    }

    @Override
    public GetOneBoardResponseDto getOneBoard(User user, Long boardNo) {

        BoardMember boardMember = boardMemberRepository.findByUserIdAndBoardsId(user.getId(), boardNo);

        if (boardMember == null) {
            throw new NullPointerException("해당 보드에 유저가 가입되어 있지 않거나, 해당 보드가 존재하지 않습니다.");
        }

        Board board = boardRepository.findById(boardNo).orElseThrow(
                () -> new NullPointerException("선택한 보드가 존재하지 않습니다.")
        );

        return new GetOneBoardResponseDto(board);
    }

    @Override
    public List<GetBoardListResponseDto> getBoardList(User user) {

        List<BoardMember> boardMembers = boardMemberRepository.findByUserId(user.getId());

        List<GetBoardListResponseDto> getBoardListResponseDtoList = new ArrayList<>();
        for (BoardMember boardMember : boardMembers) {
            GetBoardListResponseDto getBoardListResponseDto = new GetBoardListResponseDto(boardMember.getBoards());
            getBoardListResponseDtoList.add(getBoardListResponseDto);
        }

        return getBoardListResponseDtoList;
    }

    @Override
    public void sendEmailToInviteUser(Long boardNo, EmailRequestDto emailRequestDto) throws MessagingException, UnsupportedEncodingException {

        String emailLink = "<a href='http://localhost:8080/api/boards'>";
        String signupLink = "<a href='http://localhost:8080/api/user/signup'>";
        // 만약 이미 가입한 유저라면 그냥 보드 멤버에 추가
        User user = userRepository.findByEmail(emailRequestDto.getEmail()).orElseThrow(
                () -> new NullPointerException("해당 유저가 존재하지 않습니다.")
        );


        Board board = boardRepository.findById(boardNo).orElseThrow(
                () -> new NullPointerException("선택한 보드가 존재하지 않습니다.")
        );

        BoardMember boardMember = new BoardMember(user, board, MemberRoleEnum.MEMBER);

        boardMemberRepository.save(boardMember);


        // 받는 사람 이메일 주소
        String receiverMail = emailRequestDto.getEmail();
        // 이메일 보내기 위해 사용되는 값 (HTML 형식)
        MimeMessage message = javaMailSender.createMimeMessage();
        // 이메일 받는 사람 추가
        message.addRecipients(Message.RecipientType.TO, receiverMail);
        // 이메일 제목
        message.setSubject("Trello 회원가입 이메일 인증");
        // 초대된 보드 이름
        String boardName = boardRepository.findById(boardNo).get().getTitle();
        // 사용할 이메일 내용 (html 형식 - 링크 보냄)
        String body = "<div>"
                + "<h1> 안녕하세요. Trello 관리자입니다</h1>"
                + "<br>"
                + "<p>아래 링크를 클릭하면 초대된 보드로 이동합니다.<p>"
                + "<p>회원이 아니시라면 회원 가입부터 해주세요.<p>"
                + emailLink + "회원 로그인" + "</a>"
                + signupLink  + "회원 가입" + "</a>"
                + "</div>";
        // 실제로 보낼 이메일 내용 저장
        message.setText(body, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소 (내 구글 아이디), 보내는 사람 이름 (관리자)
        message.setFrom(new InternetAddress(owner, "ADMIN"));
        // 이메일 보내기
        javaMailSender.send(message);
        // redis에 이메일 저장
        String code = emailRequestDto.getEmail();
        // 만료 시간 설정 (5분)
        redisUtil.setDataExpire(code, emailRequestDto.getEmail(), EXPIRE_TIME);
    }

//    @Override
//    public RedirectView checkUserInfo(Long boardNo, String email) {
//        // 해당 보드가 존재하는지부터 확인
//        Board board = boardRepository.findById(boardNo).orElseThrow(
//                () -> new NullPointerException("선택한 보드가 존재하지 않습니다.")
//        );
//
//        User user = userRepository.findByEmail(email);
//
//        if (user != null) {
//
//            String checkRedis = getUserIdByEmail(email);
//
//            BoardMember member = new BoardMember(user, board, MemberRoleEnum.ADMIN);
//            boardMemberRepository.save(member);
//            return new RedirectView("/api/user/login");
//        }
//        else {
//            // 회원 가입 하도록 하고 멤버 추가
//            return null;
//        }
//    }

    public String getUserByEmail(String code) {

        String email = redisUtil.getData(code);

        if (email == null) {
            throw new IllegalArgumentException("유효 기간이 만료된 링크입니다.");
        }

        return email;
    }
}
