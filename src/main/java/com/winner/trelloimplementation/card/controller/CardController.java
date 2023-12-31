package com.winner.trelloimplementation.card.controller;

import com.winner.trelloimplementation.card.dto.CardDetailResponseDto;
import com.winner.trelloimplementation.card.dto.CardRequestDto;
import com.winner.trelloimplementation.card.dto.CardResponseDto;
import com.winner.trelloimplementation.card.service.CardServiceImpl;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CardController {
    private final CardServiceImpl cardServiceImpl;

    @PostMapping("/column/{columnNo}/card")
    public ResponseEntity<CardResponseDto> createCard(@RequestBody CardRequestDto requestDto, @PathVariable Long columnNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardServiceImpl.createCard(requestDto, columnNo, userDetails.getUser());
    }

    @GetMapping("/card/{cardNo}")
    public ResponseEntity<CardDetailResponseDto> getCard(@PathVariable Long cardNo) {
        return cardServiceImpl.getCard(cardNo);
    }

    @PatchMapping("/card/{cardNo}/title")
    public ResponseEntity<ApiResponseDto> updateTitle(@PathVariable Long cardNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CardRequestDto requestDto) {
        return cardServiceImpl.updateTitle(requestDto, cardNo, userDetails.getUser());
    }

    @PatchMapping("/card/{cardNo}/deadline")
    public ResponseEntity<ApiResponseDto> updateDeadline(@PathVariable Long cardNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CardRequestDto requestDto) {
        return cardServiceImpl.updateDeadline(requestDto, cardNo, userDetails.getUser());
    }

    @PatchMapping("/card/{cardNo}/description")
    public ResponseEntity<ApiResponseDto> updateDescription(@PathVariable Long cardNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CardRequestDto requestDto) {
        return cardServiceImpl.updateDescription(requestDto, cardNo, userDetails.getUser());
    }

    @PatchMapping("/card/{cardNo}/color")
    public ResponseEntity<ApiResponseDto> updateColor(@PathVariable Long cardNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CardRequestDto requestDto) {
        return cardServiceImpl.updateColor(requestDto, cardNo, userDetails.getUser());
    }

    @PatchMapping("/card/{cardNo}/board/{boardNo}/change-column/{columnNo}/change-position/{positionNo}")
    public ResponseEntity<ApiResponseDto> switchColumn(@PathVariable Long cardNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long columnNo, @PathVariable Long positionNo, @PathVariable Long boardNo) {
        return cardServiceImpl.switchColumn(columnNo, cardNo, userDetails.getUser(), positionNo, boardNo);
    }

    @PatchMapping("/column/{columnNo}/card/{cardNo}/change-position/{changePositionNo}")
    public ResponseEntity<ApiResponseDto> updatePosition(@PathVariable Long columnNo, @PathVariable Long cardNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long changePositionNo) {
        return cardServiceImpl.updatePosition(columnNo,cardNo,userDetails, changePositionNo);
    }

    @DeleteMapping("/column/{columnNo}/card/{cardNo}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long cardNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long columnNo) {
        return cardServiceImpl.deleteCard(cardNo, userDetails, columnNo);
    }
}
