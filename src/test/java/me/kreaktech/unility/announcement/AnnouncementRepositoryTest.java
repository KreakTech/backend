package me.kreaktech.unility.announcement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.repository.UniversityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import me.kreaktech.unility.entity.Announcement;
import me.kreaktech.unility.repository.AnnouncementRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AnnouncementRepositoryTest {

	@Autowired
	private AnnouncementRepository announcementRepository;
	@Autowired
	private UniversityRepository universityRepository;

	Announcement announcement;
	LocalDateTime announcementDateTime;
	Announcement savedAnnouncement;
	University university;

	@BeforeEach
	void setUpRepository(){
		// Arrange

		university = University.builder()
				.name("Some University")
				.build();
		university = universityRepository.save(university);


		announcementDateTime = LocalDateTime.now().minusHours(1);

		announcement = Announcement.builder()
				.title("Some Title")
				.link("https://someurl.com")
				.date(Timestamp.valueOf(announcementDateTime))
				.university(university)
				.build();

		announcementRepository.save(announcement);
		savedAnnouncement = announcementRepository.save(announcement);

	}

	@Test
	public void AnnouncementRepository_SaveAll_ReturnSavedAnnouncement() {

		//Assert
		Assertions.assertThat(savedAnnouncement).isNotNull();
		Assertions.assertThat(savedAnnouncement.getId()).isGreaterThan(0);
		Assertions.assertThat(savedAnnouncement.getTitle()).isEqualTo(announcement.getTitle());
		Assertions.assertThat(savedAnnouncement.getLink()).isEqualTo(announcement.getLink());
		Assertions.assertThat(savedAnnouncement.getUniversity()).isEqualTo(university);
	}

	@Test
	public void AnnouncementRepository_FindById_ReturnAnnouncement() {
		//Act
		Announcement fetchedAnnouncement = announcementRepository.findById(savedAnnouncement.getId()).get();

		//Assert
		Assertions.assertThat(fetchedAnnouncement).isNotNull();
		Assertions.assertThat(fetchedAnnouncement.getId()).isEqualTo(savedAnnouncement.getId());
		Assertions.assertThat(fetchedAnnouncement.getTitle()).isEqualTo(savedAnnouncement.getTitle());
		Assertions.assertThat(fetchedAnnouncement.getLink()).isEqualTo(savedAnnouncement.getLink());
		Assertions.assertThat(savedAnnouncement.getUniversity()).isEqualTo(university);
	}

	@Test
	public void AnnouncementRepository_FindDatesInBetween_ReturnMoreThanOneAnnouncement() {
		// Arrange
		LocalDateTime firstAnnouncementDateTime = LocalDateTime.now().minusHours(12).withNano(0);
		LocalDateTime secondAnnouncementDateTime = LocalDateTime.now().minusHours(1).withNano(0);

		Announcement firstAnnouncement = Announcement.builder()
				.title("Some Title")
				.link("https://someurl.com")
				.date(Timestamp.valueOf(firstAnnouncementDateTime))
				.university(university)
				.build();

		Announcement secondAnnouncement = Announcement.builder()
				.title("Some Title")
				.link("https://someurl.com")
				.date(Timestamp.valueOf(secondAnnouncementDateTime))
				.university(university)
				.build();

		announcementRepository.save(firstAnnouncement);
		announcementRepository.save(secondAnnouncement);

		// Act
		List<Announcement> fetchedAnnouncements = announcementRepository.findByDateBetweenAndDateLessThanEqual(
				Timestamp.valueOf(firstAnnouncementDateTime),
				Timestamp.valueOf(secondAnnouncementDateTime));

		// Assert
		Assertions.assertThat(fetchedAnnouncements).isNotNull();
		Assertions.assertThat(fetchedAnnouncements.size()).isEqualTo(2);
	}

	@Test
	public void AnnouncementRepository_FindByTitle_ReturnAnnouncement() {
		// Act
		Announcement fetchedAnnouncement = announcementRepository.findByTitle(savedAnnouncement.getTitle()).get();

		// Assert
		Assertions.assertThat(fetchedAnnouncement).isNotNull();
		Assertions.assertThat(fetchedAnnouncement.getId()).isEqualTo(savedAnnouncement.getId());
		Assertions.assertThat(fetchedAnnouncement.getTitle()).isEqualTo(savedAnnouncement.getTitle());
		Assertions.assertThat(fetchedAnnouncement.getLink()).isEqualTo(savedAnnouncement.getLink());
		Assertions.assertThat(fetchedAnnouncement.getUniversity()).isEqualTo(savedAnnouncement.getUniversity());
	}

	@Test
	public void AnnouncementRepository_UpdateAnnouncement_ReturnAnnouncement() {
		// Act
		Announcement fetchedAnnouncement = announcementRepository.findById(announcement.getId()).get();

		fetchedAnnouncement.setTitle("Some New Title");
		fetchedAnnouncement.setLink("Some New Content");

		Announcement updatedAnnouncement = announcementRepository.save(fetchedAnnouncement);

		// Assert
		Assertions.assertThat(updatedAnnouncement).isNotNull();
		Assertions.assertThat(updatedAnnouncement.getId()).isEqualTo(fetchedAnnouncement.getId());
		Assertions.assertThat(updatedAnnouncement.getTitle()).isEqualTo(fetchedAnnouncement.getTitle());
		Assertions.assertThat(updatedAnnouncement.getLink()).isEqualTo(fetchedAnnouncement.getLink());
		Assertions.assertThat(updatedAnnouncement.getUniversity()).isEqualTo(fetchedAnnouncement.getUniversity());
	}

	@Test
	public void AnnouncementRepository_DeleteAnnouncement_ReturnAnnouncementIsNotPresent() {
		// Act
		announcementRepository.deleteById(announcement.getId());
		Optional<Announcement> fetchedAnnouncement = announcementRepository.findById(announcement.getId());

		// Assert
		Assertions.assertThat(fetchedAnnouncement).isNotPresent();
	}
}
