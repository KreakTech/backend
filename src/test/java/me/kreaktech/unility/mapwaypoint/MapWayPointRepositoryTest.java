package me.kreaktech.unility.mapwaypoint;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import me.kreaktech.unility.constants.Enum.MapWaypointType;
import me.kreaktech.unility.entity.MapWaypoint;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.repository.MapWaypointRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MapWaypointRepositoryTest {

    @Autowired
    private MapWaypointRepository mapWaypointRepository;

    MapWaypoint savedMapWaypoint;
    MapWaypoint mapWaypoint;

    @BeforeEach
    void setUpRepository() {

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
                .build();

        savedMapWaypoint = mapWaypointRepository.save(mapWaypoint);
    }

    @Test
    public void MapWaypointRepository_Save_ReturnMapWaypoint() {
        // Assert
        Assertions.assertThat(savedMapWaypoint).isNotNull();
        Assertions.assertThat(savedMapWaypoint.getId()).isGreaterThan(0);
        Assertions.assertThat(savedMapWaypoint.getName()).isEqualTo(mapWaypoint.getName());
        Assertions.assertThat(savedMapWaypoint.getType()).isEqualTo(mapWaypoint.getType());
        Assertions.assertThat(savedMapWaypoint.getCoordinates()).isEqualTo(mapWaypoint.getCoordinates());
    }

    @Test
    public void MapWaypointRepository_FindById_ReturnMapWaypoint() {
        // Act
        MapWaypoint fetchedMapWaypoint = mapWaypointRepository.findById(savedMapWaypoint.getId()).get();

        // Assert
        Assertions.assertThat(fetchedMapWaypoint).isNotNull();
        Assertions.assertThat(fetchedMapWaypoint.getId()).isGreaterThan(0);
        Assertions.assertThat(fetchedMapWaypoint.getName()).isEqualTo(savedMapWaypoint.getName());
        Assertions.assertThat(fetchedMapWaypoint.getType()).isEqualTo(savedMapWaypoint.getType());
        Assertions.assertThat(fetchedMapWaypoint.getCoordinates()).isEqualTo(savedMapWaypoint.getCoordinates());
    }

    @Test
    public void MapWaypointRepository_FindByUniversityId_ReturnMapWaypoint() {
        // Act
        List<MapWaypoint> fetchedMapWaypoints = mapWaypointRepository
                .findByUniversityId(savedMapWaypoint.getUniversity().getId());

        // Assert
        Assertions.assertThat(fetchedMapWaypoints).isNotNull();
        Assertions.assertThat(fetchedMapWaypoints).isNotEmpty();
        Assertions.assertThat(fetchedMapWaypoints).hasAtLeastOneElementOfType(MapWaypoint.class);
        Assertions.assertThat(fetchedMapWaypoints).hasSize(1);
    }

    @Test
    public void MapWaypointRepository_UpdateMapWaypoint_ReturnMapWaypoint() {
        // Act
        MapWaypoint fetchedMapWaypoint = mapWaypointRepository.findById(savedMapWaypoint.getId()).get();

        // Modifications
        fetchedMapWaypoint.setCoordinates("Some new coordinates");
        fetchedMapWaypoint.setName("Some new name");
        fetchedMapWaypoint.setType(MapWaypointType.MARKET);

        MapWaypoint updatedMapWaypoint = mapWaypointRepository.save(fetchedMapWaypoint);

        // Assert
        Assertions.assertThat(updatedMapWaypoint).isNotNull();
        Assertions.assertThat(updatedMapWaypoint.getName()).isEqualTo(fetchedMapWaypoint.getName())
                .isEqualTo("Some new name");
        Assertions.assertThat(updatedMapWaypoint.getType()).isEqualTo(fetchedMapWaypoint.getType());
        Assertions.assertThat(updatedMapWaypoint.getCoordinates()).isEqualTo(fetchedMapWaypoint.getCoordinates());
    }

    @Test
    public void MapWaypointRepository_DeleteMapWaypoint_ReturnMapWaypointIsNotPresent() {
        // Act
        mapWaypointRepository.deleteById(savedMapWaypoint.getId());
        Optional<MapWaypoint> fetchedMapWaypoint = mapWaypointRepository
                .findById(savedMapWaypoint.getId());

        // Assert
        Assertions.assertThat(fetchedMapWaypoint).isNotPresent();
    }
}
