package me.kreaktech.unility.activity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import me.kreaktech.unility.repository.ActivityRepository;
import me.kreaktech.unility.constants.Enum.Language;
import me.kreaktech.unility.constants.Enum.PhysicalStatus;
import me.kreaktech.unility.entity.Activity;
import me.kreaktech.unility.entity.ActivityContent;
import me.kreaktech.unility.entity.University;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ActivityRepositoryTest {

    @Autowired
	private ActivityRepository activityRepository;

    Activity savedActivity1;
    Activity savedActivity2;
    
    @BeforeEach
	void setUpRepository(){
        LocalDateTime activityDateTime = LocalDateTime.now().minusHours(1);
        //Arrange
        University university1 = University.builder()
            .name("some university1")
            .announcementsLastFetchDate(Timestamp.valueOf(activityDateTime))
            .build();

        University university2 = University.builder()
            .name("some university2")
            .announcementsLastFetchDate(Timestamp.valueOf(activityDateTime))
            .build();

        ActivityContent activityContent1 = ActivityContent.builder()
            .details("some details1")
            .title("some title1")
            .organizer("some organizer1")
            .activityLanguage(Language.EN)
            .activityDuration(Timestamp.valueOf(activityDateTime))
            .physicalStatus(PhysicalStatus.FACETOFACE)
            .build();

        ActivityContent activityContent2 = ActivityContent.builder()
            .details("some details2")
            .title("some title2")
            .organizer("some organizer21")
            .activityLanguage(Language.EN)
            .activityDuration(Timestamp.valueOf(activityDateTime))
            .physicalStatus(PhysicalStatus.FACETOFACE)
            .build();

	
		Activity activity1 = Activity.builder()
				.university(university1)
                .activityContent(activityContent1)
                .date(Timestamp.valueOf(activityDateTime))
                .build();
        
        Activity activity2 = Activity.builder()
				.university(university2)
                .activityContent(activityContent2)
                .date(Timestamp.valueOf(activityDateTime))
                .build();

		activityRepository.save(activity1);
		savedActivity1 = activityRepository.save(activity1);

        activityRepository.save(activity2);
		savedActivity2 = activityRepository.save(activity2);

	}

    @Test
	public void ActivityRepository_SaveAll_ReturnsavedActivity1() {

		//Assert
		Assertions.assertThat(savedActivity1).isNotNull();
		Assertions.assertThat(savedActivity1.getId()).isGreaterThan(0);
	}

	@Test
	public void ActivityRepository_FindById_ReturnActivity() {
		//Act
		Activity fetchedActivity = activityRepository.findById(savedActivity1.getId()).get();

		//Assert
		Assertions.assertThat(fetchedActivity).isNotNull();
		Assertions.assertThat(fetchedActivity.getId()).isEqualTo(savedActivity1.getId());
		Assertions.assertThat(fetchedActivity.getUniversity()).isEqualTo(savedActivity1.getUniversity());
        Assertions.assertThat(fetchedActivity.getActivityContent()).isEqualTo(savedActivity1.getActivityContent());
	}

	@Test
	public void ActivityRepository_UpdateActivity_ReturnActivity() {
		// Act
		Activity fetchedActivity = activityRepository.findById(savedActivity1.getId()).get();

		fetchedActivity.setUniversity(savedActivity2.getUniversity());
		fetchedActivity.setActivityContent(savedActivity2.getActivityContent());

		Activity updatedActivity = activityRepository.save(fetchedActivity);

		// Assert
		Assertions.assertThat(updatedActivity).isNotNull();
		Assertions.assertThat(updatedActivity.getUniversity()).isEqualTo(savedActivity2.getUniversity());
		Assertions.assertThat(updatedActivity.getActivityContent()).isEqualTo(savedActivity2.getActivityContent());
	}

	@Test
	public void ActivityRepository_DeleteActivity_ReturnActivityIsNotPresent() {
		// Act
		activityRepository.deleteById(savedActivity1.getId());
		Optional<Activity> fetchedActivity = activityRepository.findById(savedActivity1.getId());

		// Assert
		Assertions.assertThat(fetchedActivity).isNotPresent();
	}
}

