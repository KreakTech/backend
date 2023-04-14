package me.kreaktech.unility.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kreaktech.unility.entity.CafeteriaMenu;

public interface CafeteriaMenuRepository extends JpaRepository<CafeteriaMenu, Integer> {
	List<CafeteriaMenu> findByUniversityId(int universityId);
}
