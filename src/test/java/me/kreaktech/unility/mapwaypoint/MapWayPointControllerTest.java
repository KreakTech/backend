package me.kreaktech.unility.mapwaypoint;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.time.LocalDateTime;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.kreaktech.unility.constants.Enum.MapWaypointType;
import me.kreaktech.unility.web.MapWaypointController;
import me.kreaktech.unility.entity.MapWaypoint;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.service.MapWaypointServiceImpl;

@WebMvcTest(controllers = MapWaypointController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MapWaypointControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@MockBean
	private MapWaypointServiceImpl mapWaypointServiceImpl;

	@Autowired
	private ObjectMapper objectMapper;

	private MapWaypoint mapWaypoint;

	@BeforeEach
	void setUpControllerTest() {

		University university = University.builder()
				.name("some university1")
				.id(1)
				.build();

		mapWaypoint = MapWaypoint.builder()
				.id(1)
				.name("some name")
				.coordinates("some coordinate")
				.type(MapWaypointType.UTILITY)
				.university(university)
				.build();
	}

	@Test
	public void MapWaypointController_CreateMapWaypoint_ReturnCreated() throws Exception {
		// Arrange
		given(mapWaypointServiceImpl.saveMapWaypoint(ArgumentMatchers.any()))
				.willAnswer((invocation -> invocation.getArgument(0)));

		// Act
		ResultActions response = mockmvc.perform(post("/map-waypoints")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(this.mapWaypoint)));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.name").value(mapWaypoint.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.coordinates")
						.value(mapWaypoint.getCoordinates()));
	}

	@Test
	public void MapWaypointController_GetAllMapWaypoints_ReturnsMapWaypoints() throws Exception {
		// Arrange
		List<MapWaypoint> mapWaypoints = List.of(mapWaypoint);
		when(mapWaypointServiceImpl.getAllMapWaypoints()).thenReturn(mapWaypoints);

		// Act
		ResultActions response = mockmvc.perform(get("/map-waypoints/all")
				.contentType(MediaType.APPLICATION_JSON));

		// Asserts
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.[0].name").value(mapWaypoint.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].coordinates")
						.value(mapWaypoint.getCoordinates()));
	}

	@Test
	public void MapWaypointController_GetAllMapWaypointsByUniversityId_ReturnMapWaypoints() throws Exception {
		// Arrange
		List<MapWaypoint> mapWaypoints = List.of(mapWaypoint);
		when(mapWaypointServiceImpl.getAllMapWaypointsByUniversityId(ArgumentMatchers.any()))
				.thenReturn(mapWaypoints);

		// Act
		ResultActions response = mockmvc.perform(get("/map-waypoints/all/1")
				.contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.[0].name").value(mapWaypoint.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].coordinates")
						.value(mapWaypoint.getCoordinates()));
	}

	@Test
	public void MapWaypointController_GetMapWaypointById_ReturnMapWaypoint() throws Exception {
		// Arrange
		when(mapWaypointServiceImpl.getMapWaypointById(ArgumentMatchers.any())).thenReturn(mapWaypoint);

		// Act
		ResultActions response = mockmvc.perform(get("/map-waypoints/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(this.mapWaypoint)));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.name").value(mapWaypoint.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.coordinates")
						.value(mapWaypoint.getCoordinates()));
		}

	@Test
	public void MapWaypointController_DeleteMapWaypoint_ReturnsVoid() throws Exception {
		// Arrange
		doNothing().when(mapWaypointServiceImpl).deleteMapWaypointById(ArgumentMatchers.any());

		// Act
		ResultActions response = mockmvc.perform(delete("/map-waypoints/1")
				.contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

}
