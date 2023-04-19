package me.kreaktech.unility.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.kreaktech.unility.entity.Announcement;
import me.kreaktech.unility.entity.CafeteriaMenu;
import me.kreaktech.unility.entity.NutritionContent;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.entity.events.EventBody;
import me.kreaktech.unility.entity.events.BilkentUniversity.BilkentAnnouncementData;
import me.kreaktech.unility.entity.events.BilkentUniversity.BilkentMealData;
import me.kreaktech.unility.service.AnnouncementServiceImpl;
import me.kreaktech.unility.service.CafeteriaMenuServiceImpl;
import me.kreaktech.unility.service.NutritionContentServiceImpl;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class BilkentUniversityUtils {
	@Autowired
	CafeteriaMenuServiceImpl cafeteriaMenuService;
	@Autowired
	NutritionContentServiceImpl nutritionContentService;
	@Autowired
	AnnouncementServiceImpl announcementService;

	public void handleWeeklyMealsEvent(EventBody eventBody, University university) {
		BilkentMealData mealData = Utils.parseObjectToEntity(eventBody.getData(),
				BilkentMealData.class);

		NutritionContent nutritionContent = new NutritionContent();
		nutritionContent.setEnergyCal(mealData.getEnergy());
		nutritionContent.setCarbohydratePercentage(mealData.getCarbohydrate());
		nutritionContent.setProteinPercentage(mealData.getProtein());
		nutritionContent.setFatPercentage(mealData.getFat());

		NutritionContent createdNutritionContent = nutritionContentService.saveNutritionContent(nutritionContent);

		CafeteriaMenu cafeteriaMenu = new CafeteriaMenu();
		cafeteriaMenu.setNutritionContent(createdNutritionContent);
		cafeteriaMenu.setDateServed(eventBody.getDate());
		cafeteriaMenu.setLanguage(eventBody.getLanguage());
		cafeteriaMenu.setMealContent(mealData.getMealItems());
		cafeteriaMenu.setMealType(mealData.getMealType());
		cafeteriaMenu.setUniversity(university);

		cafeteriaMenuService.saveCafeteriaMenu(cafeteriaMenu);
	}

	public void handleAnnouncementEvent(EventBody eventBody, University university) {
		BilkentAnnouncementData announcementData = Utils.parseObjectToEntity(eventBody.getData(),
				BilkentAnnouncementData.class);

		Announcement announcement = new Announcement();
		announcement.setTitle(announcementData.getTitle());
		announcement.setLink(announcementData.getLink());
		announcement.setDate(eventBody.getDate());
		announcement.setUniversity(university);

		announcementService.saveAnnouncement(announcement);
	}
}
