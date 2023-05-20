package me.kreaktech.unility.entity;

import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Entity
@Table(name = "activity", uniqueConstraints = @UniqueConstraint(columnNames = { "university_id",
		"activity_content_id" }))
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "university_id", referencedColumnName = "id")
	private University university;

	@NonNull
	@Column(name = "activity_date", nullable = false)
	private Timestamp date;

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "activity_content_id", referencedColumnName = "id")
	private ActivityContent activityContent;

}
