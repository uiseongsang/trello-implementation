package com.winner.trelloimplementation.card.service;

import com.winner.trelloimplementation.card.dto.CardDetailResponseDto;
import com.winner.trelloimplementation.card.dto.CardRequestDto;
import com.winner.trelloimplementation.card.dto.CardResponseDto;
import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.card.repository.CardRepository;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import com.winner.trelloimplementation.column.service.ColumnService;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import com.winner.trelloimplementation.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final ColumnService columnService;

    @Override
    public ResponseEntity<CardResponseDto> createCard(CardRequestDto requestDto, Long columnNo, User user) {
        ColumnEntity column = columnService.findColumnEntity(columnNo);

        Card card = new Card(requestDto, user, column);
        cardRepository.save(card);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CardResponseDto(card));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> updateDeadline(CardRequestDto requestDto, Long cardNo, User user) {
        Card card = findCard(cardNo);

        card.setDeadline(requestDto.getDeadline());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("마감 설정 완료", 200));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> updateDescription(CardRequestDto requestDto, Long cardNo, User user) {
        Card card = findCard(cardNo);

        card.setDescription(requestDto.getDescription());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("본문 작성 완료", 200));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> updateColor(CardRequestDto requestDto, Long cardNo, User user) {
        Card card = findCard(cardNo);

        card.setColor(requestDto.getColor());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("카드 색상 적용 완료", 200));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> updateTitle(CardRequestDto requestDto, Long cardNo, User user) {
        Card card = findCard(cardNo);

        card.setTitle(requestDto.getTitle());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("카드 색상 적용 완료", 200));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> updateColumn(CardRequestDto requestDto, Long cardNo, User user) {
        ColumnEntity column = columnService.findColumnEntity(requestDto.getColumn());

        Card card = findCard(cardNo);

        card.setColumn(column);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("카드 이동 완료", 200));
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteCard(Long cardNo, UserDetailsImpl userDetails) {
        Card card = findCard(cardNo);

        cardRepository.delete(card);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("카드 삭제 완료", 200));
    }

    @Override
    public Card findCard(Long cardNo) {
        return cardRepository.findById(cardNo).orElseThrow(()->{
           throw new IllegalArgumentException("카드를 찾을 수 없습니다.");
        });
    }

    @Override
    public ResponseEntity<CardDetailResponseDto> getCard(Long cardNo) {
        Card card = findCard(cardNo);

        return ResponseEntity.status(HttpStatus.OK).body(new CardDetailResponseDto(card));
    }
}
