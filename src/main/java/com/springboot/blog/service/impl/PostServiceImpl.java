package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    public final PostRepository postRepository;
    public final ModelMapper modelMapper;
    public final CategoryRepository categoryRepository;

    //we can skip Autowired annotation

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(postDto.getCategoryId())));
        Post post = modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        return modelMapper.map(postRepository.save(post), PostDto.class);
    }

    @Override
    public Page<Post> findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return postRepository.findAll(pageable);
    }

    @Override
    public Post findById(Long id) {
        Optional<Post> result = postRepository.findById(id);
        if(result.isPresent())
            return result.get();
        else
            throw new ResourceNotFoundException("Post", "id", String.valueOf(id));
    }

    @Override
    public PostDto updatePost(Long id, PostDto postRequestDto) {
        Category category = categoryRepository.findById(postRequestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(postRequestDto.getCategoryId())));

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));

        post.setTitle(postRequestDto.getTitle());
        post.setDescription(postRequestDto.getDescription());
        post.setContent(postRequestDto.getContent());
        post.setCategory(category);
        Post postResponse = postRepository.save(post);
        return modelMapper.map(postResponse, PostDto.class);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(categoryId)));
        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }


}
