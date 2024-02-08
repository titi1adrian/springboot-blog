package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private static CommentRepository commentRepository;
    private static PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment createComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long postId, Long commentId) {
        return findCommentByPostIdHelper(postId, commentId);
//        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
//        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));
//        if (comment.getPost().getId() == post.getId())
//            return comment;
//        else
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "The comment is not linked to selected post");
    }

    @Override
    public List<Comment> findCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment updateComment(Long postId, Long commentId, Comment commentNew) {
        Comment commentOld = findCommentByPostIdHelper(postId, commentId);
        commentOld.setName(commentNew.getName());
        commentOld.setBody(commentNew.getBody());
        commentOld.setEmail(commentNew.getEmail());
        return commentRepository.save(commentOld);

//        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
//        Comment commentOld = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));
//        if (commentOld.getPost().getId() == post.getId()) {
//            commentOld.setName(commentNew.getName());
//            commentOld.setBody(commentNew.getBody());
//            commentOld.setEmail(commentNew.getEmail());
//            return commentRepository.save(commentOld);
//        }else
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "The comment is not linked to the selected post");
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        commentRepository.delete(findCommentByPostIdHelper(postId, commentId));
    }

    public static Comment findCommentByPostIdHelper (Long postId, Long commentId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));
        if (comment.getPost().getId() == post.getId())
            return comment;
        else
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "The comment is not linked to selected post");
    }

}
