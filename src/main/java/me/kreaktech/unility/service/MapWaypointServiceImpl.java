package me.kreaktech.unility.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import me.kreaktech.unility.entity.MapWaypoint;
import me.kreaktech.unility.repository.MapWaypointRepository;
import me.kreaktech.unility.utils.Utils;

@AllArgsConstructor
@Service
public class MapWaypointServiceImpl implements MapWaypointService {

	MapWaypointRepository mapWaypointRepository;

	@Override
	public MapWaypoint getMapWaypointById(Integer id) {
		return Utils.unwrap(mapWaypointRepository.findById(id), id);
	}

	@Override
	public MapWaypoint saveMapWaypointStop(MapWaypoint mapWaypoint) {
		return mapWaypointRepository.save(mapWaypoint);
	}

	@Override
	public void deleteMapwaypointById(Integer id) {
		mapWaypointRepository.deleteById(id);
		return;
	}

	@Override
	public List<MapWaypoint> getAllMapWaypoints() {
		return mapWaypointRepository.findAll();
	}

	@Override
	public List<MapWaypoint> getAllMapWaypointsByUniversityId(Integer id) {
		return mapWaypointRepository.findByUniversityId(id);
	}
}