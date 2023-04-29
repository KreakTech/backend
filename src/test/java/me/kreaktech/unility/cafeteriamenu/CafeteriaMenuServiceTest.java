package me.kreaktech.unility.cafeteriamenu;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import me.kreaktech.unility.constants.Enum.Language;
import me.kreaktech.unility.constants.Enum.MealType;
import me.kreaktech.unility.entity.CafeteriaMenu;
import me.kreaktech.unility.entity.NutritionContent;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.repository.CafeteriaMenuRepository;
import me.kreaktech.unility.service.CafeteriaMenuServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CafeteriaMenuServiceTest {

	@Mock
	private CafeteriaMenuRepository cafeteriaMenuRepository;

	@InjectMocks
	private CafeteriaMenuServiceImpl cafeteriaMenuServiceImpl;

	CafeteriaMenu savedCafeteriaMenu;
    CafeteriaMenu cafeteriaMenu;

	@BeforeEach
	void setUpService() {

        Language lang = Language.EN;
        MealType mealType = MealType.BREAKFAST;
        LocalDateTime activityDateTime = LocalDateTime.now().minusHours(1);
        
		University university = University.builder()
				.name("some university")
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
                .id(1)
                .build();

		// Act
		when(cafeteriaMenuRepository.save(Mockito.any(CafeteriaMenu.class))).thenReturn(cafeteriaMenu);
		savedCafeteriaMenu = cafeteriaMenuServiceImpl.saveCafeteriaMenu(cafeteriaMenu);

	}

	@Test
	public void CafeteriaMenuService_CreateCafeteriaMenu_ReturnsCafeteriaMenu() {
        // Assert
        Assertions.assertThat(savedCafeteriaMenu).isNotNull();
        Assertions.assertThat(savedCafeteriaMenu.getId()).isGreaterThan(0);
        Assertions.assertThat(savedCafeteriaMenu.getMealContent()).isEqualTo(cafeteriaMenu.getMealContent());
        Assertions.assertThat(savedCafeteriaMenu.getDateServed()).isEqualTo(cafeteriaMenu.getDateServed());
        Assertions.assertThat(savedCafeteriaMenu.getLanguage()).isEqualTo(cafeteriaMenu.getLanguage());
        Assertions.assertThat(savedCafeteriaMenu.getMealType()).isEqualTo(cafeteriaMenu.getMealType());
	}

	@Test
	public void CafeteriaMenuService_GetCafeteriaMenuById_ReturnsCafeteriaMenu() {
		// Act
		when(cafeteriaMenuRepository.findById(1)).thenReturn(Optional.ofNullable(savedCafeteriaMenu));
		CafeteriaMenu fetchedCafeteriaMenu = cafeteriaMenuServiceImpl.getCafeteriaMenuById(1);

        // Assert
        Assertions.assertThat(fetchedCafeteriaMenu).isNotNull();
        Assertions.assertThat(fetchedCafeteriaMenu.getId()).isGreaterThan(0);
        Assertions.assertThat(fetchedCafeteriaMenu.getMealContent()).isEqualTo(savedCafeteriaMenu.getMealContent());
        Assertions.assertThat(fetchedCafeteriaMenu.getDateServed()).isEqualTo(savedCafeteriaMenu.getDateServed());
        Assertions.assertThat(fetchedCafeteriaMenu.getLanguage()).isEqualTo(savedCafeteriaMenu.getLanguage());
        Assertions.assertThat(fetchedCafeteriaMenu.getMealType()).isEqualTo(savedCafeteriaMenu.getMealType());
	}
     
    //yenilenecek
    @Test
	public void CafeteriaMenuService_GetAllCafeteriaMenu_ReturnsCafeteriaMenus() {
		// When
		when(cafeteriaMenuRepository.findAll()).thenReturn(List.of(savedCafeteriaMenu));
		List<CafeteriaMenu> result = cafeteriaMenuServiceImpl.getAllCafeteriaMenu();

		// Then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(0).getMealContent()).isEqualTo(savedCafeteriaMenu.getMealContent());
        Assertions.assertThat(result.get(0).getLanguage()).isEqualTo(savedCafeteriaMenu.getLanguage());
        Assertions.assertThat(result.get(0).getMealType()).isEqualTo(savedCafeteriaMenu.getMealType());
	}

    //yenilenecek
    @Test
	public void CafeteriaMenuService_getAllCafeteriaMenuByUniversityId_ReturnsCafeteriaMenu() {
		// When
		when(cafeteriaMenuRepository.findByUniversityId(savedCafeteriaMenu.getId())).thenReturn(List.of(savedCafeteriaMenu));
		List<CafeteriaMenu> result = cafeteriaMenuServiceImpl.getAllCafeteriaMenuByUniversityId(savedCafeteriaMenu.getId());

		// Then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(0).getMealContent()).isEqualTo(savedCafeteriaMenu.getMealContent());
        Assertions.assertThat(result.get(0).getLanguage()).isEqualTo(savedCafeteriaMenu.getLanguage());
        Assertions.assertThat(result.get(0).getMealType()).isEqualTo(savedCafeteriaMenu.getMealType());
	}

	@Test
	public void CafeteriaMenuService_DeleteCafeteriaMenuById_ReturnsVoid() {
		doAnswer(invocation -> {
			Object arg0 = invocation.getArgument(0);
			if (arg0 instanceof Integer) {
				Integer id = (Integer) arg0;
				if (id == savedCafeteriaMenu.getId()) {
					return null;
				}
			}
			throw new IllegalArgumentException("Invalid argument(s) passed to deleteById method");
		}).when(cafeteriaMenuRepository).deleteById(savedCafeteriaMenu.getId());

		cafeteriaMenuServiceImpl.deleteCafeteriaMenuById(savedCafeteriaMenu.getId());

		verify(cafeteriaMenuRepository, times(1)).deleteById(savedCafeteriaMenu.getId());
	}
}
