package com.winner.trelloimplementation.comment.service;

import com.winner.trelloimplementation.comment.dto.CommentRequestDto;
import com.winner.trelloimplementation.comment.dto.CommentResponseDto;
import com.winner.trelloimplementation.comment.entity.Comment;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<CommentResponseDto> createComment(CommentRequestDto requestDto, Long cardNo, User user);

    ResponseEntity<CommentResponseDto> updateComment(User user, Long commentNo, CommentRequestDto requestDto);

    ResponseEntity<ApiResponseDto> deleteComment(User user, Long commentNo);

    Comment getComment(Long commentNo);

}