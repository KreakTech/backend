package me.kreaktech.unility.announcement;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import me.kreaktech.unility.entity.Announcement;
import me.kreaktech.unility.repository.AnnouncementRepository;
import me.kreaktech.unility.service.AnnouncementServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AnnouncementServiceTest {

	@Mock
	private AnnouncementRepository announcementRepository;

	@InjectMocks
	private AnnouncementServiceImpl announcementService;

	Announcement announcement;
	LocalDateTime announcementDateTime;

	@BeforeEach
	void setUpService(){
		// Arrange
		announcementDateTime = LocalDateTime.now().minusHours(1);

		announcement = Announcement.builder()
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(announcementDateTime))
				.build();
	}

	@Test
	public void AnnouncementService_CreateAnnouncement_ReturnsAnnouncement() {
		// Act
		when(announcementRepository.save(Mockito.any(Announcement.class))).thenReturn(announcement);
		Announcement savedAnnouncement = announcementService.saveAnnouncement(announcement);

		// Assert
		Assertions.assertThat(savedAnnouncement).isNotNull();
		Assertions.assertThat(savedAnnouncement.getTitle()).isEqualTo(announcement.getTitle());
		Assertions.assertThat(savedAnnouncement.getContent()).isEqualTo(announcement.getContent());
		Assertions.assertThat(savedAnnouncement.getDate()).isEqualTo(announcement.getDate());
	}

	@Test
	public void AnnouncementService_GetAllAnnouncements_ReturnsAnnouncements() {
		// Given
        List<Announcement> announcements = new ArrayList<>();
        Announcement announcement1 = new Announcement();
        announcement1.setId(1);
        announcement1.setTitle("Announcement 1");
        Announcement announcement2 = new Announcement();
        announcement2.setId(2);
        announcement2.setTitle("Announcement 2");
        announcements.add(announcement1);
        announcements.add(announcement2);

        // When
        when(announcementRepository.findAll()).thenReturn(announcements);
        List<Announcement> result = announcementService.getAllAnnouncements();

        // Then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.get(0).getTitle()).isEqualTo("Announcement 1");
        Assertions.assertThat(result.get(1).getTitle()).isEqualTo("Announcement 2");
	}

	@Test
	public void AnnouncementService_GetAnnouncementById_ReturnsAnnouncement() {
		// Act
		when(announcementRepository.findById(1)).thenReturn(Optional.ofNullable(announcement));
		Announcement fetchedAnnouncement = announcementService.getAnnouncementById(1);

		// Assert
		Assertions.assertThat(fetchedAnnouncement).isNotNull();
		Assertions.assertThat(fetchedAnnouncement.getTitle()).isEqualTo(announcement.getTitle());
		Assertions.assertThat(fetchedAnnouncement.getContent()).isEqualTo(announcement.getContent());
		Assertions.assertThat(fetchedAnnouncement.getDate()).isEqualTo(announcement.getDate());
	}

	@Test
	public void AnnouncementService_DeleteAnnouncementById_ReturnsVoid() {
        announcementRepository.deleteById(announcement.getId());
        Announcement deletedAnnouncement = announcementRepository.findById(announcement.getId()).orElse(null);
        Assertions.assertThat(deletedAnnouncement).isNull();
	}

}
