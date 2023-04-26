package me.kreaktech.unility.activitycontent;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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

        activityContentRepository.save(activityContent);
    }

    @AfterEach
    void flushRepository() {
        activityContentRepository.delete(activityContent);
    }

    @Test
    public void ActivityContentRepository_Save_ReturnActivityContent() {
        // Assert
        Assertions.assertThat(activityContent).isNotNull();
        Assertions.assertThat(activityContent.getId()).isGreaterThan(0);
        Assertions.assertThat(activityContent.getId()).isEqualTo(1);
    }

    @Test
    public void ActivityContentRepository_FindById_ReturnActivityContent() {
        // Act
        ActivityContent fetchedActivityContent = activityContentRepository.findById(activityContent.getId()).get();

        // Assert
        Assertions.assertThat(fetchedActivityContent).isNotNull();
        Assertions.assertThat(fetchedActivityContent.getId()).isEqualTo(activityContent.getId());
        Assertions.assertThat(fetchedActivityContent.getTitle()).isEqualTo(activityContent.getTitle());
        Assertions.assertThat(fetchedActivityContent.getOrganizer()).isEqualTo(activityContent.getOrganizer());
    }

    @Test
    public void ActivityContentRepository_FindByTitle_ReturnActivityContent() {
        // Act
        ActivityContent fetchedActivityContent = activityContentRepository.findByTitle(activityContent.getTitle())
                .get();

        // Assert
        Assertions.assertThat(fetchedActivityContent).isNotNull();
        Assertions.assertThat(fetchedActivityContent.getId()).isEqualTo(activityContent.getId());
        Assertions.assertThat(fetchedActivityContent.getTitle()).isEqualTo(activityContent.getTitle());
        Assertions.assertThat(fetchedActivityContent.getOrganizer()).isEqualTo(activityContent.getOrganizer());
    }

    @Test
    public void ActivityContentRepository_UpdateActivity_ReturnActivityContent() {
        // Act
        ActivityContent fetchedActivityContent = activityContentRepository.findById(activityContent.getId()).get();

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
    public void ActivityContentRepository_DeleteActivity_ReturnActivityContentIsNotPresent() {
        // Act
        activityContentRepository.deleteById(activityContent.getId());
        Optional<ActivityContent> fetchedActivityContent = activityContentRepository.findById(activityContent.getId());

        // Assert
        Assertions.assertThat(fetchedActivityContent).isNotPresent();
    }

}
