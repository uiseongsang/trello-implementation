package com.winner.trelloimplementation.user.service;

import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import com.winner.trelloimplementation.user.dto.*;
import com.winner.trelloimplementation.user.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;

public interface UserService {
    /**
     * 로그인 API
     * @param loginRequestDto 회원가입 요청 정보
     */
    ResponseEntity<ApiResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response);

    /**
     * 로그아웃 API
     */
    ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException;


    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청 정보
     */
    ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto, Long boardNo);

    /**
     * 모든 유저 조회 API
     */
    List<User> getUserList();

    /**
     * 유저 프로필 조회 API
     * @param user 로그인한 유저 프로필 정보
     */
    ProfileResponseDto getProfile(User user);

    /**
     * 유저 프로필 수정 API
     * @param user 로그인한 유저 프로필 정보
     * @param profileRequestDto 수정할 유저 프로필 정보
     */
    ResponseEntity<ApiResponseDto> updateProfile(User user, ProfileRequestDto profileRequestDto);

    /**
     * 비밀번호 수정 API
     * @param user 로그인한 유저 프로필 정보
     * @param passwordRequestDto 이전 비밀번호와 새 비밀번호
     */
    ResponseEntity<ApiResponseDto> updatePassword(User user, PasswordRequestDto passwordRequestDto);

    /**
     * 회원탈퇴 API
     * @param user 로그인한 유저 프로필 정보
     * @param signoutRequestDto 본인확인을 위한 비밀번호 입력
     */
    ResponseEntity<ApiResponseDto> signout(User user, SignoutRequestDto signoutRequestDto, HttpServletResponse response, Authentication authResult) throws ServletException, IOException;
}
