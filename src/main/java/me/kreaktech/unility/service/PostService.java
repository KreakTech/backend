package me.kreaktech.unility.service;

import org.springframework.data.domain.Page;

import me.kreaktech.unility.entity.Post;

public interface PostService {
    Post getPost(Long id);
    Post savePost(Post post);
    void deletePost(Long id);
    Page<Post> getPosts(int pageNumber, int pageSize);
}