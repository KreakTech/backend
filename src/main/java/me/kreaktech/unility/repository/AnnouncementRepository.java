package me.kreaktech.unility.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import me.kreaktech.unility.entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
	@Query("SELECT a FROM Announcement a WHERE a.date >= :from AND a.date <= :to")
	List<Announcement> findByDateBetweenAndDateLessThanEqual(@Param("from") Timestamp from, @Param("to") Timestamp to);
	Optional<Announcement> findByTitle(String title);
}