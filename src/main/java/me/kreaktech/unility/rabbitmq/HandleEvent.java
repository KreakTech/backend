package me.kreaktech.unility.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.kreaktech.unility.constants.Enum.EventType;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.entity.events.EventBody;
import me.kreaktech.unility.service.UniversityServiceImpl;
import me.kreaktech.unility.utils.BilkentUniversityUtils;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class HandleEvent {
	@Autowired
	private UniversityServiceImpl universityService;
	@Autowired
	private BilkentUniversityUtils bilkentUniversityUtils;

	public void processEvent(EventBody eventBody) {
		String universityName = eventBody.getUniversityName();
		// Verify validity of universityName
		University university = universityService.getUniversityByName(universityName);
		if (university != null) {
			eventBody.setUniversityId(university.getId());
		} else {
			// Invalid university, ignore this event.
			return;
		}

		if (universityName.equals("Bilkent University")) {
			handleBilkentUniversityEvent(eventBody, university);
		}
	}

	public void handleBilkentUniversityEvent(EventBody eventBody, University university) {
		EventType eventType = eventBody.getType();
		switch (eventType) {
			case DAILY_MEAL -> bilkentUniversityUtils.handleDailyMealsEvent(eventBody, university);
			case ANNOUNCEMENT -> bilkentUniversityUtils.handleAnnouncementEvent(eventBody, university);
			case STUDENT_ACTIVITIES -> bilkentUniversityUtils.handleStudentActivitiesEvent(eventBody, university);
			default -> {}
		}
	}
}
