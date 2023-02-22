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
import me.kreaktech.unility.entity.Post;
import me.kreaktech.unility.exception.ErrorResponse;
import me.kreaktech.unility.service.PostService;

@AllArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	PostService postService;

	@Operation(summary = "Gets a post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Post retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Post> getPost(@PathVariable Long id) {
		return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
	}

	@Operation(summary = "Create a post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Post created successfully"),
			@ApiResponse(responseCode = "400", description = "Post failed to be created", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Post> savePost(@Valid @RequestBody Post post) {
		return new ResponseEntity<>(postService.savePost(post), HttpStatus.CREATED);
	}

	@Operation(summary = "Delete a post given its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Post deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Post failed to be created", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Gets all posts")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
	})
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Post>> getPosts() {
		return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
	}

}
