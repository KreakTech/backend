package me.kreaktech.unility.universityfetch;

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
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.entity.UniversityFetch;
import me.kreaktech.unility.repository.UniversityFetchRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UniversityFetchRepositoryTest {

        @Autowired
        private UniversityFetchRepository universityFetchRepository;

        UniversityFetch savedUniversityFetch;
        UniversityFetch universityFetch;

        @BeforeEach
        void setUpRepository() {

                LocalDateTime activityDateTime = LocalDateTime.now().minusHours(1);

                University university = University.builder()
                                .name("some university1")
                                .build();

                universityFetch = UniversityFetch.builder()
                                .announcementsLastFetchMD5("some announcementsLastFetchMD5")
                                .cafeteriaLastFetchDate(Timestamp.valueOf(activityDateTime))
                                .language(Language.EN)
                                .university(university)
                                .build();

                savedUniversityFetch = universityFetchRepository.save(universityFetch);
        }

        @Test
        public void UniversityFetchRepository_Save_ReturnUniversityFetch() {
                // Assert
                Assertions.assertThat(savedUniversityFetch).isNotNull();
                Assertions.assertThat(savedUniversityFetch.getId()).isGreaterThan(0);
                Assertions.assertThat(savedUniversityFetch.getLanguage()).isEqualTo(universityFetch.getLanguage());
                Assertions.assertThat(savedUniversityFetch.getCafeteriaLastFetchDate())
                                .isEqualTo(universityFetch.getCafeteriaLastFetchDate());
                Assertions.assertThat(savedUniversityFetch.getAnnouncementsLastFetchMD5())
                                .isEqualTo(universityFetch.getAnnouncementsLastFetchMD5());
        }

        @Test
        public void UniversityFetchRepository_FindById_ReturnUniversityFetch() {
                // Act
                UniversityFetch fetchedUniversityFetch = universityFetchRepository
                                .findById(savedUniversityFetch.getId()).get();

                // Assert
                Assertions.assertThat(fetchedUniversityFetch).isNotNull();
                Assertions.assertThat(fetchedUniversityFetch.getId()).isGreaterThan(0);
                Assertions.assertThat(fetchedUniversityFetch.getLanguage())
                                .isEqualTo(savedUniversityFetch.getLanguage());
                Assertions.assertThat(fetchedUniversityFetch.getCafeteriaLastFetchDate())
                                .isEqualTo(savedUniversityFetch.getCafeteriaLastFetchDate());
                Assertions.assertThat(fetchedUniversityFetch.getAnnouncementsLastFetchMD5())
                                .isEqualTo(savedUniversityFetch.getAnnouncementsLastFetchMD5());
        }

        @Test
        public void UniversityFetchRepository_FindByUniversityIdAndLanguage_ReturnUniversityFetch() {
                // Act
                UniversityFetch fetchedUniversityFetch = universityFetchRepository.findByUniversityIdAndLanguage(
                                savedUniversityFetch.getUniversity().getId(), savedUniversityFetch.getLanguage())
                                .get();

                // Assert
                Assertions.assertThat(fetchedUniversityFetch).isNotNull();
                Assertions.assertThat(fetchedUniversityFetch.getId()).isGreaterThan(0);
                Assertions.assertThat(fetchedUniversityFetch.getLanguage())
                                .isEqualTo(savedUniversityFetch.getLanguage());
                Assertions.assertThat(fetchedUniversityFetch.getCafeteriaLastFetchDate())
                                .isEqualTo(savedUniversityFetch.getCafeteriaLastFetchDate());
                Assertions.assertThat(fetchedUniversityFetch.getAnnouncementsLastFetchMD5())
                                .isEqualTo(savedUniversityFetch.getAnnouncementsLastFetchMD5());
        }

        @Test
        public void UniversityFetchRepository_UpdateUniversityFetch_ReturnUniversityFetch() {
                // Act
                UniversityFetch fetchedUniversityFetch = universityFetchRepository
                                .findById(savedUniversityFetch.getId()).get();

                // Modifications
                fetchedUniversityFetch.setAnnouncementsLastFetchMD5("Some new announcementsLastFetchMD5");
                fetchedUniversityFetch.setLanguage(Language.TR);

                UniversityFetch updatedUniversityFetch = universityFetchRepository.save(fetchedUniversityFetch);

                // Assert
                Assertions.assertThat(updatedUniversityFetch).isNotNull();
                Assertions.assertThat(updatedUniversityFetch.getAnnouncementsLastFetchMD5())
                                .isEqualTo(fetchedUniversityFetch.getAnnouncementsLastFetchMD5())
                                .isEqualTo("Some new announcementsLastFetchMD5");
                Assertions.assertThat(updatedUniversityFetch.getLanguage())
                                .isEqualTo(fetchedUniversityFetch.getLanguage());
        }

        @Test
        public void UniversityFetchRepository_DeleteUniversityFetch_ReturnUniversityFetchIsNotPresent() {
                // Act
                universityFetchRepository.deleteById(savedUniversityFetch.getId());
                Optional<UniversityFetch> fetchedUniversityFetch = universityFetchRepository
                                .findById(savedUniversityFetch.getId());

                // Assert
                Assertions.assertThat(fetchedUniversityFetch).isNotPresent();
        }
}