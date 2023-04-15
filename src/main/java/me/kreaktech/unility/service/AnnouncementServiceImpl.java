package me.kreaktech.unility.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import me.kreaktech.unility.entity.Announcement;
import me.kreaktech.unility.repository.AnnouncementRepository;
import me.kreaktech.unility.utils.Utils;
import lombok.AllArgsConstructor;

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
	public List<Announcement> getAllAnnouncements() {
		return announcementRepository.findAll();
	}

}