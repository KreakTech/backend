package me.kreaktech.unility.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import me.kreaktech.unility.entity.ActivityContent;
import me.kreaktech.unility.repository.ActivityContentRepository;
import me.kreaktech.unility.utils.Utils;

public class ActivityContentServiceImpl implements ActivityContentService {

    @Autowired
    ActivityContentRepository activityContentRepository;

    @Override
    public ActivityContent getActivityContentById(Integer id) {
        Optional<ActivityContent> activityContent = activityContentRepository.findById(id);
        return Utils.unwrap(activityContent, id);
    }

    @Override
    public ActivityContent saveActivityContent(ActivityContent activityContent) {
        return activityContentRepository.save(activityContent);
    }

    @Override
    public void deleteActivityContentById(Integer id) {
        activityContentRepository.deleteById(id);
    }

    @Override
    public ActivityContent getActivityContentByTitle(String title) {
        Optional<ActivityContent> activityContent = activityContentRepository.findActivityContentByTitle(title);
        return Utils.unwrap(activityContent, title);
    }
}
