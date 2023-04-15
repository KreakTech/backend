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
import me.kreaktech.unility.entity.Announcement;
import me.kreaktech.unility.exception.ErrorResponse;
import me.kreaktech.unility.service.AnnouncementServiceImpl;

@AllArgsConstructor
@RestController
@RequestMapping("/announcements")
public class AnnouncementController {

	@Autowired
	AnnouncementServiceImpl announcementService;

	@Operation(summary = "Gets a announcement")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "announcement retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "announcement not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Announcement> getAnnouncement(@PathVariable Integer id) {
		return new ResponseEntity<>(announcementService.getAnnouncementById(id), HttpStatus.OK);
	}

	@Operation(summary = "Create a announcement")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "announcement created successfully"),
			@ApiResponse(responseCode = "400", description = "announcement failed to be created", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Announcement> saveAnnouncement(@Valid @RequestBody Announcement announcement) {
		return new ResponseEntity<>(announcementService.saveAnnouncement(announcement), HttpStatus.CREATED);
	}

	@Operation(summary = "Delete a announcement given its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Announcement deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Announcement failed to be deleted", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteAnnouncement(@PathVariable Integer id) {
		announcementService.deleteAnnouncementById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Gets all announcements in a list")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "announcements list retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "announcements list not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Announcement>> getAnnouncements() {
		List<Announcement> announcements = announcementService.getAllAnnouncements();
		return new ResponseEntity<>(announcements, HttpStatus.OK);
	}

}
