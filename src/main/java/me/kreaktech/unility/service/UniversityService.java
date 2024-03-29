package me.kreaktech.unility.service;

import java.util.List;

import me.kreaktech.unility.entity.University;

public interface UniversityService {
	University getUniversity(Integer id);

	University createUniversity(University university);

	University getUniversityByName(String name);

	void deleteUniversity(Integer id);

	List<University> getUniversities();
}
