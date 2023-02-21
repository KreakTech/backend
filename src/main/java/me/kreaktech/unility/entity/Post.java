package me.kreaktech.unility.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Past(message = "The post date must be in the past")
	@NonNull
	@Column(name = "date", nullable = false)
	private LocalDate date;

	@NotBlank(message = "Content cannot be blank")
	@NonNull
	@Column(name = "content", nullable = false)
	private String content;
}
