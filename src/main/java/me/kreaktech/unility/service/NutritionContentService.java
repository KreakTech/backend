package me.kreaktech.unility.service;

import me.kreaktech.unility.entity.NutritionContent;

public interface NutritionContentService {
	NutritionContent getNutritionContentById(Integer id);

	NutritionContent saveNutritionContent(NutritionContent nutritionContent);

	void deleteNutritionContentById(Integer id);
}
