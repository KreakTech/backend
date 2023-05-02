package me.kreaktech.unility.entity.events.DefaultUniversity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kreaktech.unility.constants.Enum.MealType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class DefaultMealData {
	private MealType mealType;
	private String mealItems;
}