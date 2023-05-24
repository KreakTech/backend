package me.kreaktech.unility.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kreaktech.unility.entity.CafeteriaMenu;
import org.springframework.transaction.annotation.Transactional;

public interface CafeteriaMenuRepository extends JpaRepository<CafeteriaMenu, Integer> {
	List<CafeteriaMenu> findByUniversityId(Integer universityId);
	@Transactional
	void deleteAllByUniversityId(Integer universityId);
}
