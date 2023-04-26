package me.kreaktech.unility.activitycontent;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
import me.kreaktech.unility.controller.ActivityContentController;
import me.kreaktech.unility.entity.ActivityContent;
import me.kreaktech.unility.service.ActivityContentServiceImpl;

@WebMvcTest(controllers = ActivityContentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ActivityContentControllerTest {

        @Autowired
        private MockMvc mockmvc;

        @MockBean
        private ActivityContentServiceImpl activityContentServiceImpl;

        @Autowired
        private ObjectMapper objectMapper;

        private ActivityContent activityContent;

        @BeforeEach
        void setUpControllerTest() throws JsonProcessingException, Exception {

                LocalDateTime activityContentDateTime = LocalDateTime.now().minusHours(1);

                activityContent = ActivityContent.builder()
                                .details("some details")
                                .title("some title")
                                .organizer("some organizer")
                                .activityLanguage(Language.EN)
                                .activityDuration(Timestamp.valueOf(activityContentDateTime))
                                .physicalStatus(PhysicalStatus.FACETOFACE)
                                .id(1)
                                .build();
        }

        @Test
        public void ActivityContentController_CreateActivityContent_ReturnCreated() throws Exception {
                // Arrange
                given(activityContentServiceImpl.saveActivityContent(ArgumentMatchers.any()))
                                .willAnswer((invocation -> invocation.getArgument(0)));

                // Act
                ResultActions response = mockmvc.perform(post("/activity-contents")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(this.activityContent)));

                // Assert
                response.andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("some title"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.organizer").value("some organizer"));
        }

    @Test
	public void ActivityContentController_GetActivityContentById_ReturnActivityContent() throws Exception {
		// Arrange
		when(activityContentServiceImpl.getActivityContentById(ArgumentMatchers.any())).thenReturn(activityContent);

		// Act
		ResultActions response = mockmvc.perform(get("/activity-contents/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(this.activityContent)));

		// Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("some title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.organizer").value("some organizer"));
		}

    @Test
    public void ActivityContentController_GetActivityContentByTitle_ReturnActivityContent() throws Exception {
        // Arrange
        when(activityContentServiceImpl.getActivityContentByTitle(ArgumentMatchers.any())).thenReturn(activityContent);

        // Act
        ResultActions response = mockmvc.perform(get("/activity-contents?title=some title")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.activityContent)));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("some title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.organizer").value("some organizer"));
        }

        @Test
        public void ActivityContentController_DeleteActivityContent_ReturnsVoid() throws Exception {
                // Arrange
                doNothing().when(activityContentServiceImpl).deleteActivityContentById(ArgumentMatchers.any());

                // Act
                ResultActions response = mockmvc.perform(delete("/activity-contents/1")
                                .contentType(MediaType.APPLICATION_JSON));

                // Assert
                response.andExpect(MockMvcResultMatchers.status().isNoContent());
        }
}
