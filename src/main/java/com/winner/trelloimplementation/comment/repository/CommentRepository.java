package com.winner.trelloimplementation.comment.repository;

import com.winner.trelloimplementation.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
