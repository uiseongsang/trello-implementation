package com.winner.trelloimplementation.Comment.repository;

import com.winner.trelloimplementation.Comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
