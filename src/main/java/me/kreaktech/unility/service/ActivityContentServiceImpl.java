package me.kreaktech.unility.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import me.kreaktech.unility.entity.ActivityContent;
import me.kreaktech.unility.repository.ActivityContentRepository;
import me.kreaktech.unility.utils.Utils;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
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
    public void deleteAllActivityContent() {
        activityContentRepository.deleteAll();
    }
}
