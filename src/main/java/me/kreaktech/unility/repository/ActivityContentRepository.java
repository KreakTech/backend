package me.kreaktech.unility.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kreaktech.unility.entity.ActivityContent;

public interface ActivityContentRepository extends JpaRepository<ActivityContent, Integer> {
    Optional<ActivityContent> findActivityContentByTitle(String title);
}
