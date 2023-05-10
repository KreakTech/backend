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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.exception.ErrorResponse;
import me.kreaktech.unility.service.UniversityServiceImpl;

@AllArgsConstructor
@RestController
@RequestMapping("/universities")
public class UniversityController {

	@Autowired
	UniversityServiceImpl universityService;

	@Operation(summary = "Gets a university")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "University retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "University not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<University> getUniversity(@PathVariable Integer id) {
		return new ResponseEntity<>(universityService.getUniversity(id), HttpStatus.OK);
	}

	@Operation(summary = "Gets a university by its name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "University retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "University not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<University> getUniversity(@RequestParam(value = "name") String name) {
		return new ResponseEntity<>(universityService.getUniversityByName(name), HttpStatus.OK);
	}

	@Operation(summary = "Create a university")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "University created successfully"),
			@ApiResponse(responseCode = "400", description = "University failed to be created", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<University> createUniversity(@Valid @RequestBody University university) {
		return new ResponseEntity<>(universityService.createUniversity(university), HttpStatus.CREATED);
	}

	@Operation(summary = "Delete a university given its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "University deleted successfully"),
			@ApiResponse(responseCode = "400", description = "University failed to be deleted", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteUniversity(@PathVariable Integer id) {
		universityService.deleteUniversity(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Gets all universities")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Universities retrieved successfully"),
	})
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<University>> getUniversities() {
		List<University> universities = universityService.getUniversities();
		return new ResponseEntity<>(universities, HttpStatus.OK);
	}
}
