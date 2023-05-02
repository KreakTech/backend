package me.kreaktech.unility.service;

import java.util.List;

import me.kreaktech.unility.entity.Activity;

public interface ActivityService {
    Activity getActivityById(Integer id);

    Activity saveActivity(Activity activity);

    void deleteActivityById(Integer id);

    public List<Activity> getAllActivities();
}
