package me.kreaktech.unility.mapwaypoint;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

import me.kreaktech.unility.constants.Enum.MapWaypointType;
import me.kreaktech.unility.entity.MapWaypoint;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.repository.MapWaypointRepository;
import me.kreaktech.unility.service.MapWaypointServiceImpl;

@ExtendWith(MockitoExtension.class)
class MapWaypointServiceTest {

    @Mock
    private MapWaypointRepository mapWaypointRepository;

    @InjectMocks
    private MapWaypointServiceImpl mapWaypointServiceImpl;

    MapWaypoint savedMapWaypoint;
    MapWaypoint mapWaypoint;

    @BeforeEach
    void setUpService() {
        LocalDateTime activityDateTime = LocalDateTime.now().minusHours(1);

        University university = University.builder()
                .name("some university1")
                .announcementsLastFetchDate(Timestamp.valueOf(activityDateTime))
                .build();

        mapWaypoint = MapWaypoint.builder()
                .name("some name")
                .coordinates("some coordinate")
                .type(MapWaypointType.UTILITY)
                .university(university)
                .id(1)
                .build();

        // Act
        when(mapWaypointRepository.save(Mockito.any(MapWaypoint.class))).thenReturn(mapWaypoint);
        savedMapWaypoint = mapWaypointServiceImpl.saveMapWaypoint(mapWaypoint);

    }

    @Test
    public void MapWaypointService_CreateMapWaypoint_ReturnsMapWaypoint() {
        // Assert
        Assertions.assertThat(savedMapWaypoint).isNotNull();
        Assertions.assertThat(savedMapWaypoint.getId()).isGreaterThan(0);
        Assertions.assertThat(savedMapWaypoint.getName()).isEqualTo(mapWaypoint.getName());
        Assertions.assertThat(savedMapWaypoint.getType()).isEqualTo(mapWaypoint.getType());
        Assertions.assertThat(savedMapWaypoint.getUniversity()).isEqualTo(mapWaypoint.getUniversity());
        Assertions.assertThat(savedMapWaypoint.getCoordinates()).isEqualTo(mapWaypoint.getCoordinates());
    }

    @Test
	public void MapWaypointService_GetMapWaypointById_ReturnsMapWaypoint() {
		// Act
		when(mapWaypointRepository.findById(1)).thenReturn(Optional.ofNullable(savedMapWaypoint));
		MapWaypoint fetchedMapWaypoint = mapWaypointServiceImpl.getMapWaypointById(1);

		// Assert
		Assertions.assertThat(fetchedMapWaypoint).isNotNull();
		Assertions.assertThat(fetchedMapWaypoint.getId()).isGreaterThan(0);
		Assertions.assertThat(fetchedMapWaypoint.getName()).isEqualTo(savedMapWaypoint.getName());
		Assertions.assertThat(fetchedMapWaypoint.getType()).isEqualTo(savedMapWaypoint.getType());
		Assertions.assertThat(fetchedMapWaypoint.getUniversity()).isEqualTo(savedMapWaypoint.getUniversity());
		Assertions.assertThat(fetchedMapWaypoint.getCoordinates()).isEqualTo(savedMapWaypoint.getCoordinates());
	}

    @Test
	public void MapWaypointService_GetAllMapWaypoint_ReturnsMapWaypoints() {
		// When
		when(mapWaypointRepository.findAll()).thenReturn(List.of(savedMapWaypoint));
		List<MapWaypoint> result = mapWaypointServiceImpl.getAllMapWaypoints();

		// Then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(0).getName()).isEqualTo(savedMapWaypoint.getName());
        Assertions.assertThat(result.get(0).getType()).isEqualTo(savedMapWaypoint.getType());
        Assertions.assertThat(result.get(0).getCoordinates()).isEqualTo(savedMapWaypoint.getCoordinates());
	}

    @Test
	public void MapWaypointService_getAllMapWaypointByUniversityId_ReturnsMapWaypoint() {
		// When
		when(mapWaypointRepository.findByUniversityId(savedMapWaypoint.getId())).thenReturn(List.of(savedMapWaypoint));
		List<MapWaypoint> result = mapWaypointServiceImpl.getAllMapWaypointsByUniversityId(savedMapWaypoint.getId());

		// Then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(0).getName()).isEqualTo(savedMapWaypoint.getName());
        Assertions.assertThat(result.get(0).getType()).isEqualTo(savedMapWaypoint.getType());
        Assertions.assertThat(result.get(0).getCoordinates()).isEqualTo(savedMapWaypoint.getCoordinates());
	}

    @Test
    public void MapWaypointService_DeleteMapWaypointById_ReturnsVoid() {
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            if (arg0 instanceof Integer id) {
                if (id.equals(savedMapWaypoint.getId())) {
                    return null;
                }
            }
            throw new IllegalArgumentException("Invalid argument(s) passed to deleteById method");
        }).when(mapWaypointRepository).deleteById(savedMapWaypoint.getId());

        mapWaypointServiceImpl.deleteMapWaypointById(savedMapWaypoint.getId());

        verify(mapWaypointRepository, times(1)).deleteById(savedMapWaypoint.getId());
    }
}
