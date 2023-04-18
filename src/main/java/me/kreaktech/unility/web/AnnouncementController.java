package me.kreaktech.unility.web;

import java.text.ParseException;
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
import me.kreaktech.unility.entity.Announcement;
import me.kreaktech.unility.exception.ErrorResponse;
import me.kreaktech.unility.service.AnnouncementServiceImpl;
import me.kreaktech.unility.utils.Utils;

@AllArgsConstructor
@RestController
@RequestMapping("/announcements")
public class AnnouncementController {

	@Autowired
	AnnouncementServiceImpl announcementService;

	@Operation(summary = "Gets an announcement by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Announcement retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Announcement not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Announcement> getAnnouncement(@PathVariable Integer id) {
		return new ResponseEntity<>(announcementService.getAnnouncementById(id), HttpStatus.OK);
	}

	@Operation(summary = "Gets an announcement by its title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Announcement retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Announcement not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Announcement> getAnnouncementByTitle(@RequestParam(value="title") String title) {
		return new ResponseEntity<>(announcementService.getByTitle(title), HttpStatus.OK);
	}

	@Operation(summary = "Creates an announcement")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Announcement created successfully"),
			@ApiResponse(responseCode = "400", description = "Announcement failed to be created", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Announcement> saveAnnouncement(@Valid @RequestBody Announcement announcement) {
		return new ResponseEntity<>(announcementService.saveAnnouncement(announcement), HttpStatus.CREATED);
	}

	@Operation(summary = "Deletes an announcement by its ID")
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
			@ApiResponse(responseCode = "200", description = "Announcements list retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Announcements list not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Announcement>> getAnnouncements() {
		List<Announcement> announcements = announcementService.getAllAnnouncements();
		return new ResponseEntity<>(announcements, HttpStatus.OK);
	}

	@Operation(summary = "Gets all announcements in a list by the help of dates")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Announcements list retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Announcements list not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Announcement>> getAnnouncementsByDate(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to) throws ParseException {
		List<Announcement> announcements = announcementService.getByDateBetweenAndDateLessThanEqual(Utils.stringToTimestamp(from), Utils.stringToTimestamp(to));
		return new ResponseEntity<>(announcements, HttpStatus.OK);
	}

}
