package me.kreaktech.unility.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/health")
public class HealthController {

	@Operation(summary = "Checks if the server is up")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Server is up"),
	})
	@GetMapping(value = "")
	public ResponseEntity<String> checkHealth() {
		return new ResponseEntity<>("Server is healthy", HttpStatus.OK);
	}

}
