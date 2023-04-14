package me.kreaktech.unility.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.kreaktech.unility.constants.Enum.MapWaypointType;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "map_waypoint", uniqueConstraints = @UniqueConstraint
(columnNames = { "coordinates", "menu_type","language" }))
public class MapWaypoint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotBlank(message = "Title cannot be blank")
	@NonNull
	@Column(name = "name", nullable = false)
	private String name;

	@NotBlank(message = "Coordinates cannot be blank")
	@NonNull
	@Column(name = "coordinates", nullable = false)
	private String coordinates;

	@NotBlank(message = "Type cannot be blank")
	@NonNull
	@Column(name = "type", nullable = false)
	private MapWaypointType type;

	@ManyToOne(optional = false)
	@JoinColumn(name = "university_id", referencedColumnName = "id")
	private University university;
}
