package me.kreaktech.unility.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import me.kreaktech.unility.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	default List<Post> findByDateBetween(Timestamp from, Timestamp to) {
		return this.findByDateBetweenAndDateLessThanEqual(from, to);
	}
	
	
	@Query("SELECT p FROM Post p WHERE p.date >= :from AND p.date <= :to")
	List<Post> findByDateBetweenAndDateLessThanEqual(@Param("from") Timestamp from, @Param("to") Timestamp to);
	Optional<Post> findByTitle(String title);
	Page<Post> findAll(Pageable pageable);
}