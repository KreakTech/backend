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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import me.kreaktech.unility.entity.NutritionContent;
import me.kreaktech.unility.exception.ErrorResponse;
import me.kreaktech.unility.service.NutritionContentServiceImpl;

@AllArgsConstructor
@RestController
@RequestMapping("/nutritionContent")
public class NutritionContentController {

	@Autowired
	NutritionContentServiceImpl nutritionContentService;

	@Operation(summary = "Gets a specific nutrition content")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Nutrition content retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Nutrition content menu not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NutritionContent> getNutritionContent(@PathVariable Integer id) {
		return new ResponseEntity<>(nutritionContentService.getNutritionContentById(id), HttpStatus.OK);
	}

	@Operation(summary = "Create a nutrition content")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Nutrition content created successfully"),
			@ApiResponse(responseCode = "400", description = "Nutrition content menu failed to be created", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NutritionContent> saveNutritionContent(
			@Valid @RequestBody NutritionContent nutritionContent) {
		return new ResponseEntity<>(nutritionContentService.saveNutritionContent(nutritionContent), HttpStatus.CREATED);
	}

	@Operation(summary = "Delete a nutrition content given its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Nutrition content deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Nutrition content failed to be deleted", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteNutritionContent(@PathVariable Integer id) {
		nutritionContentService.deleteNutritionContentById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
