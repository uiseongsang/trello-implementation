package com.winner.trelloimplementation.service;

import com.winner.trelloimplementation.dto.ApiResponseDto;
import com.winner.trelloimplementation.dto.SignupRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청 정보
     */

    ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto);
}
