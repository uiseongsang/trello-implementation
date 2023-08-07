package com.winner.trelloimplementation.user.service;

import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.user.dto.SignupRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청 정보
     */

    ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto);
}
