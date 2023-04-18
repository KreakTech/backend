package me.kreaktech.unility.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kreaktech.unility.entity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

}
