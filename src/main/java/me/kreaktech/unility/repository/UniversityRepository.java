package me.kreaktech.unility.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kreaktech.unility.entity.University;

public interface UniversityRepository extends JpaRepository<University, Integer> {
	Optional<University> findByName(String name);
}