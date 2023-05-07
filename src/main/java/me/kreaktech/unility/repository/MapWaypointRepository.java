package me.kreaktech.unility.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kreaktech.unility.constants.Enum.MapWaypointType;
import me.kreaktech.unility.entity.MapWaypoint;

public interface MapWaypointRepository extends JpaRepository<MapWaypoint, Integer> {
	List<MapWaypoint> findByUniversityId(Integer universityId);

    List<MapWaypoint> findAllMapWaypointsByUniversityIdAndType(Integer universityId, MapWaypointType type);
}
