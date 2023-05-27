package me.kreaktech.unility.activitycontent;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import me.kreaktech.unility.entity.ActivityContent;
import me.kreaktech.unility.repository.ActivityContentRepository;
import me.kreaktech.unility.service.ActivityContentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ActivityContentServiceTest {

	@Mock
	private ActivityContentRepository activityContentRepository;

	@InjectMocks
	private ActivityContentServiceImpl activityContentServiceImpl;

	ActivityContent savedActivityContent;

	@BeforeEach
	void setUpService() {
		// Arrange
		LocalDateTime activityContentDateTime = LocalDateTime.now().minusHours(1);

		ActivityContent activityContent = ActivityContent.builder()
				.details("some details")
				.organizer("some organizer")
				.activityLanguages(String.valueOf(Language.EN))
				.date(String.valueOf(Timestamp.valueOf(activityContentDateTime)))
				.physicalStatus(PhysicalStatus.FACETOFACE)
				.id(1)
				.build();

		// Act
		when(activityContentRepository.save(Mockito.any(ActivityContent.class))).thenReturn(activityContent);
		savedActivityContent = activityContentServiceImpl.saveActivityContent(activityContent);

	}

	@Test
	public void ActivityContentService_CreateActivityContent_ReturnsActivityContent() {
		// Assert
		Assertions.assertThat(savedActivityContent).isNotNull();
		Assertions.assertThat(savedActivityContent.getDetails()).isEqualTo("some details");
		Assertions.assertThat(savedActivityContent.getOrganizer()).isEqualTo("some organizer");
	}

	@Test
	public void ActivityContentService_GetActivityContentById_ReturnsActivityContent() {
		// Act
		when(activityContentRepository.findById(1)).thenReturn(Optional.ofNullable(savedActivityContent));
		ActivityContent fetchedActivityContent = activityContentServiceImpl.getActivityContentById(1);

		// Assert
		Assertions.assertThat(fetchedActivityContent).isNotNull();
		Assertions.assertThat(savedActivityContent.getDetails()).isEqualTo("some details");
		Assertions.assertThat(savedActivityContent.getOrganizer()).isEqualTo("some organizer");
	}

	@Test
	public void ActivityContentService_DeleteActivityContentById_ReturnsVoid() {
		doAnswer(invocation -> {
			Object arg0 = invocation.getArgument(0);
			if (arg0 instanceof Integer id) {
				if (id == savedActivityContent.getId()) {
					return null;
				}
			}
			throw new IllegalArgumentException("Invalid argument(s) passed to deleteById method");
		}).when(activityContentRepository).deleteById(savedActivityContent.getId());

		activityContentServiceImpl.deleteActivityContentById(savedActivityContent.getId());

		verify(activityContentRepository, times(1)).deleteById(savedActivityContent.getId());
	}
}
