package com.winner.trelloimplementation.card.service;

import com.winner.trelloimplementation.card.dto.CardRequestDto;
import com.winner.trelloimplementation.card.dto.CardResponseDto;
import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface CardService {
    ResponseEntity<CardResponseDto> createCard(CardRequestDto cardRequestDto, Long columnNo, User user);

    ResponseEntity<ApiResponseDto> updateDeadline(CardRequestDto requestDto, Long cardNo, User user);

    ResponseEntity<ApiResponseDto> updateDescription(CardRequestDto requestDto, Long cardNo, User user);

    Card getCard(Long cardNo);

}
