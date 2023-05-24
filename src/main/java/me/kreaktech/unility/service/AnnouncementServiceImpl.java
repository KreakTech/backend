package me.kreaktech.unility.service;

import lombok.AllArgsConstructor;
import me.kreaktech.unility.entity.Announcement;
import me.kreaktech.unility.repository.AnnouncementRepository;
import me.kreaktech.unility.utils.Utils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private AnnouncementRepository announcementRepository;

    @Override
    public Announcement getAnnouncementById(Integer id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        return Utils.unwrap(announcement, id);
    }

    @Override
    public Announcement saveAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @Override
    public void deleteAnnouncementById(Integer id) {
        announcementRepository.deleteById(id);
    }

    @Override
    public void deleteAllAnnouncementsByUniversityId(Integer universityId) {
        announcementRepository.deleteAllByUniversityId(universityId);
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    @Override
    public Announcement getAnnouncementByTitle(String title) {
        Optional<Announcement> announcement = announcementRepository.findByTitle(title);
        return Utils.unwrap(announcement, title);
    }

    @Override
    public List<Announcement> getAnnouncementsByDateBetweenAndDateLessThanEqualAndUniversityId(Timestamp from, Timestamp to, Integer universityId) {
        return announcementRepository.findByDateBetweenAndDateLessThanEqualAndUniversityId(from, to, universityId);
    }

    @Override
    public List<Announcement> getAnnouncementsByUniversityId(Integer universityId) {
        return announcementRepository.findByUniversityId(universityId);
    }

}