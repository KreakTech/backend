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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class BilkentUniversityUtils {

    @Autowired
    private CafeteriaMenuServiceImpl cafeteriaMenuService;
    @Autowired
    private NutritionContentServiceImpl nutritionContentService;
    @Autowired
    private AnnouncementServiceImpl announcementService;
    @Autowired
    private UniversityFetchServiceImpl universityFetchService;
    @Autowired
    private ActivityServiceImpl activityService;
    @Autowired
    private ActivityContentServiceImpl activityContentService;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void handleDailyMealsEvent(EventBody eventBody, University university) {
        BilkentMealData mealData = Utils.parseObjectToEntity(eventBody.getData(),
                BilkentMealData.class);

        university = entityManager.merge(university);

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

        UniversityFetch getUniversityFetch = universityFetchService.getUniversityFetchByUniversityIdAndLanguage(university.getId(), eventBody.getLanguage());
        getUniversityFetch.setCafeteriaLastFetchDate(cafeteriaMenu.getDateServed());
        universityFetchService.saveUniversityFetch(getUniversityFetch);

        cafeteriaMenuService.saveCafeteriaMenu(cafeteriaMenu);
    }

    @Transactional
    public void handleAnnouncementEvent(EventBody eventBody, University university) {
        BilkentAnnouncementData announcementData = Utils.parseObjectToEntity(eventBody.getData(),
                BilkentAnnouncementData.class);

        university = entityManager.merge(university);

        Announcement announcement = new Announcement();
        announcement.setTitle(announcementData.getTitle());
        announcement.setLink(announcementData.getLink());
        announcement.setDate(eventBody.getDate());
        announcement.setLanguage(eventBody.getLanguage());
        announcement.setUniversity(university);

        UniversityFetch getUniversityFetch = universityFetchService.getUniversityFetchByUniversityIdAndLanguage(university.getId(), eventBody.getLanguage());
        getUniversityFetch.setAnnouncementsLastFetchMD5(announcementData.getMd5());
        universityFetchService.saveUniversityFetch(getUniversityFetch);

        announcementService.saveAnnouncement(announcement);
    }

    @Transactional
    public void handleStudentActivitiesEvent(EventBody eventBody, University university) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(eventBody.getData());
        BilkentActivityData[] activityData = gson.fromJson(jsonElement, BilkentActivityData[].class);

        university = entityManager.merge(university);

        activityService.deleteAllActivitiesByUniversityId(university.getId());

        for (BilkentActivityData activity : activityData) {
            ActivityContent currActivityContent = new ActivityContent();
            currActivityContent.setDate(activity.getDate());
            currActivityContent.setActivityLanguages(activity.getLanguages());
            currActivityContent.setOrganizer(activity.getOrganizers());
            currActivityContent.setPhysicalStatus(activity.getPhysicalStatus());
            currActivityContent.setDetails(activity.getDetails());
            currActivityContent.setIncentive(Boolean.parseBoolean(activity.getHasRewards()));
            currActivityContent.setCanceled(false);

            activityContentService.saveActivityContent(currActivityContent);

            Activity currActivity = new Activity();
            currActivity.setActivityContent(currActivityContent);
            currActivity.setUniversity(university);
            currActivity.setDate(eventBody.getDate());

            entityManager.persist(currActivity);

            activityService.saveActivity(currActivity);
        }
    }
}
