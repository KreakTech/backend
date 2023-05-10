package me.kreaktech.unility.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.kreaktech.unility.constants.Enum;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Entity
@Table(name = "announcement")
public class Announcement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotBlank(message = "Title cannot be blank")
	@NonNull
	@Column(name = "title", nullable = false)
	private String title;

	@Past(message = "The announcement date must be in the past")
	@NonNull
	@Column(name = "date", nullable = false)
	private Timestamp date;

	@NotBlank(message = "Link cannot be blank")
	@NonNull
	@Column(name = "link", nullable = false)
	private String link;

	@NonNull
	@Column(name = "language", nullable = false)
	private Enum.Language language;

	@ManyToOne(optional = false)
	@JoinColumn(name = "university_id", referencedColumnName = "id")
	private University university;

}
