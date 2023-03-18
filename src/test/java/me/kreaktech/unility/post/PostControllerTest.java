package me.kreaktech.unility.post;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.kreaktech.unility.entity.Post;
import me.kreaktech.unility.service.PostService;
import me.kreaktech.unility.web.PostController;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@MockBean
	private PostService postService;

	@Autowired
	private ObjectMapper objectMapper;

	private Post post;

	@BeforeEach
	public void init() {
		LocalDateTime postDateTime = LocalDateTime.now().minusHours(1);

		post = Post.builder()
				.id(1L)
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(postDateTime))
				.build();
	}

	@Test
	public void PostController_CreatePost_ReturnCreated() throws Exception {
		// Arrange
		given(postService.savePost(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

		// Act
		ResultActions response = mockmvc.perform(post("/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(this.post)));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(post.getTitle())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(post.getContent())));
	}

	@Test
	public void PostController_GetAllPosts_ReturnsPaginatedPosts() throws Exception {
		// Arrange
		Page<Post> posts = new PageImpl<>(List.of(post));
		when(postService.getPosts(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(posts);

		// Act
		ResultActions response = mockmvc.perform(get("/posts/all")
				.contentType(MediaType.APPLICATION_JSON)
				.param("pageNo", "0")
				.param("pageSize", "10"));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(posts.getContent().size())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", CoreMatchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title", CoreMatchers.is(post.getTitle())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].content", CoreMatchers.is(post.getContent())));
	}

	@Test
	public void PostController_GetPostById_ReturnPost() throws Exception {
		// Arrange
		when(postService.getPost(ArgumentMatchers.any())).thenReturn(post);

		// Act
		ResultActions response = mockmvc.perform(get("/posts/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(this.post)));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(post.getTitle())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(post.getContent())));
	}

	@Test
	public void PostController_DeletePost_ReturnsVoid() throws Exception {
		// Arrange
		doNothing().when(postService).deletePost(ArgumentMatchers.any());

		// Act
		ResultActions response = mockmvc.perform(delete("/posts/1")
				.contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}