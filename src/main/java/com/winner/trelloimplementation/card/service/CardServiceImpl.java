package com.winner.trelloimplementation.card.service;

import com.winner.trelloimplementation.card.dto.CardDetailResponseDto;
import com.winner.trelloimplementation.card.dto.CardRequestDto;
import com.winner.trelloimplementation.card.dto.CardResponseDto;
import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.card.repository.CardRepository;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import com.winner.trelloimplementation.column.service.ColumnServiceImpl;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import com.winner.trelloimplementation.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final ColumnServiceImpl columnService;

    @Override
    public ResponseEntity<CardResponseDto> createCard(CardRequestDto requestDto, Long columnNo, User user) {
        ColumnEntity column = columnService.findColumnEntity(columnNo);

        Long position = getPosition(column);

        Card card = new Card(requestDto, user, column, position);
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

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("카드 제목 적용 완료", 200));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> updateColumn(CardRequestDto requestDto, Long cardNo, User user) {
        ColumnEntity column = columnService.findColumnEntity(requestDto.getColumn());

        Long position = getPosition(column);

        Card card = findCard(cardNo);

        card.setPosition(position);
        card.setColumn(column);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("카드 이동 완료", 200));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteCard(Long cardNo, UserDetailsImpl userDetails, Long columnNo) {
        ColumnEntity column = columnService.findColumnEntity(columnNo);
        Optional<List<Card>> cardsOptional = cardRepository.findByColumnEntity(column);

        Card card = findCard(cardNo);

        cardRepository.delete(card);

        if (cardsOptional.isPresent()) {
            List<Card> cards = cardsOptional.get();
            for (Card cardInfo : cards) {
                if (cardInfo.getPosition() > card.getPosition()) {
                    cardInfo.setPosition(cardInfo.getPosition() - 1);
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("카드 삭제 완료", 200));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> updatePosition(Long columnNo, Long cardNo, UserDetailsImpl userDetails, Long changeCardNo) {
        ColumnEntity column = columnService.findColumnEntity(columnNo);

        Card currentCard = cardRepository.findByColumnEntityAndId(column, cardNo).orElseThrow(()->{
           throw new IllegalArgumentException("바꿀 카드가 존재하지 않습니다.");
        });

        Card changeCard = cardRepository.findByColumnEntityAndId(column, changeCardNo).orElseThrow(()->{
            throw new IllegalArgumentException("바뀔 카드가 존재하지 않습니다.");
        });

        Long currentCardPosition = currentCard.getPosition();
        Long changeCardPosition = changeCard.getPosition();

        currentCard.setPosition(changeCardPosition);
        changeCard.setPosition(currentCardPosition);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("카드 바꿈 완료", 200));
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

    @Override
    public Long getPosition(ColumnEntity column) {
        Optional<List<Card>> cards = cardRepository.findByColumnEntity(column);

        long position;

        if(cards.isPresent()) {
            List<Card> cardList = cards.get();

            position = cardList.size();
        } else {
            position = 0L;
        }
        return position;
    }
}
