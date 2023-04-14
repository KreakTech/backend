package me.kreaktech.unility.service;

import java.util.List;

import me.kreaktech.unility.entity.University;

public interface UniversityService {

	University getUniversity(int id);
	University createUniversity(University university);
	void deleteUniversity(int id);
	List<University> getUniversities();
}
