package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private ModelMapper modelMapper;
    private CommentService commentService;

    @Autowired
    public CommentController(ModelMapper modelMapper, CommentService commentService) {
        super();
        this.modelMapper = modelMapper;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") Long postId, @RequestBody CommentDto commentDto){
        Comment comment = commentService.createComment(postId, modelMapper.map(commentDto, Comment.class));
        CommentDto commentResponse = modelMapper.map(comment, CommentDto.class);
        return new ResponseEntity<CommentDto>(commentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> findCommentById(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId")Long commentId){
        return new ResponseEntity<CommentDto>(modelMapper.map(commentService.findById(postId, commentId), CommentDto.class), HttpStatus.OK);
    }

    @GetMapping
    public List<CommentDto> findCommentsByPostId(@PathVariable(name = "postId") Long postId){
        List<Comment> commentList = commentService.findCommentsByPostId(postId);
        return commentList.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment (@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId")Long commentId, @RequestBody CommentDto commentDto){
        Comment comment = commentService.updateComment(postId, commentId, modelMapper.map(commentDto, Comment.class));
        return ResponseEntity.ok().body(modelMapper.map(comment, CommentDto.class));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId")Long commentId){
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok().body("The comment was delete!");
    }


}
