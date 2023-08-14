package com.winner.trelloimplementation.comment.dto;

import com.winner.trelloimplementation.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String username;
    private String createdAt;
    private String modifiedAt;
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAtAsString();
        this.modifiedAt = comment.getModifiedAtAsString();
        this.username = comment.getUser().getUsername();
    }
}
