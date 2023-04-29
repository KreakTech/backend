package me.kreaktech.unility.activitycontent;

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

import me.kreaktech.unility.constants.Enum.Language;
import me.kreaktech.unility.constants.Enum.PhysicalStatus;
import me.kreaktech.unility.entity.ActivityContent;
import me.kreaktech.unility.repository.ActivityContentRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ActivityContentRepositoryTest {

    @Autowired
    private ActivityContentRepository activityContentRepository;

    ActivityContent savedActivityContent;
    ActivityContent activityContent;

    @BeforeEach
    void setUpRepository() {
        LocalDateTime activityDateTime = LocalDateTime.now().minusHours(1);

        activityContent = ActivityContent.builder()
                .details("some details")
                .title("some title")
                .organizer("some organizer")
                .activityLanguage(Language.EN)
                .activityDuration(Timestamp.valueOf(activityDateTime))
                .physicalStatus(PhysicalStatus.FACETOFACE)
                .build();

        savedActivityContent = activityContentRepository.save(activityContent);
    }

    @Test
    public void ActivityContentRepository_Save_ReturnActivityContent() {
        // Assert
        Assertions.assertThat(savedActivityContent).isNotNull();
        Assertions.assertThat(savedActivityContent.getId()).isGreaterThan(0);
        Assertions.assertThat(savedActivityContent.getDetails()).isEqualTo(activityContent.getDetails());
    }

    @Test
    public void ActivityContentRepository_FindById_ReturnActivityContent() {
        // Act
        ActivityContent fetchedActivityContent = activityContentRepository.findById(savedActivityContent.getId()).get();

        // Assert
        Assertions.assertThat(fetchedActivityContent).isNotNull();
        Assertions.assertThat(fetchedActivityContent.getId()).isEqualTo(savedActivityContent.getId());
        Assertions.assertThat(fetchedActivityContent.getTitle()).isEqualTo(savedActivityContent.getTitle());
        Assertions.assertThat(fetchedActivityContent.getOrganizer()).isEqualTo(savedActivityContent.getOrganizer());
    }

    @Test
    public void ActivityContentRepository_FindActivityContentByTitle_ReturnActivityContent() {
        // Act
        ActivityContent fetchedActivityContent = activityContentRepository.findActivityContentByTitle(savedActivityContent.getTitle())
                .get();

        // Assert
        Assertions.assertThat(fetchedActivityContent).isNotNull();
        Assertions.assertThat(fetchedActivityContent.getId()).isEqualTo(savedActivityContent.getId());
        Assertions.assertThat(fetchedActivityContent.getTitle()).isEqualTo(savedActivityContent.getTitle());
        Assertions.assertThat(fetchedActivityContent.getOrganizer()).isEqualTo(savedActivityContent.getOrganizer());
    }

    @Test
    public void ActivityContentRepository_UpdateActivityContent_ReturnActivityContent() {
        // Act
        ActivityContent fetchedActivityContent = activityContentRepository.findById(savedActivityContent.getId()).get();

        // Modifications
        fetchedActivityContent.setOrganizer("some new organizer");
        fetchedActivityContent.setTitle("some new title");

        ActivityContent updatedActivityContent = activityContentRepository.save(fetchedActivityContent);

        // Assert
        Assertions.assertThat(updatedActivityContent).isNotNull();
        Assertions.assertThat(updatedActivityContent.getTitle()).isEqualTo("some new title");
        Assertions.assertThat(updatedActivityContent.getOrganizer()).isEqualTo("some new organizer");
    }

    @Test
    public void ActivityContentRepository_DeleteActivityContent_ReturnActivityContentIsNotPresent() {
        // Act
        activityContentRepository.deleteById(savedActivityContent.getId());
        Optional<ActivityContent> fetchedActivityContent = activityContentRepository
                .findById(savedActivityContent.getId());

        // Assert
        Assertions.assertThat(fetchedActivityContent).isNotPresent();
    }

}
