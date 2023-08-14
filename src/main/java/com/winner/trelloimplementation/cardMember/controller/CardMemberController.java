package com.winner.trelloimplementation.cardMember.controller;

import com.winner.trelloimplementation.cardMember.dto.CardMemberRequestDto;
import com.winner.trelloimplementation.cardMember.service.CardMemberServiceImpl;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CardMemberController {
    private final CardMemberServiceImpl cardMemberService;

    @PostMapping("/card/{cardNo}/member")
    public ResponseEntity<ApiResponseDto> addMember(@PathVariable Long cardNo, @RequestBody CardMemberRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardMemberService.addMember(cardNo, requestDto);
    }

    @DeleteMapping("/card/{cardNo}/member")
    public ResponseEntity<ApiResponseDto> deleteMember(@PathVariable Long cardNo, @RequestBody CardMemberRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardMemberService.deleteMember(cardNo, requestDto);
    }
}
