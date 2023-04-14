package me.kreaktech.unility.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kreaktech.unility.entity.BusStop;

public interface BusStopRepository extends JpaRepository<BusStop, Integer> {
	List<BusStop> findByUniversityId(int universityId);
}
