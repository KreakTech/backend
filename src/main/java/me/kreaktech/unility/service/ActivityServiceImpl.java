package me.kreaktech.unility.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import me.kreaktech.unility.entity.Activity;
import me.kreaktech.unility.repository.ActivityRepository;
import me.kreaktech.unility.utils.Utils;

@AllArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public Activity getActivityById(Integer id) {
        Optional<Activity> activity = activityRepository.findById(id);
        return Utils.unwrap(activity, id);
    }

    @Override
    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public void deleteActivityById(Integer id) {
        activityRepository.deleteById(id);
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
}
