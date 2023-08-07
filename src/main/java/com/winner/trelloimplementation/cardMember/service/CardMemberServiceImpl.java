package com.winner.trelloimplementation.cardMember.service;

import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.card.service.CardServiceImpl;
import com.winner.trelloimplementation.cardMember.dto.CardMemberRequestDto;
import com.winner.trelloimplementation.cardMember.entity.CardMember;
import com.winner.trelloimplementation.cardMember.repository.CardMemberRepository;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.user.entity.User;
import com.winner.trelloimplementation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardMemberServiceImpl implements CardMemberService {
    private final CardMemberRepository cardMemberRepository;

    private final CardServiceImpl cardService;

    private final UserRepository userRepository;
    @Override
    public ResponseEntity<ApiResponseDto> addMember(Long cardNo, CardMemberRequestDto requestDto) {
        Card card = cardService.getCard(cardNo);

        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(()->{
            throw new IllegalArgumentException("존재하지 않는 회원입니다");
        });

        cardMemberRepository.save(new CardMember(user, card));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("멤버 추가 성공", 200));
    };

    @Override
    public ResponseEntity<ApiResponseDto> deleteMember(Long cardNo, CardMemberRequestDto requestDto) {
        Card card = cardService.getCard(cardNo);

        cardMemberRepository.findCardMemberByCardAndUserEmail(card, requestDto.getEmail()).orElseThrow(()->{
            throw new IllegalArgumentException("유저가 존재하지 않습니다");
        });

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("멤버 삭제 성공", 200));
    }
}
