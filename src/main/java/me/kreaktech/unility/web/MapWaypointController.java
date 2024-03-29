package me.kreaktech.unility.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import me.kreaktech.unility.constants.Enum.MapWaypointType;
import me.kreaktech.unility.entity.MapWaypoint;
import me.kreaktech.unility.exception.ErrorResponse;
import me.kreaktech.unility.service.MapWaypointServiceImpl;

@AllArgsConstructor
@RestController
@RequestMapping("/map-waypoints")
public class MapWaypointController {

	@Autowired
	MapWaypointServiceImpl mapWaypointService;

	@Operation(summary = "Gets a waypoint")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "waypoint retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "waypoint not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MapWaypoint> getMapWaypoint(@PathVariable Integer id) {
		return new ResponseEntity<>(mapWaypointService.getMapWaypointById(id), HttpStatus.OK);
	}

	@Operation(summary = "Create a waypoint")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "waypoint created successfully"),
			@ApiResponse(responseCode = "400", description = "waypoint failed to be created", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MapWaypoint> saveMapwaypoint(@Valid @RequestBody MapWaypoint mapWaypoint) {
		return new ResponseEntity<>(mapWaypointService.saveMapWaypoint(mapWaypoint), HttpStatus.CREATED);
	}

	@Operation(summary = "Delete a waypoint given its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "waypoint deleted successfully"),
			@ApiResponse(responseCode = "400", description = "waypoint failed to be deleted", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteWaypoint(@PathVariable Integer id) {
		mapWaypointService.deleteMapWaypointById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Gets all waypoint in a list")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "waypoint list retrieved successfully"),
	})
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MapWaypoint>> getMapWaypoints() {
		List<MapWaypoint> mapWaypoints = mapWaypointService.getAllMapWaypoints();
		return new ResponseEntity<>(mapWaypoints, HttpStatus.OK);
	}

	@Operation(summary = "Gets all waypoint in a list of a specific university")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "waypoint list retrieved successfully"),
	})
	@GetMapping(value = "/all/{universityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MapWaypoint>> getMapWaypointsByUniversityId(@PathVariable Integer universityId) {
		List<MapWaypoint> mapWaypoints = mapWaypointService.getAllMapWaypointsByUniversityId(universityId);
		return new ResponseEntity<>(mapWaypoints, HttpStatus.OK);
	}

	@Operation(summary = "Gets all waypoint in a list of a specific university and type")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "waypoint list retrieved successfully"),
	})
	@GetMapping(value = "/all/{universityId}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MapWaypoint>> getMapWaypointByUniversityIdAndType(@PathVariable Integer universityId, @PathVariable MapWaypointType type) {
		List<MapWaypoint> busMapWaypoints = mapWaypointService.getAllMapWaypointsByUniversityIdAndType(universityId, type);
		return new ResponseEntity<>(busMapWaypoints, HttpStatus.OK);
	}
}
