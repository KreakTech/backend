package me.kreaktech.unility.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kreaktech.unility.entity.Activity;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findAllActivitiesByUniversityId(Integer universityId);
}
