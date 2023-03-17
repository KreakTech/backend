package me.kreaktech.unility.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank(message = "Title cannot be blank")
	@NonNull
	@Column(name = "title", nullable = false)
	private String title;

	@Past(message = "The post date must be in the past")
	@NonNull
	@Column(name = "date", nullable = false)
	private Timestamp date;

	@NotBlank(message = "Content cannot be blank")
	@NonNull
	@Column(name = "content", nullable = false)
	private String content;
}
