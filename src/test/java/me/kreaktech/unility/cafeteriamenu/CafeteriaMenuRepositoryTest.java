package me.kreaktech.unility.cafeteriamenu;

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

import me.kreaktech.unility.constants.Enum.Language;
import me.kreaktech.unility.constants.Enum.MealType;
import me.kreaktech.unility.entity.CafeteriaMenu;
import me.kreaktech.unility.entity.NutritionContent;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.repository.CafeteriaMenuRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CafeteriaMenuRepositoryTest {

    @Autowired
    private CafeteriaMenuRepository cafeteriaMenuRepository;

    CafeteriaMenu savedCafeteriaMenu;
    CafeteriaMenu cafeteriaMenu;

    @BeforeEach
    void setUpRepository() {
        Language lang = Language.EN;
        MealType mealType = MealType.BREAKFAST;
        LocalDateTime activityDateTime = LocalDateTime.now().minusHours(1);
        
		University university = University.builder()
				.name("some university1")
				.announcementsLastFetchDate(Timestamp.valueOf(activityDateTime))
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
                .build();

        savedCafeteriaMenu = cafeteriaMenuRepository.save(cafeteriaMenu);
    }

    @Test
    public void CafeteriaMenuRepository_Save_ReturnCafeteriaMenu() {
        // Assert
        Assertions.assertThat(savedCafeteriaMenu).isNotNull();
        Assertions.assertThat(savedCafeteriaMenu.getId()).isGreaterThan(0);
        Assertions.assertThat(savedCafeteriaMenu.getMealContent()).isEqualTo(cafeteriaMenu.getMealContent());
        Assertions.assertThat(savedCafeteriaMenu.getDateServed()).isEqualTo(cafeteriaMenu.getDateServed());
        Assertions.assertThat(savedCafeteriaMenu.getLanguage()).isEqualTo(cafeteriaMenu.getLanguage());
        Assertions.assertThat(savedCafeteriaMenu.getMealType()).isEqualTo(cafeteriaMenu.getMealType());
    }

    @Test
    public void CafeteriaMenuRepository_FindById_ReturnCafeteriaMenu() {
        // Act
        CafeteriaMenu fetchedCafeteriaMenu = cafeteriaMenuRepository.findById(savedCafeteriaMenu.getId()).get();

        // Assert
        Assertions.assertThat(fetchedCafeteriaMenu).isNotNull();
        Assertions.assertThat(fetchedCafeteriaMenu.getId()).isGreaterThan(0);
        Assertions.assertThat(fetchedCafeteriaMenu.getMealContent()).isEqualTo(savedCafeteriaMenu.getMealContent());
        Assertions.assertThat(fetchedCafeteriaMenu.getDateServed()).isEqualTo(savedCafeteriaMenu.getDateServed());
        Assertions.assertThat(fetchedCafeteriaMenu.getLanguage()).isEqualTo(savedCafeteriaMenu.getLanguage());
        Assertions.assertThat(fetchedCafeteriaMenu.getMealType()).isEqualTo(savedCafeteriaMenu.getMealType());
    }

    @Test
    public void CafeteriaMenuRepository_FindByUniversityId_ReturnCafeteriaMenu() {
        // Act
        List<CafeteriaMenu> fetchedCafeteriaMenus = cafeteriaMenuRepository.findByUniversityId(savedCafeteriaMenu.getUniversity().getId());

        // Assert
        Assertions.assertThat(fetchedCafeteriaMenus).isNotNull();
        Assertions.assertThat(fetchedCafeteriaMenus).isNotEmpty();
        Assertions.assertThat(fetchedCafeteriaMenus).hasAtLeastOneElementOfType(CafeteriaMenu.class);
        Assertions.assertThat(fetchedCafeteriaMenus).hasSize(1);
    }

    @Test
    public void CafeteriaMenuRepository_UpdateCafeteriaMenu_ReturnCafeteriaMenu() {
        // Act
        CafeteriaMenu fetchedCafeteriaMenu = cafeteriaMenuRepository.findById(savedCafeteriaMenu.getId()).get();

        // Modifications
        fetchedCafeteriaMenu.setLanguage(Language.TR);
        fetchedCafeteriaMenu.setMealType(MealType.LUNCH);
        fetchedCafeteriaMenu.setMealContent("Some new content");

        CafeteriaMenu updatedCafeteriaMenu = cafeteriaMenuRepository.save(fetchedCafeteriaMenu);

        // Assert
        Assertions.assertThat(updatedCafeteriaMenu).isNotNull();
        Assertions.assertThat(updatedCafeteriaMenu.getMealContent()).isEqualTo(fetchedCafeteriaMenu.getMealContent()).isEqualTo("Some new content");
        Assertions.assertThat(updatedCafeteriaMenu.getLanguage()).isEqualTo(fetchedCafeteriaMenu.getLanguage());
        Assertions.assertThat(updatedCafeteriaMenu.getMealType()).isEqualTo(fetchedCafeteriaMenu.getMealType());
    }

    @Test
    public void CafeteriaMenuRepository_DeleteCafeteriaMenu_ReturnCafeteriaMenuIsNotPresent() {
        // Act
        cafeteriaMenuRepository.deleteById(savedCafeteriaMenu.getId());
        Optional<CafeteriaMenu> fetchedCafeteriaMenu = cafeteriaMenuRepository
                .findById(savedCafeteriaMenu.getId());

        // Assert
        Assertions.assertThat(fetchedCafeteriaMenu).isNotPresent();
    }
}
