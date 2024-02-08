package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {

    PostDto createPost(PostDto postDto);
//    List<Post> findAllPosts();
    Page<Post> findAllPosts(int pageNo, int pageSize, String sortby, String sortDir);
    Post findById(Long id);
    PostDto updatePost(Long id, PostDto postRequestDto);
    void deletePost(Long id);

    List<PostDto> getPostsByCategory(Long categoryId);
}
