package me.kreaktech.unility.activity;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.sql.Timestamp;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.kreaktech.unility.constants.Enum.Language;
import me.kreaktech.unility.constants.Enum.PhysicalStatus;
import me.kreaktech.unility.controller.ActivityController;
import me.kreaktech.unility.entity.Activity;
import me.kreaktech.unility.entity.ActivityContent;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.service.ActivityServiceImpl;

@WebMvcTest(controllers = ActivityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ActivityControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private ActivityServiceImpl activityServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private Activity activity;

    @BeforeEach
	void setUpControllerTest() throws JsonProcessingException, Exception{
        LocalDateTime activityDateTime = LocalDateTime.now().minusHours(1);
        //Arrange
        University university = University.builder()
            .name("some university")
            .announcementsLastFetchDate(Timestamp.valueOf(activityDateTime))
            .build();

        ActivityContent activityContent = ActivityContent.builder()
            .details("some details")
            .title("some title")
            .organizer("some organizer")
            .activityLanguage(Language.EN)
            .activityDuration(Timestamp.valueOf(activityDateTime))
            .physicalStatus(PhysicalStatus.FACETOFACE)
            .build();
	
		activity = Activity.builder()
				.university(university)
                .activityContent(activityContent)
                .date(Timestamp.valueOf(activityDateTime))
                .id(1)
                .build();
	}

    @Test
    public void ActivityController_CreateActivity_ReturnCreated() throws Exception {
        // Arrange
        given(activityServiceImpl.saveActivity(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        // Act
        ResultActions response = mockmvc.perform(post("/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.activity)));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.university.name").value("some university"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activityContent.details").value("some details"));
    }

    @Test
    public void ActivityController_GetAllActivities_ReturnsActivities() throws Exception {
        // Arrange
        List<Activity> activities = List.of(activity);
        when(activityServiceImpl.getAllActivities()).thenReturn(activities);

        // Act
        ResultActions response = mockmvc.perform(get("/activities/all")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(activity.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].university.name").value("some university"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].activityContent.details").value("some details"));
    }

    @Test
	public void ActivityController_GetActivityById_ReturnActivity() throws Exception {
		// Arrange
		when(activityServiceImpl.getActivityById(ArgumentMatchers.any())).thenReturn(activity);

		// Act
		ResultActions response = mockmvc.perform(get("/activities/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(this.activity)));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.university.name").value("some university"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activityContent.details").value("some details"));
		}

    @Test
    public void ActivityController_DeleteActivity_ReturnsVoid() throws Exception {
        // Arrange
        doNothing().when(activityServiceImpl).deleteActivityById(ArgumentMatchers.any());

        // Act
        ResultActions response = mockmvc.perform(delete("/activities/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
