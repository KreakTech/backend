package me.kreaktech.unility.service;

import java.util.List;

import me.kreaktech.unility.entity.MapWaypoint;

public interface MapWaypointService {

	MapWaypoint getMapWaypointById(Integer id);
    MapWaypoint saveMapWaypointStop(MapWaypoint mapWaypoint);
    void deleteMapwaypointById(Integer id);
    List<MapWaypoint> getAllMapWaypoints();
    List<MapWaypoint> getAllMapWaypointsByUniversityId(Integer id);
}