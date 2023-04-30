package me.kreaktech.unility.nutritioncontent;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import me.kreaktech.unility.entity.NutritionContent;
import me.kreaktech.unility.repository.NutritionContentRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class NutritionContentRepositoryTest {

        @Autowired
        private NutritionContentRepository nutritionContentRepository;

        NutritionContent savedNutritionContent;
        NutritionContent nutritionContent;

        @BeforeEach
        void setUpRepository() {
                nutritionContent = NutritionContent.builder()
                                .fatPercentage(10)
                                .proteinPercentage(10)
                                .carbohydratePercentage(10)
                                .energyCal(100)
                                .build();

                savedNutritionContent = nutritionContentRepository.save(nutritionContent);
        }

        @Test
        public void NutritionContentRepository_Save_ReturnNutritionContent() {
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
        public void NutritionContentRepository_FindById_ReturnNutritionContent() {
                // Act
                NutritionContent fetchedNutritionContent = nutritionContentRepository
                                .findById(savedNutritionContent.getId())
                                .get();

                // Assert
                Assertions.assertThat(fetchedNutritionContent).isNotNull();
                Assertions.assertThat(fetchedNutritionContent.getId()).isGreaterThan(0);
                Assertions.assertThat(fetchedNutritionContent.getCarbohydratePercentage())
                                .isEqualTo(savedNutritionContent.getCarbohydratePercentage());
                Assertions.assertThat(fetchedNutritionContent.getEnergyCal())
                                .isEqualTo(savedNutritionContent.getEnergyCal());
                Assertions.assertThat(fetchedNutritionContent.getFatPercentage())
                                .isEqualTo(savedNutritionContent.getFatPercentage());
                Assertions.assertThat(fetchedNutritionContent.getProteinPercentage())
                                .isEqualTo(savedNutritionContent.getProteinPercentage());
        }

        @Test
        public void NutritionContentRepository_UpdateNutritionContent_ReturnNutritionContent() {
                // Act
                NutritionContent fetchedNutritionContent = nutritionContentRepository
                                .findById(savedNutritionContent.getId())
                                .get();

                // Modifications
                fetchedNutritionContent.setCarbohydratePercentage(21);
                fetchedNutritionContent.setFatPercentage(21);
                fetchedNutritionContent.setProteinPercentage(21);

                NutritionContent updatedNutritionContent = nutritionContentRepository.save(fetchedNutritionContent);

                // Assert
                Assertions.assertThat(updatedNutritionContent).isNotNull();
                Assertions.assertThat(updatedNutritionContent.getId()).isGreaterThan(0);
                Assertions.assertThat(updatedNutritionContent.getCarbohydratePercentage())
                                .isEqualTo(fetchedNutritionContent.getCarbohydratePercentage());
                Assertions.assertThat(updatedNutritionContent.getEnergyCal())
                                .isEqualTo(fetchedNutritionContent.getEnergyCal());
                Assertions.assertThat(updatedNutritionContent.getFatPercentage())
                                .isEqualTo(fetchedNutritionContent.getFatPercentage());
                Assertions.assertThat(updatedNutritionContent.getProteinPercentage())
                                .isEqualTo(fetchedNutritionContent.getProteinPercentage());
        }

        @Test
        public void NutritionContentRepository_DeleteNutritionContent_ReturnNutritionContentIsNotPresent() {
                // Act
                nutritionContentRepository.deleteById(savedNutritionContent.getId());
                Optional<NutritionContent> fetchedNutritionContent = nutritionContentRepository
                                .findById(savedNutritionContent.getId());

                // Assert
                Assertions.assertThat(fetchedNutritionContent).isNotPresent();
        }
}
