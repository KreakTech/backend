package me.kreaktech.unility.service;

import me.kreaktech.unility.entity.ActivityContent;

public interface ActivityContentService {
    ActivityContent getActivityContentById(Integer id);

    ActivityContent saveActivityContent(ActivityContent activityContent);

    void deleteActivityContentById(Integer id);

    void deleteAllActivityContent();
}
