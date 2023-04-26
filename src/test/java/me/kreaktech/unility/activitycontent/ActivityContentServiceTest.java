package me.kreaktech.unility.activitycontent;

@ExtendWith(MockitoExtension.class)
public class ActivityContentServiceTest {

	@Mock
	private ActivityContentRepository activityContentRepository;

	@InjectMocks
	private ActivityContentServiceImpl activityContentService;

	ActivityContent activityContent;
	LocalDateTime activityContentDateTime;

	@BeforeEach
	void setUpService(){
		// Arrange
		activityContentDateTime = LocalDateTime.now().minusHours(1);

		activityContent = ActivityContent.builder()
				.title("Some Title")
				.content("Some Content")
				.date(Timestamp.valueOf(activityContentDateTime))
				.build();
	}

	@Test
	public void ActivityContentService_CreateActivityContent_ReturnsActivityContent() {
		// Act
		when(activityContentRepository.save(Mockito.any(ActivityContent.class))).thenReturn(activityContent);
		ActivityContent savedActivityContent = activityContentService.saveActivityContent(activityContent);

		// Assert
		Assertions.assertThat(savedActivityContent).isNotNull();
		Assertions.assertThat(savedActivityContent.getTitle()).isEqualTo(activityContent.getTitle());
		Assertions.assertThat(savedActivityContent.getContent()).isEqualTo(activityContent.getContent());
		Assertions.assertThat(savedActivityContent.getDate()).isEqualTo(activityContent.getDate());
	}

	@Test
	public void ActivityContentService_GetActivityContentById_ReturnsActivityContent() {
		// Act
		when(activityContentRepository.findById(1)).thenReturn(Optional.ofNullable(activityContent));
		ActivityContent fetchedActivityContent = activityContentService.getActivityContentById(1);

		// Assert
		Assertions.assertThat(fetchedActivityContent).isNotNull();
		Assertions.assertThat(fetchedActivityContent.getTitle()).isEqualTo(activityContent.getTitle());
		Assertions.assertThat(fetchedActivityContent.getContent()).isEqualTo(activityContent.getContent());
		Assertions.assertThat(fetchedActivityContent.getDate()).isEqualTo(activityContent.getDate());
	}

	@Test
	public void ActivityContentService_DeleteActivityContentById_ReturnsVoid() {
        activityContentRepository.deleteById(activityContent.getId());
        ActivityContent deletedActivityContent = activityContentRepository.findById(activityContent.getId()).orElse(null);
        Assertions.assertThat(deletedActivityContent).isNull();
	}

}

