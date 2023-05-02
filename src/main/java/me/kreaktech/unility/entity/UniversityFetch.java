package me.kreaktech.unility.entity;

import lombok.*;
import me.kreaktech.unility.constants.Enum;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Entity
@Table(name = "university_fetch")
public class UniversityFetch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NonNull
	@Column(name = "announcements_last_fetch_md5", nullable = false)
	private String announcementsLastFetchMD5;

	@NonNull
	@Column(name = "cafeteria_last_fetch_date", nullable = false)
	private Timestamp cafeteriaLastFetchDate;

	@NonNull
	@Column(name = "language", nullable = false)
	private Enum.Language language;

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "university_id", referencedColumnName = "id")
	private University university;
}
