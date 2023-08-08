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

public interface UserService {
    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청 정보
     */

    ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto, Long boardNo);

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
