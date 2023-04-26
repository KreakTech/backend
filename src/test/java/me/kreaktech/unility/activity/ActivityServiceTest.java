package me.kreaktech.unility.activity;

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

import me.kreaktech.unility.constants.Enum.Language;
import me.kreaktech.unility.constants.Enum.PhysicalStatus;
import me.kreaktech.unility.entity.Activity;
import me.kreaktech.unility.entity.ActivityContent;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.repository.ActivityRepository;
import me.kreaktech.unility.service.ActivityServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityServiceImpl activityServiceImpl;

    Activity savedActivity1;
    Activity savedActivity2;
    LocalDateTime activityDateTime;

    @BeforeEach
    void setUpRepository() {
        LocalDateTime activityDateTime = LocalDateTime.now().minusHours(1);
        // Arrange
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

        savedActivity1 = Activity.builder()
                .university(university1)
                .activityContent(activityContent1)
                .date(Timestamp.valueOf(activityDateTime))
                .build();

        savedActivity2 = Activity.builder()
                .university(university2)
                .activityContent(activityContent2)
                .date(Timestamp.valueOf(activityDateTime))
                .build();
    }

    @Test
	public void ActivityService_CreateActivity_ReturnsActivity() {
		// Act
		when(activityRepository.save(Mockito.any(Activity.class))).thenReturn(savedActivity1);
		Activity savedActivityInTest = activityServiceImpl.saveActivity(savedActivity1);

		// Assert
		Assertions.assertThat(savedActivityInTest).isNotNull();
		Assertions.assertThat(savedActivityInTest.getDate()).isEqualTo(savedActivity1.getDate());
	}

    @Test
    public void ActivityService_GetAllActivities_ReturnsActivities() {
        // Given
        List<Activity> activities = new ArrayList<>();
        activities.add(savedActivity1);
        activities.add(savedActivity2);

        // When
        when(activityRepository.findAll()).thenReturn(activities);
        List<Activity> result = activityServiceImpl.getAllActivities();

        // Then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.get(0).getUniversity()).isEqualTo(savedActivity1.getUniversity());
        Assertions.assertThat(result.get(1).getUniversity()).isEqualTo(savedActivity2.getUniversity());
    }

    @Test
	public void ActivityService_GetActivityById_ReturnsActivity() {
		// Act
		when(activityRepository.findById(1)).thenReturn(Optional.ofNullable(savedActivity1));
		Activity fetchedActivity = activityServiceImpl.getActivityById(1);

		// Assert
		Assertions.assertThat(fetchedActivity).isNotNull();
		Assertions.assertThat(fetchedActivity.getUniversity()).isEqualTo(savedActivity1.getUniversity());
		Assertions.assertThat(fetchedActivity.getId()).isEqualTo(savedActivity1.getId());
		Assertions.assertThat(fetchedActivity.getActivityContent()).isEqualTo(savedActivity1.getActivityContent());
	}

    @Test
    public void ActivityService_DeleteActivityById_ReturnsVoid() {
        activityRepository.deleteById(savedActivity1.getId());
        Activity deletedActivity = activityRepository.findById(savedActivity1.getId()).orElse(null);
        Assertions.assertThat(deletedActivity).isNull();
    }

}
