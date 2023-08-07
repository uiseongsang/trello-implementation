package com.winner.trelloimplementation.controller;

import com.winner.trelloimplementation.dto.ApiResponseDto;
import com.winner.trelloimplementation.dto.SignupRequestDto;
import com.winner.trelloimplementation.security.JwtAuthenticationFilter;
import com.winner.trelloimplementation.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @RequestMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        jwtAuthenticationFilter.deleteAuthentication(response, authResult);
        return ResponseEntity.status(200).body(new ApiResponseDto("로그아웃 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid SignupRequestDto requestDto) {
        return userServiceImpl.signup(requestDto);
    }
}