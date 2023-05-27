package me.kreaktech.unility.web;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import me.kreaktech.unility.entity.UniversityFetch;
import me.kreaktech.unility.service.UniversityFetchServiceImpl;
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
import me.kreaktech.unility.entity.CafeteriaMenu;
import me.kreaktech.unility.exception.ErrorResponse;
import me.kreaktech.unility.service.CafeteriaMenuServiceImpl;

@AllArgsConstructor
@RestController
@RequestMapping("/cafeteria-menu")
public class CafeteriaMenuController {

	@Autowired
	CafeteriaMenuServiceImpl cafeteriaMenuService;
	@Autowired
	UniversityFetchServiceImpl universityFetchService;

	@Operation(summary = "Gets a cafeteria menu")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cafeteria menu retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Cafeteria menu not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CafeteriaMenu> getCafeteriaMenu(@PathVariable Integer id) {
		return new ResponseEntity<>(cafeteriaMenuService.getCafeteriaMenuById(id), HttpStatus.OK);
	}

	@Operation(summary = "Create a cafeteria menu")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cafeteria menu created successfully"),
			@ApiResponse(responseCode = "400", description = "Cafeteria menu failed to be created", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CafeteriaMenu> saveCafeteriaMenu(@Valid @RequestBody CafeteriaMenu cafeteriaMenu) {
		return new ResponseEntity<>(cafeteriaMenuService.saveCafeteriaMenu(cafeteriaMenu), HttpStatus.CREATED);
	}

	@Operation(summary = "Delete a cafeteria menu given its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Cafeteria menu deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Cafeteria menu failed to be deleted", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteCafeteriaMenu(@PathVariable Integer id) {
		cafeteriaMenuService.deleteCafeteriaMenuById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Deletes all cafeteria menus made by a certain university")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Cafeteria menus deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Cafeteria menus failed to be deleted", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/all/{universityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteAllCafeteriaMenusByUniversityId(@PathVariable Integer universityId) {
		cafeteriaMenuService.deleteAllCafeteriaMenusByUniversityId(universityId);
		UniversityFetch universityFetch = universityFetchService.getUniversityFetchById(universityId);
		universityFetch.setCafeteriaLastFetchDate(new Timestamp(0));
		universityFetchService.saveUniversityFetch(universityFetch);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Gets all cafeteria menu in a list")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cafeteria menu list retrieved successfully"),
	})
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CafeteriaMenu>> getCafeteriaMenu() {
		List<CafeteriaMenu> cafeteriaMenu = cafeteriaMenuService.getAllCafeteriaMenu();
		return new ResponseEntity<>(cafeteriaMenu, HttpStatus.OK);
	}

	@Operation(summary = "Gets all cafeteria menu in a list")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cafeteria menu list retrieved successfully"),
	})
	@GetMapping(value = "/all/{universityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CafeteriaMenu>> getCafeteriaMenuByUniversityId(@PathVariable Integer universityId) {
		List<CafeteriaMenu> cafeteriaMenu = cafeteriaMenuService.getAllCafeteriaMenuByUniversityId(universityId);
		return new ResponseEntity<>(cafeteriaMenu, HttpStatus.OK);
	}
}
