package me.kreaktech.unility.announcement;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
	private AnnouncementServiceImpl announcementServiceImpl;

	Announcement savedAnnouncement;
	LocalDateTime announcementDateTime;

	@BeforeEach
	void setUpService() {
		// Arrange
		announcementDateTime = LocalDateTime.now().minusHours(1);

		Announcement announcement = Announcement.builder()
				.title("Some Title")
				.link("https://somelink.com")
				.date(Timestamp.valueOf(announcementDateTime))
				.id(1)
				.build();

		// Act
		when(announcementRepository.save(Mockito.any(Announcement.class))).thenReturn(announcement);
		savedAnnouncement = announcementServiceImpl.saveAnnouncement(announcement);
	}

	@Test
	public void AnnouncementService_CreateAnnouncement_ReturnsAnnouncement() {
		// Assert
		Assertions.assertThat(savedAnnouncement).isNotNull();
		Assertions.assertThat(savedAnnouncement.getTitle()).isEqualTo(savedAnnouncement.getTitle());
		Assertions.assertThat(savedAnnouncement.getLink()).isEqualTo(savedAnnouncement.getLink());
		Assertions.assertThat(savedAnnouncement.getDate()).isEqualTo(savedAnnouncement.getDate());
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
		List<Announcement> result = announcementServiceImpl.getAllAnnouncements();

		// Then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(2);
		Assertions.assertThat(result.get(0).getTitle()).isEqualTo("Announcement 1");
		Assertions.assertThat(result.get(1).getTitle()).isEqualTo("Announcement 2");
	}

	@Test
	public void AnnouncementService_GetAnnouncementById_ReturnsAnnouncement() {
		// Act
		when(announcementRepository.findById(1)).thenReturn(Optional.ofNullable(savedAnnouncement));
		Announcement fetchedAnnouncement = announcementServiceImpl.getAnnouncementById(1);

		// Assert
		Assertions.assertThat(fetchedAnnouncement).isNotNull();
		Assertions.assertThat(fetchedAnnouncement.getTitle()).isEqualTo(savedAnnouncement.getTitle());
		Assertions.assertThat(fetchedAnnouncement.getLink()).isEqualTo(savedAnnouncement.getLink());
		Assertions.assertThat(fetchedAnnouncement.getDate()).isEqualTo(savedAnnouncement.getDate());
	}

	@Test
	public void AnnouncementService_DeleteAnnouncementById_ReturnsVoid() {
		doAnswer(invocation -> {
			Object arg0 = invocation.getArgument(0);
			if (arg0 instanceof Integer) {
				Integer id = (Integer) arg0;
				if (id == savedAnnouncement.getId()) {
					return null;
				}
			}
			throw new IllegalArgumentException("Invalid argument(s) passed to deleteById method");
		}).when(announcementRepository).deleteById(savedAnnouncement.getId());

		announcementServiceImpl.deleteAnnouncementById(savedAnnouncement.getId());

		verify(announcementRepository, times(1)).deleteById(savedAnnouncement.getId());
	}

}
