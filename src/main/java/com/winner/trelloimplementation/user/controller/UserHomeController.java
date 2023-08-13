package com.winner.trelloimplementation.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.winner.trelloimplementation.board.controller.BoardController;
import com.winner.trelloimplementation.board.entity.BoardMember;
import com.winner.trelloimplementation.board.repository.BoardMemberRepository;
import com.winner.trelloimplementation.common.jwt.JwtUtil;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import com.winner.trelloimplementation.user.dto.ProfileResponseDto;
import com.winner.trelloimplementation.user.repository.UserRepository;
import com.winner.trelloimplementation.user.service.KakaoService;
import com.winner.trelloimplementation.user.userlog.UserLog;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserHomeController {

    private final KakaoService kakaoService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BoardMemberRepository boardMemberRepository;
    private final BoardController boardController;

    @GetMapping("/sign")
    public String signPage() { return "sign"; }

    @GetMapping("/my-page")
    public String myPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(userDetails.getUser());

        // model 필요한 데이터 담아서 반환
        model.addAttribute("users", profileResponseDto);
        model.addAttribute("oldlogs", UserLog.fullUserLog(userDetails.getUser()));
        model.addAttribute("logs", UserLog.fileReader(userDetails.getUser()));
        return "my-page";
    }

    @GetMapping("/user/profile")
    public String userInfoUpdate() { return "userInfoUpdate"; }

    @GetMapping("/user/profile/password")
    public String passwordUpdate() { return "passwordUpdate"; }

    @GetMapping("/user/sign-out")
    public String signoutPage() { return "sign-out"; }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드 Service 전달 후 인증 처리 및 JWT 반환
        String token = kakaoService.kakaoLogin(code);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        try {
            jwtUtil.addJwtToCookie(response.getHeader(JwtUtil.AUTHORIZATION_HEADER), response);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/web";
    }

    // 다른 보드멤버의 개인정보를 담은 마이페이지 불러오기 메서드
    @GetMapping("/board/{boardNo}/anotherUser/{userid}")
    public String getAnotherMemberInfo(@PathVariable Long boardNo, @PathVariable Long userid, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        // 현재 로그인한 사용자의 보드멤버 정보
        BoardMember boardMember = boardMemberRepository.findByUserIdAndBoardsId(userDetails.getUser().getId(), boardNo).orElseThrow(() ->
                new NullPointerException("존재하지 않는 사용자입니다."));
        // 현재 로그인한 사용자가 Admin 인지 확인
        if (boardMember.getRole().equals("ROLE_ADMIN") && boardMember.getBoards().getId().equals(boardNo)) {
            // 프로필을 보고 싶은 대상 사용자의 보드멤버 정보
            BoardMember targetBoardMember = boardMemberRepository.findByUserIdAndBoardsId(userid, boardNo).orElseThrow(() ->
                    new NullPointerException("존재하지 않는 사용자입니다."));;
            // 서로 같은 보드를 사용 중인지 확인
            if (targetBoardMember.getBoards().getId().equals(boardNo)) {
                ProfileResponseDto profileResponseDto = new ProfileResponseDto(targetBoardMember.getUser());
                ArrayList<String> temp = UserLog.fileReader(userDetails.getUser());
                ArrayList<String> memberLog = new ArrayList<>();

                for (int i = 0; i < temp.size(); i ++) {
                    if (temp.get(i).startsWith(userDetails.getUser().getUsername())) {
                        memberLog.add(temp.get(i));
                    }
                }

                model.addAttribute("users", profileResponseDto);
                model.addAttribute("logs", memberLog);
                return "my-page";
            } else {
                throw new IllegalArgumentException("동작을 수행할 권한이 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("동작을 수행할 권한이 없습니다.");
        }
    }
}
