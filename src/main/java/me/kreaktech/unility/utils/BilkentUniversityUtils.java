package me.kreaktech.unility.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.kreaktech.unility.entity.*;
import me.kreaktech.unility.entity.events.BilkentUniversity.BilkentActivityData;
import me.kreaktech.unility.entity.events.BilkentUniversity.BilkentAnnouncementData;
import me.kreaktech.unility.entity.events.BilkentUniversity.BilkentMealData;
import me.kreaktech.unility.entity.events.EventBody;
import me.kreaktech.unility.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
    @Autowired
    ActivityServiceImpl activityService;
    @Autowired
    ActivityContentServiceImpl activityContentService;

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

        UniversityFetch getUniversityFetch = universityFetchService.getUniversityFetchById(university.getId());
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


        UniversityFetch getUniversityFetch = universityFetchService.getUniversityFetchById(university.getId());
        getUniversityFetch.setAnnouncementsLastFetchMD5(announcementData.getMd5());
        universityFetchService.saveUniversityFetch(getUniversityFetch);

        announcementService.saveAnnouncement(announcement);
    }


    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void handleStudentActivitiesEvent(EventBody eventBody, University university) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(eventBody.getData());
        BilkentActivityData[] activityData = gson.fromJson(jsonElement, BilkentActivityData[].class);

        activityService.deleteAllActivity();
        activityContentService.deleteAllActivityContent();
        university = entityManager.merge(university);

        for (BilkentActivityData activity : activityData) {
            ActivityContent currActivityContent = new ActivityContent();
            currActivityContent.setActivityDuration(activity.getTime());
            currActivityContent.setActivityLanguages(activity.getLanguages());
            currActivityContent.setOrganizer(activity.getOrganizers());
            currActivityContent.setPhysicalStatus(activity.getPhysicalStatus());
            currActivityContent.setDetails(activity.getDetails());
            currActivityContent.setIncentive(activity.isHasRewards());
            currActivityContent.setCanceled(false);

            activityContentService.saveActivityContent(currActivityContent);

            Activity currActivity = new Activity();
            currActivity.setActivityContent(currActivityContent);
            currActivity.setUniversity(university);
            currActivity.setDate(activity.getDate());

            entityManager.persist(currActivity);

            activityService.saveActivity(currActivity);
        }

    }
}
