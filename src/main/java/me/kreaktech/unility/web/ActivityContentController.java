package me.kreaktech.unility.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import me.kreaktech.unility.entity.ActivityContent;
import me.kreaktech.unility.exception.ErrorResponse;
import me.kreaktech.unility.service.ActivityContentServiceImpl;

@RestController
@AllArgsConstructor
@RequestMapping("/activity-contents")
public class ActivityContentController {

	@Autowired
	ActivityContentServiceImpl activityContentServiceImpl;

	@Operation(summary = "Gets an activity content")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Activity content retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Activity content not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ActivityContent> getActivityContent(@PathVariable Integer id) {
		return new ResponseEntity<>(activityContentServiceImpl.getActivityContentById(id), HttpStatus.OK);
	}

	@Operation(summary = "Create an activity content")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Activity content created successfully"),
			@ApiResponse(responseCode = "400", description = "Activity content failed to be created", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ActivityContent> saveActivityContent(@Valid @RequestBody ActivityContent activityContent) {
		return new ResponseEntity<>(activityContentServiceImpl.saveActivityContent(activityContent),
				HttpStatus.CREATED);
	}

	@Operation(summary = "Delete an activity content given its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Activity content deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Activity content failed to be deleted", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteActivityContent(@PathVariable Integer id) {
		activityContentServiceImpl.deleteActivityContentById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
