package me.kreaktech.unility.entity;

import java.sql.Timestamp;

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
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.kreaktech.unility.constants.Enum.Language;
import me.kreaktech.unility.constants.Enum.MealType;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Builder
@Table(name = "cafeteria_menu", uniqueConstraints = @UniqueConstraint(columnNames = { "date_served", "menu_type",
		"language" }))
public class CafeteriaMenu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotBlank(message = "mealContent cannot be blank")
	@NonNull
	@Column(name = "meal_content", nullable = false)
	private String mealContent;

	@NonNull
	@Column(name = "date_served", nullable = false)
	private Timestamp dateServed;

	@NonNull
	@Column(name = "language", nullable = false)
	private Language language;

	@NonNull
	@Column(name = "menu_type", nullable = false)
	private MealType mealType;

	@ManyToOne(optional = false)
	@JoinColumn(name = "university_id", referencedColumnName = "id")
	private University university;

	@OneToOne(optional = false)
	@JoinColumn(name = "nutrition_content_id", referencedColumnName = "id")
	private NutritionContent nutritionContent;
}
