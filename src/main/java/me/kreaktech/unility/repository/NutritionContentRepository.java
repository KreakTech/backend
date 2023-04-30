package me.kreaktech.unility.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kreaktech.unility.entity.NutritionContent;

public interface NutritionContentRepository extends JpaRepository<NutritionContent, Integer> {
    
}
