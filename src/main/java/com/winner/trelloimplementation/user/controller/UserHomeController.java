package com.winner.trelloimplementation.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.winner.trelloimplementation.common.jwt.JwtUtil;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import com.winner.trelloimplementation.user.dto.ProfileResponseDto;
import com.winner.trelloimplementation.user.service.KakaoService;
import com.winner.trelloimplementation.user.userlog.UserLog;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserHomeController {

    private final KakaoService kakaoService;
    private final JwtUtil jwtUtil;

    @GetMapping("/sign")
    public String signPage() { return "sign"; }

    @GetMapping("/my-page")
    public String myPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(userDetails.getUser());
        // model 필요한 데이터 담아서 반환
        model.addAttribute("users", profileResponseDto);
        return "my-page";
    }

    @GetMapping("/user/profile")
    public String userInfoUpdate() { return "userInfoUpdate"; }

    @GetMapping("/user/profile/password")
    public String passwordUpdate() { return "passwordUpdate"; }

    @GetMapping("/user/sign-out")
    public String signoutPage() { return "sign-out"; }

    @GetMapping("/user/logview")
    public String logView() {
        return UserLog.fileReader();
    }

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
}
