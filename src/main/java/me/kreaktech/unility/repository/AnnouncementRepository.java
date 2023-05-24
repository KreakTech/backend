package me.kreaktech.unility.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import me.kreaktech.unility.entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
	@Query("SELECT a FROM Announcement a WHERE a.date >= :from AND a.date <= :to AND a.university.id = :universityId")
	List<Announcement> findByDateBetweenAndDateLessThanEqualAndUniversityId(@Param("from") Timestamp from, @Param("to") Timestamp to, @Param("universityId") Integer universityId);

	Optional<Announcement> findByTitle(String title);
	List<Announcement> findByUniversityId(Integer universityId);
	void deleteAllByUniversityId(Integer universityId);
}