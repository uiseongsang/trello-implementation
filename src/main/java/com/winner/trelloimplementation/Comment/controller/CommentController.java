package com.winner.trelloimplementation.Comment.controller;

import com.winner.trelloimplementation.Comment.dto.CommentRequestDto;
import com.winner.trelloimplementation.Comment.dto.CommentResponseDto;
import com.winner.trelloimplementation.Comment.service.CommentServiceImpl;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentServiceImpl commentService;

    @PostMapping("/card/{cardNo}/comment")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto, @PathVariable Long cardNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(requestDto, cardNo, userDetails.getUser());
    }

    @PatchMapping("/comment/{commentNo}")
    public ResponseEntity<CommentResponseDto> updateComment(@RequestBody CommentRequestDto requestDto, @PathVariable Long commentNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(requestDto, commentNo, userDetails.getUser());
    }
}
