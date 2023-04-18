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
import me.kreaktech.unility.entity.Activity;
import me.kreaktech.unility.exception.ErrorResponse;
import me.kreaktech.unility.service.ActivityServiceImpl;

@RestController
@AllArgsConstructor
@RequestMapping("/activities")
//UPDATE METHOD IS MISSING SHOULD WE IMPLEMENT IT?
public class ActivityController {
    
    @Autowired
    ActivityServiceImpl activityServiceImpl;

    @Operation(summary = "Gets an activity")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "activity retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "activity not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Activity> getActivity(@PathVariable Integer id) {
		return new ResponseEntity<>(activityServiceImpl.getActivityById(id), HttpStatus.OK);
	}

    @Operation(summary = "Create an activity")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "activity created successfully"),
			@ApiResponse(responseCode = "400", description = "activity failed to be created", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Activity> saveActivity(@Valid @RequestBody Activity activity) {
		return new ResponseEntity<>(activityServiceImpl.saveActivity(activity), HttpStatus.CREATED);
	}

    @Operation(summary = "Delete an activity given its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Activity deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Activity failed to be deleted", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteActivity(@PathVariable Integer id) {
		activityServiceImpl.deleteActivityById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

    @Operation(summary = "Gets all activities in a list")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Activities list retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Activities list not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Activity>> getAllActivities() {
		List<Activity> activities = activityServiceImpl.getAllActivities();
		return new ResponseEntity<>(activities, HttpStatus.OK);
	}
}
