package me.kreaktech.unility.cafeteriamenu;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.kreaktech.unility.constants.Enum.Language;
import me.kreaktech.unility.constants.Enum.MealType;
import me.kreaktech.unility.controller.CafeteriaMenuController;
import me.kreaktech.unility.entity.CafeteriaMenu;
import me.kreaktech.unility.entity.NutritionContent;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.service.CafeteriaMenuServiceImpl;

@WebMvcTest(controllers = CafeteriaMenuController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CafeteriaMenuControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@MockBean
	private CafeteriaMenuServiceImpl cafeteriaMenuServiceImpl;

	@Autowired
	private ObjectMapper objectMapper;

	private CafeteriaMenu cafeteriaMenu;

	@BeforeEach
	void setUpControllerTest() throws JsonProcessingException, Exception {
		Language lang = Language.EN;
		MealType mealType = MealType.BREAKFAST;
		LocalDateTime activityDateTime = LocalDateTime.now().minusHours(1);

		University university = University.builder()
				.name("some university1")
				.id(1)
				.build();

		NutritionContent nutritionContent = NutritionContent.builder()
				.fatPercentage(10)
				.build();

		String mealContent = "some content";

		cafeteriaMenu = CafeteriaMenu.builder()
				.language(lang).mealContent(mealContent)
				.nutritionContent(nutritionContent)
				.mealType(mealType)
				.university(university)
				.mealContent(mealContent)
				.dateServed(Timestamp.valueOf(activityDateTime))
				.id(1)
				.build();
	}

	@Test
	public void CafeteriaMenuController_CreateCafeteriaMenu_ReturnCreated() throws Exception {
		// Arrange
		given(cafeteriaMenuServiceImpl.saveCafeteriaMenu(ArgumentMatchers.any()))
				.willAnswer((invocation -> invocation.getArgument(0)));

		// Act
		ResultActions response = mockmvc.perform(post("/cafeteriamenu")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(this.cafeteriaMenu)));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.mealType").value(String.valueOf(cafeteriaMenu.getMealType())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.mealContent")
						.value(String.valueOf(cafeteriaMenu.getMealContent())));
	}

	@Test
	public void CafeteriaMenuController_GetAllCafeteriaMenus_ReturnsCafeteriaMenus() throws Exception {
		// Arrange
		List<CafeteriaMenu> cafeteriaMenus = List.of(cafeteriaMenu);
		when(cafeteriaMenuServiceImpl.getAllCafeteriaMenu()).thenReturn(cafeteriaMenus);

		// Act
		ResultActions response = mockmvc.perform(get("/cafeteriamenu/all")
				.contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].mealType")
						.value(String.valueOf(cafeteriaMenu.getMealType())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].mealContent")
						.value(String.valueOf(cafeteriaMenu.getMealContent())));
	}

	@Test
	public void CafeteriaMenuController_GetAllCafeteriaMenusByUniversityId_ReturnCafeteriaMenus() throws Exception {
		// Arrange
		List<CafeteriaMenu> cafeteriaMenus = List.of(cafeteriaMenu);
		when(cafeteriaMenuServiceImpl.getAllCafeteriaMenuByUniversityId(ArgumentMatchers.any()))
				.thenReturn(cafeteriaMenus);

		// Act
		ResultActions response = mockmvc.perform(get("/cafeteriamenu/all/1")
				.contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].mealType")
						.value(String.valueOf(cafeteriaMenu.getMealType())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].mealContent")
						.value(String.valueOf(cafeteriaMenu.getMealContent())));
	}

	@Test
	public void CafeteriaMenuController_GetCafeteriaMenuById_ReturnCafeteriaMenu() throws Exception {
		// Arrange
		when(cafeteriaMenuServiceImpl.getCafeteriaMenuById(ArgumentMatchers.any())).thenReturn(cafeteriaMenu);

		// Act
		ResultActions response = mockmvc.perform(get("/cafeteriamenu/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(this.cafeteriaMenu)));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mealType").value(String.valueOf(cafeteriaMenu.getMealType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mealContent").value(String.valueOf(cafeteriaMenu.getMealContent())));
		}

	@Test
	public void CafeteriaMenuController_DeleteCafeteriaMenu_ReturnsVoid() throws Exception {
		// Arrange
		doNothing().when(cafeteriaMenuServiceImpl).deleteCafeteriaMenuById(ArgumentMatchers.any());

		// Act
		ResultActions response = mockmvc.perform(delete("/cafeteriamenu/1")
				.contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

}
