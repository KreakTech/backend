package me.kreaktech.unility.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import me.kreaktech.unility.constants.Enum;

import me.kreaktech.unility.entity.UniversityFetch;
import me.kreaktech.unility.exception.ErrorResponse;

import me.kreaktech.unility.service.UniversityFetchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/university-fetch")
public class UniversityFetchController {

	@Autowired
	private UniversityFetchServiceImpl universityFetchService;

	@Operation(summary = "Gets a university fetch")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "University fetch retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "University fetch not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UniversityFetch> getUniversityFetch(@PathVariable Integer id) {
		return new ResponseEntity<>(universityFetchService.getUniversityFetchById(id), HttpStatus.OK);
	}

	@Operation(summary = "Get university fetch data by university name and language")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully fetched university data"),
			@ApiResponse(responseCode = "400", description = "University fetch entry failed to be fetched", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UniversityFetch> getAnnouncementsLastFetchMD5(@RequestParam String universityName,
			@RequestParam Enum.Language language) {
		System.out.println(universityName);
		return new ResponseEntity<>(universityFetchService.getUniversityFetchByUniversityNameAndLanguage(universityName, language),
				HttpStatus.OK);
	}

	@Operation(summary = "Create a university fetch row")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "University fetch created successfully"),
			@ApiResponse(responseCode = "400", description = "University fetch failed to be created", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UniversityFetch> createUniversityFetch(@Valid @RequestBody UniversityFetch universityFetch) {
		return new ResponseEntity<>(universityFetchService.saveUniversityFetch(universityFetch), HttpStatus.CREATED);
	}

	@Operation(summary = "Delete a university fetch given its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "University fetch deleted successfully"),
			@ApiResponse(responseCode = "400", description = "University fetch failed to be deleted", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteUniversityFetch(@PathVariable Integer id) {
		universityFetchService.deleteUniversityFetchById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
