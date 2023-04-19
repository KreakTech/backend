package me.kreaktech.unility.service;

import java.util.List;

import me.kreaktech.unility.entity.University;

public interface UniversityService {

	University getUniversity(Integer id);
	University getUniversityByName(String name);
	University createUniversity(University university);
	void deleteUniversity(Integer id);
	List<University> getUniversities();
}
