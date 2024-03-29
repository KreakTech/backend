package me.kreaktech.unility.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import me.kreaktech.unility.constants.Enum.MapWaypointType;
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
	public MapWaypoint saveMapWaypoint(MapWaypoint mapWaypoint) {
		return mapWaypointRepository.save(mapWaypoint);
	}

	@Override
	public void deleteMapWaypointById(Integer id) {
		mapWaypointRepository.deleteById(id);
    }

	@Override
	public List<MapWaypoint> getAllMapWaypoints() {
		return mapWaypointRepository.findAll();
	}

	@Override
	public List<MapWaypoint> getAllMapWaypointsByUniversityId(Integer universityId) {
		return mapWaypointRepository.findByUniversityId(universityId);
	}

	@Override
	public List<MapWaypoint> getAllMapWaypointsByUniversityIdAndType(Integer universityId, MapWaypointType type) {
		return mapWaypointRepository.findAllMapWaypointsByUniversityIdAndType(universityId, type);
	}

}