package com.winner.trelloimplementation.common.aop;

import com.winner.trelloimplementation.comment.entity.Comment;
import com.winner.trelloimplementation.comment.service.CommentService;
import com.winner.trelloimplementation.comment.service.CommentServiceImpl;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommentValidationAspect {

    private final CommentService commentService;

    public CommentValidationAspect(CommentService commentService) {
        this.commentService = commentService;
    }
    @Pointcut("execution(public * com.winner.trelloimplementation.comment.controller.CommentController.deleteComment(..)) || " +
            "execution(public * com.winner.trelloimplementation.comment.controller.CommentController.updateComment(..))")
    public void commentMethods() {
    }

    @Before("commentMethods() && args(commentNo, user, ..)")
    public void validateCommentOwner(UserDetailsImpl user, Long commentNo) {
        Comment comment = commentService.getComment(commentNo);
        System.out.println(comment.getUser().getUsername());
        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("댓글 작성한 회원이 아닙니다.");
        }
    }
}
