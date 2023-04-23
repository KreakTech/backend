package me.kreaktech.unility.utils;

import me.kreaktech.unility.entity.*;
import me.kreaktech.unility.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.kreaktech.unility.entity.events.EventBody;
import me.kreaktech.unility.entity.events.BilkentUniversity.BilkentAnnouncementData;
import me.kreaktech.unility.entity.events.BilkentUniversity.BilkentMealData;

import java.sql.Timestamp;

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
	@Autowired
	UniversityFetchServiceImpl universityFetchService;

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

		UniversityFetch getUniversityFetch = universityFetchService.getUniversityFetch(university.getId());
		getUniversityFetch.setCafeteriaLastFetchDate(cafeteriaMenu.getDateServed());
		universityFetchService.saveUniversityFetch(getUniversityFetch);

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


		UniversityFetch getUniversityFetch = universityFetchService.getUniversityFetch(university.getId());
		getUniversityFetch.setAnnouncementsLastFetchMD5(announcementData.getMd5());
		universityFetchService.saveUniversityFetch(getUniversityFetch);

		announcementService.saveAnnouncement(announcement);
	}
}
