package com.winner.trelloimplementation.cardMember.service;

import com.winner.trelloimplementation.cardMember.dto.CardMemberRequestDto;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface CardMemberService {
    ResponseEntity<ApiResponseDto> addMember(Long cardNo, CardMemberRequestDto requestDto);

    ResponseEntity<ApiResponseDto> deleteMember(Long cardNo, CardMemberRequestDto requestDto);
}
