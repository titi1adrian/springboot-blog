package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    Comment createComment(Long postId, Comment comment);
    Comment findById(Long postId, Long commentId);
    List<Comment> findCommentsByPostId(Long postId);
    Comment updateComment(Long postId, Long commentId, Comment comment);
    void deleteComment(Long postId, Long commentId);

}
