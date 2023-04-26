package me.kreaktech.unility.service;

import java.sql.Timestamp;
import java.util.List;

import me.kreaktech.unility.entity.Announcement;

public interface AnnouncementService {
    Announcement getAnnouncementById(Integer id);
    Announcement saveAnnouncement(Announcement announcement);
    void deleteAnnouncementById(Integer id);
    List<Announcement> getAllAnnouncements();
    Announcement getAnnouncementByTitle(String title);
    List<Announcement> getAnnouncementByDateBetweenAndDateLessThanEqual(Timestamp from, Timestamp to);
}