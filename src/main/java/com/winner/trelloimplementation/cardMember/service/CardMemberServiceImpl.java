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
        Card card = cardService.findCard(cardNo);

        // 보드에 속해있는 사람만 카드의 멤버로 추가 할 수 있으므로 테이블이 만들어지면 예외처리 작업 예정.

        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(()->{
            throw new IllegalArgumentException("존재하지 않는 회원입니다");
        });

        if (cardMemberRepository.findCardMemberByCardAndUserEmail(card, requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 멤버로 추가한 사람입니다.");
        }

        cardMemberRepository.save(new CardMember(user, card));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("멤버 추가 성공", 200));
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteMember(Long cardNo, CardMemberRequestDto requestDto) {
        Card card = cardService.findCard(cardNo);

        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(()->{
            throw new IllegalArgumentException("존재하지 않는 회원입니다");
        });

        CardMember cardMember = cardMemberRepository.findCardMemberByCardAndUserEmail(card, user.getEmail()).orElseThrow(()->{
            throw new IllegalArgumentException("멤버로 등록한 유저가 아닙니다.");
        });

        cardMemberRepository.delete(cardMember);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("멤버 삭제 성공", 200));
    }
}
