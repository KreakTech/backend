package me.kreaktech.unility.announcement;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import me.kreaktech.unility.entity.Announcement;
import me.kreaktech.unility.service.AnnouncementServiceImpl;
import me.kreaktech.unility.web.AnnouncementController;

@WebMvcTest(controllers = AnnouncementController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AnnouncementControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@MockBean
	private AnnouncementServiceImpl announcementService;

	@Autowired
	private ObjectMapper objectMapper;

	private Announcement announcement;

	@BeforeEach
	public void init() {
		LocalDateTime announcementDateTime = LocalDateTime.now().minusHours(1);

		announcement = Announcement.builder()
				.id(1)
				.title("Some Title")
				.link("https://someurl.com")
				.date(Timestamp.valueOf(announcementDateTime))
				.build();
	}

	@Test
	public void AnnouncementController_CreateAnnouncement_ReturnCreated() throws Exception {
		// Arrange
		given(announcementService.saveAnnouncement(ArgumentMatchers.any()))
				.willAnswer((invocation -> invocation.getArgument(0)));

		// Act
		ResultActions response = mockmvc.perform(post("/announcements")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(this.announcement)));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(announcement.getTitle())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.link", CoreMatchers.is(announcement.getLink())));
	}

	@Test
	public void AnnouncementController_GetAllAnnouncements_ReturnsAnnouncements() throws Exception {
		// Arrange
		List<Announcement> announcements = List.of(announcement);
		when(announcementService.getAllAnnouncements()).thenReturn(announcements);

		// Act
		ResultActions response = mockmvc.perform(get("/announcements/all")
				.contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(announcement.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value(announcement.getTitle()));
	}

	@Test
	public void AnnouncementController_GetAnnouncementById_ReturnAnnouncement() throws Exception {
		// Arrange
		when(announcementService.getAnnouncementById(ArgumentMatchers.any())).thenReturn(announcement);

		// Act
		ResultActions response = mockmvc.perform(get("/announcements/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(this.announcement)));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(announcement.getTitle())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.link", CoreMatchers.is(announcement.getLink())));
		}

	@Test
	public void AnnouncementController_DeleteAnnouncement_ReturnsVoid() throws Exception {
		// Arrange
		doNothing().when(announcementService).deleteAnnouncementById(ArgumentMatchers.any());

		// Act
		ResultActions response = mockmvc.perform(delete("/announcements/1")
				.contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}