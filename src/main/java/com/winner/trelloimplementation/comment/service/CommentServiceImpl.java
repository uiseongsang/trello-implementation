package com.winner.trelloimplementation.comment.service;

import com.winner.trelloimplementation.comment.dto.CommentRequestDto;
import com.winner.trelloimplementation.comment.dto.CommentResponseDto;
import com.winner.trelloimplementation.comment.entity.Comment;
import com.winner.trelloimplementation.comment.repository.CommentRepository;
import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.card.service.CardServiceImpl;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CardServiceImpl cardService;
    private final CommentRepository commentRepository;
    @Override
    public ResponseEntity<CommentResponseDto> createComment(CommentRequestDto requestDto, Long cardNo, User user) {
        Card card = cardService.findCard(cardNo);
        Comment comment = new Comment(requestDto, user, card);
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponseDto(comment));
    }

    @Override
    @Transactional
    public ResponseEntity<CommentResponseDto> updateComment(User user, Long commentNo, CommentRequestDto requestDto) {
        Comment comment = getComment(commentNo);
        comment.setContent(requestDto.getContent());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponseDto(comment));
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteComment(User user, Long commentNo) {
        Comment comment = getComment(commentNo);

        commentRepository.delete(comment);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("댓글 삭제 완료", 200));
    }

    @Override
    public Comment getComment(Long commentNo) {
        return commentRepository.findById(commentNo).orElseThrow(()->{
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        });
    };
}
