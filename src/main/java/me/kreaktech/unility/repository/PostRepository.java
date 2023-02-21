package me.kreaktech.unility.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import me.kreaktech.unility.entity.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
	List<Post> findByDateBetween(Date from, Date to);
	Optional<Post> findByTitle(String title);
}