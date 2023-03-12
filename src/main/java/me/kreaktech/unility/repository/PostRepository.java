package me.kreaktech.unility.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.kreaktech.unility.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	default List<Post> findByDateBetween(Timestamp from, Timestamp to) {
		return this.findByDateBetweenAndDateLessThanEqual(from, to);
	}
	
	
    @Query("SELECT p FROM Post p WHERE p.date BETWEEN ?1 AND ?2 AND p.date <= ?2")
	List<Post> findByDateBetweenAndDateLessThanEqual(Timestamp from, Timestamp to);
	Optional<Post> findByTitle(String title);
}