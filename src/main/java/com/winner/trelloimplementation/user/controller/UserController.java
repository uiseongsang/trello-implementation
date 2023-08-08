package com.winner.trelloimplementation.user.controller;

import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.JwtAuthenticationFilter;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import com.winner.trelloimplementation.user.dto.*;
import com.winner.trelloimplementation.user.service.UserServiceImpl;
import jakarta.annotation.Nullable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @GetMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        jwtAuthenticationFilter.deleteAuthentication(response, authResult);
        return ResponseEntity.status(200).body(new ApiResponseDto("로그아웃 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto, @Nullable @RequestParam("boardNo") Long boardNo) {
        return userServiceImpl.signup(requestDto, boardNo);
    }

    @GetMapping("/info")
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userServiceImpl.getProfile(userDetails.getUser());
    }

    @Transactional
    @PutMapping("/info")
    public ResponseEntity<ApiResponseDto> updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto profileRequestDto) {
        return userServiceImpl.updateProfile(userDetails.getUser(), profileRequestDto);
    }

    @Transactional
    @PutMapping("/info/password")
    public ResponseEntity<ApiResponseDto> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody PasswordRequestDto passwordRequestDto) {
        return userServiceImpl.updatePassword(userDetails.getUser(), passwordRequestDto);
    }

    @Transactional
    @DeleteMapping("/signout")
    public ResponseEntity<ApiResponseDto> signout(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody SignoutRequestDto signoutRequestDto,
                                                  HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        return userServiceImpl.signout(userDetails.getUser(), signoutRequestDto, response, authResult);
    }
}
