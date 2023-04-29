package me.kreaktech.unility.service;

import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.repository.UniversityRepository;
import me.kreaktech.unility.utils.Utils;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UniversityServiceImpl implements UniversityService {

	private UniversityRepository universityRepository;

	@Override
	public University getUniversity(Integer id) {
		Optional<University> university = universityRepository.findById(id);
		return Utils.unwrap(university, id);
	}

	@Override
	public University createUniversity(University university) {
		return universityRepository.save(university);
	}

	@Override
	public void deleteUniversity(Integer id) {
		universityRepository.deleteById(id);
		return;
	}

	@Override
	public List<University> getUniversities() {
		return universityRepository.findAll();
	}

	@Override
	public University getUniversityByName(String name) {
		Optional<University> university = universityRepository.findByName(name);
		return Utils.unwrap(university, name);
	}
}
