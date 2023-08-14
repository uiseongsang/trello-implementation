package com.winner.trelloimplementation.card.service;

import com.winner.trelloimplementation.card.dto.CardDetailResponseDto;
import com.winner.trelloimplementation.card.dto.CardRequestDto;
import com.winner.trelloimplementation.card.dto.CardResponseDto;
import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import com.winner.trelloimplementation.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface CardService {
    ResponseEntity<CardResponseDto> createCard(CardRequestDto cardRequestDto, Long columnNo, User user);

    ResponseEntity<ApiResponseDto> updateDeadline(CardRequestDto requestDto, Long cardNo, User user);

    ResponseEntity<ApiResponseDto> updateDescription(CardRequestDto requestDto, Long cardNo, User user);

    ResponseEntity<ApiResponseDto> deleteCard(Long cardNo, UserDetailsImpl userDetails, Long columnNo);

    ResponseEntity<ApiResponseDto> updateColor(CardRequestDto requestDto, Long cardNo, User user);

    ResponseEntity<ApiResponseDto> updateTitle(CardRequestDto requestDto, Long cardNo, User user);

    ResponseEntity<ApiResponseDto> switchColumn(Long columnNo, Long cardNo, User user, Long positionNo, Long boardNo);

    ResponseEntity<CardDetailResponseDto> getCard(Long cardNo);

    ResponseEntity<ApiResponseDto> updatePosition(Long columnNo, Long cardNo, UserDetailsImpl userDetails, Long changePositionNo);

    Card findCard(Long cardNo);

    Long getPosition(ColumnEntity column);

    Integer updateIsDeadline();
}
