package me.kreaktech.unility.universityfetch;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
import me.kreaktech.unility.controller.UniversityFetchController;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.entity.UniversityFetch;
import me.kreaktech.unility.service.UniversityFetchServiceImpl;

@WebMvcTest(controllers = UniversityFetchController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UniversityFetchControllerTest {

        @Autowired
        private MockMvc mockmvc;

        @MockBean
        private UniversityFetchServiceImpl universityFetchServiceImpl;

        @Autowired
        private ObjectMapper objectMapper;

        private UniversityFetch universityFetch;

        @BeforeEach
        void setUpControllerTest() throws JsonProcessingException, Exception {

                LocalDateTime activityDateTime = LocalDateTime.now().minusHours(1);

                University university = University.builder()
                                .name("some university1")
                                .id(1)
                                .build();

                universityFetch = UniversityFetch.builder()
                                .id(1)
                                .announcementsLastFetchMD5("some announcementsLastFetchMD5")
                                .cafeteriaLastFetchDate(Timestamp.valueOf(activityDateTime))
                                .language(Language.EN)
                                .university(university)
                                .build();
        }

        @Test
        public void UniversityFetchController_CreateUniversityFetch_ReturnCreated() throws Exception {
                // Arrange
                given(universityFetchServiceImpl.saveUniversityFetch(ArgumentMatchers.any()))
                                .willAnswer((invocation -> invocation.getArgument(0)));

                // Act
                ResultActions response = mockmvc.perform(post("/university-fetch")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(this.universityFetch)));

                // Assert
                response.andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.announcementsLastFetchMD5")
                                                .value(universityFetch.getAnnouncementsLastFetchMD5()));
        }

    @Test
    public void UniversityFetchController_GetUniversityFetchByUniversityNameAndLanguage_ReturnUniversityFetch() throws Exception {
        // Arrange
        when(universityFetchServiceImpl.getUniversityFetchByUniversityNameAndLanguage(
            ArgumentMatchers.anyString(), 
            ArgumentMatchers.any(Language.class))).thenReturn(universityFetch);


        // Act
        ResultActions response = mockmvc.perform(get("/university-fetch?universityName=some university1&language=EN")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.announcementsLastFetchMD5")
                                .value(universityFetch.getAnnouncementsLastFetchMD5()));
    }

    @Test
    public void UniversityFetchController_GetUniversityFetchById_ReturnUniversityFetch() throws Exception {
        // Arrange
        when(universityFetchServiceImpl.getUniversityFetchById(ArgumentMatchers.any())).thenReturn(universityFetch);

        // Act
        ResultActions response = mockmvc.perform(get("/university-fetch/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.universityFetch)));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.announcementsLastFetchMD5")
                                .value(universityFetch.getAnnouncementsLastFetchMD5()));
        }

        @Test
        public void UniversityFetchController_DeleteUniversityFetch_ReturnsVoid() throws Exception {
                // Arrange
                doNothing().when(universityFetchServiceImpl).deleteUniversityFetchById(ArgumentMatchers.any());

                // Act
                ResultActions response = mockmvc.perform(delete("/university-fetch/1")
                                .contentType(MediaType.APPLICATION_JSON));

                // Assert
                response.andExpect(MockMvcResultMatchers.status().isNoContent());
        }

}