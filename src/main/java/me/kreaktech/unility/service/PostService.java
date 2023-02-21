package me.kreaktech.unility.service;

import java.util.List;

import me.kreaktech.unility.entity.Post;

public interface PostService {
    Post getPost(Long id);
    Post savePost(Post post);
    void deletePost(Long id);
    List<Post> getPosts();
}