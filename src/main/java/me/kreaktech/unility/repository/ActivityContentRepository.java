package me.kreaktech.unility.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kreaktech.unility.entity.ActivityContent;

public interface ActivityContentRepository extends JpaRepository<ActivityContent, Integer> {
}
