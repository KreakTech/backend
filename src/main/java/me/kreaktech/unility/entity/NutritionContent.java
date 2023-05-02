package me.kreaktech.unility.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private Integer id;

	@Column(name = "energy_cal", nullable = true)
	private int energyCal;

	@Column(name = "carbohydrate_percentage", nullable = true)
	private int carbohydratePercentage;

	@Column(name = "protein_percentage", nullable = true)
	private int proteinPercentage;

	@Column(name = "fat_percentage", nullable = true)
	private int fatPercentage;

}
