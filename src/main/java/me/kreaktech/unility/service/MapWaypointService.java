package me.kreaktech.unility.service;

import java.util.List;

import me.kreaktech.unility.constants.Enum.MapWaypointType;
import me.kreaktech.unility.entity.MapWaypoint;

public interface MapWaypointService {

    MapWaypoint getMapWaypointById(Integer id);

    MapWaypoint saveMapWaypoint(MapWaypoint mapWaypoint);

    void deleteMapWaypointById(Integer id);

    List<MapWaypoint> getAllMapWaypoints();

    List<MapWaypoint> getAllMapWaypointsByUniversityId(Integer universityId);

    List<MapWaypoint> getAllMapWaypointsByUniversityIdAndType(Integer universityId, MapWaypointType type);
}