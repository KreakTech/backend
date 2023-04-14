package me.kreaktech.unility.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kreaktech.unility.entity.University;

public interface UniversityRepository extends JpaRepository<University, Integer> {
	University findByName(String name);
}