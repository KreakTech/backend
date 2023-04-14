package me.kreaktech.unility.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@NoArgsConstructor
@Builder
@Entity
@Table(name = "university")
public class University {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotBlank(message = "Name cannot be blank")
	@NotNull
	@Column(name = "name", unique = true)
	private String name;

	@NonNull
	@Column(name = "announcements_last_fetch_date", nullable = false)
	private Timestamp announcementsLastFetchDate;

	@JsonIgnore
	@OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
	private Set<MapWaypoint> mapWaypoints;

	@JsonIgnore
	@OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
	private Set<CafeteriaMenu> cafeteriaMenus;

	@JsonIgnore
	@OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
	private Set<BusStop> busStops;

}
