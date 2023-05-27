package me.kreaktech.unility.service;

import java.util.List;

import me.kreaktech.unility.entity.Activity;

public interface ActivityService {
    Activity getActivityById(Integer id);

    Activity saveActivity(Activity activity);

    void deleteActivityById(Integer id);

    List<Activity> getAllActivities();

    List<Activity> getAllActivitiesByUniversityId(Integer universityId);

    void deleteAllActivitiesByUniversityId(Integer universityId);
}
