package me.kreaktech.unility.activity;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
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
    String activityDateTimeString;

    @BeforeEach
    void setUpService() {

        activityDateTime = LocalDateTime.now().minusHours(1);
        activityDateTimeString = activityDateTime.toString();
        // Arrange
        University university1 = University.builder()
                .name("some university1")
                .build();

        University university2 = University.builder()
                .name("some university2")
                .build();

        ActivityContent activityContent1 = ActivityContent.builder()
                .details("some details1")
                .organizer("some organizer1")
                .activityLanguages(String.valueOf(Language.EN))
                .physicalStatus(PhysicalStatus.FACETOFACE)
                .build();

        ActivityContent activityContent2 = ActivityContent.builder()
                .details("some details2")
                .organizer("some organizer21")
                .activityLanguages(String.valueOf(Language.EN))
                .physicalStatus(PhysicalStatus.FACETOFACE)
                .build();

        Activity activity1 = Activity.builder()
                .university(university1)
                .activityContent(activityContent1)
                .date(activityDateTimeString)
                .id(1)
                .build();

        Activity activity2 = Activity.builder()
                .university(university2)
                .activityContent(activityContent2)
                .date(activityDateTimeString)
                .id(1)
                .build();

        // Act
        when(activityRepository.save(Mockito.any(Activity.class))).thenReturn(activity1);
        savedActivity1 = activityServiceImpl.saveActivity(activity1);
        when(activityRepository.save(Mockito.any(Activity.class))).thenReturn(activity2);
        savedActivity2 = activityServiceImpl.saveActivity(activity2);

    }

    @Test
    public void ActivityService_CreateActivity_ReturnsActivity() {
        // Assert
        Assertions.assertThat(savedActivity1).isNotNull();
        Assertions.assertThat(savedActivity1.getDate()).isEqualTo(savedActivity1.getDate());
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
        // When
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            if (arg0 instanceof Integer id) {
                if (id == savedActivity1.getId()) {
                    return null;
                }
            }
            throw new IllegalArgumentException("Invalid argument(s) passed to deleteById method");
        }).when(activityRepository).deleteById(savedActivity1.getId());

        activityServiceImpl.deleteActivityById(savedActivity1.getId());

        // Then
        verify(activityRepository, times(1)).deleteById(savedActivity1.getId());
    }

}
