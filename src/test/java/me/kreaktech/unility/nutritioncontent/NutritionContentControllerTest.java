package me.kreaktech.unility.nutritioncontent;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

import me.kreaktech.unility.web.NutritionContentController;
import me.kreaktech.unility.entity.NutritionContent;
import me.kreaktech.unility.service.NutritionContentServiceImpl;

@WebMvcTest(controllers = NutritionContentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class NutritionContentControllerTest {

        @Autowired
        private MockMvc mockmvc;

        @MockBean
        private NutritionContentServiceImpl nutritionContentServiceImpl;

        @Autowired
        private ObjectMapper objectMapper;

        private NutritionContent nutritionContent;

        @BeforeEach
        void setUpControllerTest() {
                nutritionContent = NutritionContent.builder()
                                .fatPercentage(10)
                                .proteinPercentage(10)
                                .carbohydratePercentage(10)
                                .energyCal(100)
                                .id(1)
                                .build();
        }

        @Test
        public void NutritionContentController_CreateNutritionContent_ReturnCreated() throws Exception {
                // Arrange
                given(nutritionContentServiceImpl.saveNutritionContent(ArgumentMatchers.any()))
                                .willAnswer((invocation -> invocation.getArgument(0)));

                // Act
                ResultActions response = mockmvc.perform(post("/nutrition-contents")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(this.nutritionContent)));

                // Assert
                response.andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.carbohydratePercentage")
                                                .value(nutritionContent.getCarbohydratePercentage()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.proteinPercentage")
                                                .value(nutritionContent.getProteinPercentage()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.fatPercentage")
                                                .value(nutritionContent.getFatPercentage()));
        }

        @Test
	public void NutritionContentController_GetNutritionContentById_ReturnNutritionContent() throws Exception {
		// Arrange
		when(nutritionContentServiceImpl.getNutritionContentById(ArgumentMatchers.any())).thenReturn(nutritionContent);

		// Act
		ResultActions response = mockmvc.perform(get("/nutrition-contents/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(this.nutritionContent)));

		// Assert
		response.andExpect(MockMvcResultMatchers.status().isOk())        
                        .andExpect(MockMvcResultMatchers.jsonPath("$.carbohydratePercentage")
                               .value(nutritionContent.getCarbohydratePercentage()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.proteinPercentage")
                                .value(nutritionContent.getProteinPercentage()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.fatPercentage")
                                .value(nutritionContent.getFatPercentage()));
        }

        @Test
        public void NutritionContentController_DeleteNutritionContent_ReturnsVoid() throws Exception {
                // Arrange
                doNothing().when(nutritionContentServiceImpl).deleteNutritionContentById(ArgumentMatchers.any());

                // Act
                ResultActions response = mockmvc.perform(delete("/nutrition-contents/1")
                                .contentType(MediaType.APPLICATION_JSON));

                // Assert
                response.andExpect(MockMvcResultMatchers.status().isNoContent());
        }

}
