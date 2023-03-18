package me.kreaktech.unility.post;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import me.kreaktech.unility.entity.Post;
import me.kreaktech.unility.repository.PostRepository;
import me.kreaktech.unility.service.PostServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private PostServiceImpl postService;

	@Test
	public void PostService_CreatePost_ReturnsPost() {
		// Arrange
		LocalDateTime postDateTime = LocalDateTime.now().minusHours(1);

		Post post = Post.builder()
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(postDateTime))
				.build();

		// Act
		when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);
		Post savedPost = postService.savePost(post);

		// Assert
		Assertions.assertThat(savedPost).isNotNull();
		Assertions.assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
		Assertions.assertThat(savedPost.getContent()).isEqualTo(post.getContent());
		Assertions.assertThat(savedPost.getDate()).isEqualTo(post.getDate());
	}

	@Test
	public void PostService_GetAllPosts_ReturnsPaginatedPosts() {
		// Arrange
		@SuppressWarnings("unchecked")
		Page<Post> posts = Mockito.mock(Page.class);
		PageRequest pageable = PageRequest.of(0, 10);

		// Act
		when(postRepository.findAll(pageable)).thenReturn(posts);
		Page<Post> savedPost = postService.getPosts(0, 10);

		// Assert
		Assertions.assertThat(savedPost).isNotNull();
		verify(postRepository).findAll(pageable);
	}

	@Test
	public void PostService_GetPostById_ReturnsPost() {
		// Arrange
		LocalDateTime postDateTime = LocalDateTime.now().minusHours(1);

		Post post = Post.builder()
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(postDateTime))
				.build();

		// Act
		when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(post));
		Post fetchedPost = postService.getPost(1L);

		// Assert
		Assertions.assertThat(fetchedPost).isNotNull();
		Assertions.assertThat(fetchedPost.getTitle()).isEqualTo(post.getTitle());
		Assertions.assertThat(fetchedPost.getContent()).isEqualTo(post.getContent());
		Assertions.assertThat(fetchedPost.getDate()).isEqualTo(post.getDate());
	}

	@Test
	public void PostService_DeletePostById_ReturnsVoid() {
		// Arrange
		// Act
		
		// Assert
		assertAll(() -> postService.deletePost(1L));
	}

}
