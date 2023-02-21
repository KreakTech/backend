package me.kreaktech.unility.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import me.kreaktech.unility.entity.Post;
import me.kreaktech.unility.exception.EntityNotFoundException;
import me.kreaktech.unility.repository.PostRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;

	@Override
	public Post getPost(Long id) {
		Optional<Post> post = postRepository.findById(id);
		return unwrapPost(post, id);
	}

	@Override
	public Post savePost(Post post) {
		return postRepository.save(post);
	}

	@Override
	public void deletePost(Long id) {
		postRepository.deleteById(id);
	}

	@Override
	public List<Post> getPosts() {
		return (List<Post>) postRepository.findAll();
	}

	static Post unwrapPost(Optional<Post> entity, Long id) {
		if (entity.isPresent())
			return entity.get();
		else
			throw new EntityNotFoundException(id, Post.class);
	}

}