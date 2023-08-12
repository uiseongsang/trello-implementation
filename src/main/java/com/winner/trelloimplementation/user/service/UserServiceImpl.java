package com.winner.trelloimplementation.user.service;

import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.board.entity.BoardMember;
import com.winner.trelloimplementation.board.entity.MemberRoleEnum;
import com.winner.trelloimplementation.board.repository.BoardMemberRepository;
import com.winner.trelloimplementation.board.repository.BoardRepository;
import com.winner.trelloimplementation.board.service.BoardServiceImpl;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.jwt.JwtUtil;
import com.winner.trelloimplementation.user.dto.*;
import com.winner.trelloimplementation.user.entity.User;
import com.winner.trelloimplementation.user.entity.UserRoleEnum;
import com.winner.trelloimplementation.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardServiceImpl boardServiceImpl;
    private final BoardRepository boardRepository;
    private final BoardMemberRepository boardMemberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<ApiResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        log.info("login 시도");
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(),loginRequestDto.getRole()));
        try {
            jwtUtil.addJwtToCookie(response.getHeader(JwtUtil.AUTHORIZATION_HEADER), response);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        log.info(response.getHeader(JwtUtil.AUTHORIZATION_HEADER));
        return ResponseEntity.ok().body(new ApiResponseDto("로그인에 성공했습니다.", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        jwtUtil.deleteCookie(response, authResult);
        return ResponseEntity.status(200).body(new ApiResponseDto("로그아웃 성공", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto, Long boardNo) {
        if (requestDto.getPassword().contains(requestDto.getUsername())) {
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함되어 있습니다.");
        }
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        String introduction = requestDto.getIntroduction();
        UserRoleEnum role = UserRoleEnum.USER;

        // 회원 중복 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        User user = new User(username, nickname, password, email, introduction, role);
        userRepository.save(user);

        if (boardNo != null) {

            Board board = boardRepository.findById(boardNo).orElseThrow(
                    () -> new NullPointerException("해당 보드가 존재하지 않습니다.")
            );

            if (boardServiceImpl.getUserByEmail(email) != null) {
                BoardMember boardMember = new BoardMember(user, board, MemberRoleEnum.MEMBER);
                boardMemberRepository.save(boardMember);
            }
        }

        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @Override
    public ProfileResponseDto getProfile(User user) {
        return new ProfileResponseDto(user);
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateProfile(User user, ProfileRequestDto profileRequestDto) {
        user.setNickname(profileRequestDto.getNickname());
        user.setIntroduction(profileRequestDto.getIntroduction());
        userRepository.save(user);
        return ResponseEntity.status(200).body(new ApiResponseDto("프로필 수정 성공", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ApiResponseDto> updatePassword(User user, PasswordRequestDto passwordRequestDto) {
        if (passwordEncoder.matches(passwordRequestDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordRequestDto.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.status(200).body(new ApiResponseDto("비밀번호 수정 성공", HttpStatus.OK.value()));
        } else {
            throw new IllegalArgumentException("이전 비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> signout(User user, SignoutRequestDto signoutRequestDto, HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        if (user.getUsername().startsWith("kakao")) {
            if (signoutRequestDto.getPassword().equals(user.getEmail())) {
                userRepository.delete(user);
                jwtUtil.deleteCookie(response, authResult);
                return ResponseEntity.status(200).body(new ApiResponseDto("회원탈퇴 성공",HttpStatus.OK.value()));
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            if (passwordEncoder.matches(signoutRequestDto.getPassword(), user.getPassword())) {
                userRepository.delete(user);
                jwtUtil.deleteCookie(response, authResult);
                return ResponseEntity.status(200).body(new ApiResponseDto("회원탈퇴 성공", HttpStatus.OK.value()));
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        }
    }
}
