package com.winner.trelloimplementation.Comment.service;

import com.winner.trelloimplementation.Comment.dto.CommentRequestDto;
import com.winner.trelloimplementation.Comment.dto.CommentResponseDto;
import com.winner.trelloimplementation.Comment.entity.Comment;
import com.winner.trelloimplementation.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<CommentResponseDto> createComment(CommentRequestDto requestDto, Long cardNo, User user);

    ResponseEntity<CommentResponseDto> updateComment(CommentRequestDto requestDto, Long commentNo, User user);

    Comment getComment(Long commentNo);
}