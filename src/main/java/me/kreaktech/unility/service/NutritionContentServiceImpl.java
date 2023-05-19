package me.kreaktech.unility.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import me.kreaktech.unility.entity.NutritionContent;
import me.kreaktech.unility.repository.NutritionContentRepository;
import me.kreaktech.unility.utils.Utils;

@AllArgsConstructor
@Service
public class NutritionContentServiceImpl implements NutritionContentService {

	NutritionContentRepository nutritionContentRepository;

	@Override
	public NutritionContent getNutritionContentById(Integer id) {
		return Utils.unwrap(nutritionContentRepository.findById(id), id);
	}

	@Override
	public NutritionContent saveNutritionContent(NutritionContent nutritionContent) {
		return nutritionContentRepository.save(nutritionContent);
	}

	@Override
	public void deleteNutritionContentById(Integer id) {
		nutritionContentRepository.deleteById(id);
    }

}
