package me.kreaktech.unility.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "nutrition_content")
public class NutritionContent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "energy_cal", nullable = true)
	private int energyCal;

	@Column(name = "carbohydrate_percentage", nullable = true)
	private int carbohydratePercentage;

	@Column(name = "protein_percentage", nullable = true)
	private int proteinPercentage;

	@Column(name = "fat_percentage", nullable = true)
	private int fatPercentage;

	@JsonIgnore
	@OneToOne(optional = false)
	@JoinColumn(name = "cafeteria_menu_id", referencedColumnName = "id")
	private CafeteriaMenu cafeteriaMenu;

}
