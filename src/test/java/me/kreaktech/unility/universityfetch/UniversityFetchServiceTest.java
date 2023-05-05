package me.kreaktech.unility.universityfetch;

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
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.entity.UniversityFetch;
import me.kreaktech.unility.repository.UniversityFetchRepository;
import me.kreaktech.unility.service.UniversityFetchServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UniversityFetchServiceTest {

    @Mock
    private UniversityFetchRepository universityFetchRepository;

    @InjectMocks
    private UniversityFetchServiceImpl universityFetchServiceImpl;

    UniversityFetch savedUniversityFetch;
    UniversityFetch universityFetch;

    @BeforeEach
    void setUpService() {

        LocalDateTime activityDateTime = LocalDateTime.now().minusHours(1);

        University university = University.builder()
                .name("some university1")
                .build();

        universityFetch = UniversityFetch.builder()
                .announcementsLastFetchMD5("some announcementsLastFetchMD5")
                .cafeteriaLastFetchDate(Timestamp.valueOf(activityDateTime))
                .language(Language.EN)
                .university(university)
                .id(1)
                .build();

        // Act
        when(universityFetchRepository.save(Mockito.any(UniversityFetch.class))).thenReturn(universityFetch);
        savedUniversityFetch = universityFetchServiceImpl.saveUniversityFetch(universityFetch);

    }

    @Test
    public void UniversityFetchService_CreateUniversityFetch_ReturnsUniversityFetch() {
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
	public void UniversityFetchService_GetUniversityFetchById_ReturnsUniversityFetch() {
		// Act
		when(universityFetchRepository.findById(1)).thenReturn(Optional.ofNullable(savedUniversityFetch));
		UniversityFetch fetchedUniversityFetch = universityFetchServiceImpl.getUniversityFetchById(1);

        // Assert
        Assertions.assertThat(fetchedUniversityFetch).isNotNull();
        Assertions.assertThat(fetchedUniversityFetch.getId()).isGreaterThan(0);
        Assertions.assertThat(fetchedUniversityFetch.getLanguage()).isEqualTo(savedUniversityFetch.getLanguage());
        Assertions.assertThat(fetchedUniversityFetch.getCafeteriaLastFetchDate())
                .isEqualTo(savedUniversityFetch.getCafeteriaLastFetchDate());
        Assertions.assertThat(fetchedUniversityFetch.getAnnouncementsLastFetchMD5())
                .isEqualTo(savedUniversityFetch.getAnnouncementsLastFetchMD5());
	}

    @Test
    public void UniversityFetchService_GetUniversityFetchByUniversityIdAndLanguage_ReturnsUniversityFetch(){
        // Act
		when(universityFetchRepository.findByUniversityIdAndLanguage(universityFetch.getUniversity().getId(), universityFetch.getLanguage())).thenReturn(Optional.ofNullable(savedUniversityFetch));
		UniversityFetch fetchedUniversityFetch = universityFetchServiceImpl.getUniversityFetchByUniversityIdAndLanguage(universityFetch.getUniversity().getId(), universityFetch.getLanguage());

        // Assert
        Assertions.assertThat(fetchedUniversityFetch).isNotNull();
        Assertions.assertThat(fetchedUniversityFetch.getId()).isGreaterThan(0);
        Assertions.assertThat(fetchedUniversityFetch.getLanguage()).isEqualTo(savedUniversityFetch.getLanguage());
        Assertions.assertThat(fetchedUniversityFetch.getCafeteriaLastFetchDate())
                .isEqualTo(savedUniversityFetch.getCafeteriaLastFetchDate());
        Assertions.assertThat(fetchedUniversityFetch.getAnnouncementsLastFetchMD5())
                .isEqualTo(savedUniversityFetch.getAnnouncementsLastFetchMD5());
    }

    @Test
    public void UniversityFetchService_DeleteUniversityFetchById_ReturnsVoid() {
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            if (arg0 instanceof Integer) {
                Integer id = (Integer) arg0;
                if (id == savedUniversityFetch.getId()) {
                    return null;
                }
            }
            throw new IllegalArgumentException("Invalid argument(s) passed to deleteById method");
        }).when(universityFetchRepository).deleteById(savedUniversityFetch.getId());

        universityFetchServiceImpl.deleteUniversityFetchById(savedUniversityFetch.getId());

        verify(universityFetchRepository, times(1)).deleteById(savedUniversityFetch.getId());
    }
}