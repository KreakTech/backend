package me.kreaktech.unility.service;

import java.util.List;

import me.kreaktech.unility.entity.Announcement;

public interface AnnouncementService {
    Announcement getAnnouncementById(Integer id);
    Announcement saveAnnouncement(Announcement announcement);
    void deleteAnnouncementById(Integer id);
    List<Announcement> getAllAnnouncements();
}