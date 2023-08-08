package com.winner.trelloimplementation.comment.controller;

import com.winner.trelloimplementation.comment.dto.CommentRequestDto;
import com.winner.trelloimplementation.comment.dto.CommentResponseDto;
import com.winner.trelloimplementation.comment.service.CommentServiceImpl;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
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
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(userDetails.getUser(), commentNo, requestDto);
    }

    @DeleteMapping("/comment/{commentNo}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(userDetails.getUser(), commentNo);
    }
}
