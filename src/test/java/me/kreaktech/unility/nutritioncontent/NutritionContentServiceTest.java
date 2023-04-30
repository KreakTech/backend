package me.kreaktech.unility.nutritioncontent;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import me.kreaktech.unility.entity.NutritionContent;
import me.kreaktech.unility.repository.NutritionContentRepository;
import me.kreaktech.unility.service.NutritionContentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class NutritionContentServiceTest {

    @Mock
    private NutritionContentRepository nutritionContentRepository;

    @InjectMocks
    private NutritionContentServiceImpl nutritionContentServiceImpl;

    NutritionContent savedNutritionContent;
    NutritionContent nutritionContent;

    @BeforeEach
    void setUpService() {
        nutritionContent = NutritionContent.builder()
                .fatPercentage(10)
                .proteinPercentage(10)
                .carbohydratePercentage(10)
                .energyCal(100)
                .id(1)
                .build();

        // Act
        when(nutritionContentRepository.save(Mockito.any(NutritionContent.class))).thenReturn(nutritionContent);
        savedNutritionContent = nutritionContentServiceImpl.saveNutritionContent(nutritionContent);
    }

    @Test
    public void NutritionContentService_CreateNutritionContent_ReturnsNutritionContent() {
        // Assert
        Assertions.assertThat(savedNutritionContent).isNotNull();
        Assertions.assertThat(savedNutritionContent.getId()).isGreaterThan(0);
        Assertions.assertThat(savedNutritionContent.getCarbohydratePercentage())
                .isEqualTo(nutritionContent.getCarbohydratePercentage());
        Assertions.assertThat(savedNutritionContent.getEnergyCal()).isEqualTo(nutritionContent.getEnergyCal());
        Assertions.assertThat(savedNutritionContent.getFatPercentage())
                .isEqualTo(nutritionContent.getFatPercentage());
        Assertions.assertThat(savedNutritionContent.getProteinPercentage())
                .isEqualTo(nutritionContent.getProteinPercentage());
    }

    @Test
	public void NutritionContentService_GetNutritionContentById_ReturnsNutritionContent() {
		// Act
		when(nutritionContentRepository.findById(1)).thenReturn(Optional.ofNullable(savedNutritionContent));
		NutritionContent fetchedNutritionContent = nutritionContentServiceImpl.getNutritionContentById(1);

                // Assert
                Assertions.assertThat(fetchedNutritionContent).isNotNull();
                Assertions.assertThat(fetchedNutritionContent.getId()).isGreaterThan(0);
                Assertions.assertThat(fetchedNutritionContent.getCarbohydratePercentage())
                                .isEqualTo(savedNutritionContent.getCarbohydratePercentage());
                Assertions.assertThat(fetchedNutritionContent.getEnergyCal()).isEqualTo(savedNutritionContent.getEnergyCal());
                Assertions.assertThat(fetchedNutritionContent.getFatPercentage())
                                .isEqualTo(savedNutritionContent.getFatPercentage());
                Assertions.assertThat(fetchedNutritionContent.getProteinPercentage())
                                .isEqualTo(savedNutritionContent.getProteinPercentage());
	}

    @Test
    public void NutritionContentService_DeleteNutritionContentById_ReturnsVoid() {
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            if (arg0 instanceof Integer) {
                Integer id = (Integer) arg0;
                if (id == savedNutritionContent.getId()) {
                    return null;
                }
            }
            throw new IllegalArgumentException("Invalid argument(s) passed to deleteById method");
        }).when(nutritionContentRepository).deleteById(savedNutritionContent.getId());

        nutritionContentServiceImpl.deleteNutritionContentById(savedNutritionContent.getId());

        verify(nutritionContentRepository, times(1)).deleteById(savedNutritionContent.getId());
    }
}
