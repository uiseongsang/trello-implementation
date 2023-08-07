package com.winner.trelloimplementation.Comment.service;

import com.winner.trelloimplementation.Comment.dto.CommentRequestDto;
import com.winner.trelloimplementation.Comment.dto.CommentResponseDto;
import com.winner.trelloimplementation.Comment.entity.Comment;
import com.winner.trelloimplementation.Comment.repository.CommentRepository;
import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.card.service.CardServiceImpl;
import com.winner.trelloimplementation.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CardServiceImpl cardService;
    private final CommentRepository commentRepository;
    @Override
    public ResponseEntity<CommentResponseDto> createComment(CommentRequestDto requestDto, Long cardNo, User user) {
        Card card = cardService.getCard(cardNo);
        Comment comment = new Comment(requestDto, user, card);
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponseDto(comment));
    }
}
