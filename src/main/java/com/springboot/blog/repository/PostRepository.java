package com.springboot.blog.repository;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    //give all CRUD API for Post + sorting and pagination

    List<Post> findByCategoryId(Long categoryId);
}
