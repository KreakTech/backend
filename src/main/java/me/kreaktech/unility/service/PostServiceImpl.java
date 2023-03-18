package me.kreaktech.unility.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public Page<Post> getPosts(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return postRepository.findAll(pageable);
	}

	static Post unwrapPost(Optional<Post> entity, Long id) {
		if (entity.isPresent())
			return entity.get();
		else
			throw new EntityNotFoundException(id, Post.class);
	}

}