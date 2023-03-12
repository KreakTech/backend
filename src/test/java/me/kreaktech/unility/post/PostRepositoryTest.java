package me.kreaktech.unility.post;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import me.kreaktech.unility.entity.Post;
import me.kreaktech.unility.repository.PostRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	// This test will deliberately fail
	@Test
	public void PostRepository_WillFail() {
		Assertions.assertThat(1).isEqualTo(2);
	}

	@Test
	public void PostRepository_SaveAll_ReturnSavedPost() {
		// Arrange
		LocalDateTime postDateTime = LocalDateTime.now().minusHours(1);

		Post post = Post.builder()
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(postDateTime)).build();

		// Act
		Post savedPost = postRepository.save(post);

		// Assert
		Assertions.assertThat(savedPost).isNotNull();
		Assertions.assertThat(savedPost.getId()).isGreaterThan(0);
		Assertions.assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
		Assertions.assertThat(savedPost.getContent()).isEqualTo(post.getContent());
	}

	@Test
	public void PostRepository_FindById_ReturnPost() {
		// Arrange
		LocalDateTime postDateTime = LocalDateTime.now().minusHours(1);

		Post post = Post.builder()
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(postDateTime))
				.build();

		Post savedPost = postRepository.save(post);

		// Act
		Post fetchedPost = postRepository.findById(savedPost.getId()).get();

		// Assert
		Assertions.assertThat(fetchedPost).isNotNull();
		Assertions.assertThat(fetchedPost.getId()).isEqualTo(savedPost.getId());
		Assertions.assertThat(fetchedPost.getTitle()).isEqualTo(savedPost.getTitle());
		Assertions.assertThat(fetchedPost.getContent()).isEqualTo(savedPost.getContent());
	}

	@Test
	public void PostRepository_FindDatesInBetween_ReturnMoreThanOnePost() {
		// Arrange
		LocalDateTime firstPostDateTime = LocalDateTime.now().minusHours(12).withNano(0);
		LocalDateTime secondPostDateTime = LocalDateTime.now().minusHours(1).withNano(0);

		Post firstPost = Post.builder()
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(firstPostDateTime))
				.build();

		Post secondPost = Post.builder()
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(secondPostDateTime))
				.build();

		postRepository.save(firstPost);
		postRepository.save(secondPost);

		// Act
		List<Post> fetchedPosts = postRepository.findByDateBetweenAndDateLessThanEqual(
				Timestamp.valueOf(firstPostDateTime),
				Timestamp.valueOf(secondPostDateTime));

		// Assert
		Assertions.assertThat(fetchedPosts).isNotNull();
		Assertions.assertThat(fetchedPosts.size()).isEqualTo(2);
	}

	@Test
	public void PostRepository_FindByTitle_ReturnPost() {
		// Arrange
		LocalDateTime postDateTime = LocalDateTime.now().minusHours(1);

		Post post = Post.builder()
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(postDateTime))
				.build();

		Post savedPost = postRepository.save(post);

		// Act
		Post fetchedPost = postRepository.findByTitle(savedPost.getTitle()).get();

		// Assert
		Assertions.assertThat(fetchedPost).isNotNull();
		Assertions.assertThat(fetchedPost.getId()).isEqualTo(savedPost.getId());
		Assertions.assertThat(fetchedPost.getTitle()).isEqualTo(savedPost.getTitle());
		Assertions.assertThat(fetchedPost.getContent()).isEqualTo(savedPost.getContent());
	}

	@Test
	public void PostRepository_UpdatePost_ReturnPost() {
		// Arrange
		LocalDateTime postDateTime = LocalDateTime.now().minusHours(1);

		Post post = Post.builder()
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(postDateTime))
				.build();

		postRepository.save(post);

		// Act
		Post fetchedPost = postRepository.findById(post.getId()).get();

		fetchedPost.setTitle("Some New Title");
		fetchedPost.setContent("Some New Content");

		Post updatedPost = postRepository.save(fetchedPost);

		// Assert
		Assertions.assertThat(updatedPost).isNotNull();
		Assertions.assertThat(updatedPost.getId()).isEqualTo(fetchedPost.getId());
		Assertions.assertThat(updatedPost.getTitle()).isEqualTo(fetchedPost.getTitle());
		Assertions.assertThat(updatedPost.getContent()).isEqualTo(fetchedPost.getContent());
	}

	@Test
	public void PostRepository_DeletePost_ReturnPostIsNotPresent() {
		// Arrange
		LocalDateTime postDateTime = LocalDateTime.now().minusHours(1);

		Post post = Post.builder()
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(postDateTime))
				.build();

		postRepository.save(post);

		// Act
		postRepository.deleteById(post.getId());
		Optional<Post> fetchedPost = postRepository.findById(post.getId());

		// Assert
		Assertions.assertThat(fetchedPost).isNotPresent();
	}
}
