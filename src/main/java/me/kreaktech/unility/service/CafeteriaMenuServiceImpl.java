package me.kreaktech.unility.service;

import java.sql.Timestamp;
import java.util.List;

import me.kreaktech.unility.entity.UniversityFetch;
import me.kreaktech.unility.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import me.kreaktech.unility.entity.CafeteriaMenu;
import me.kreaktech.unility.repository.CafeteriaMenuRepository;

@AllArgsConstructor
@Service
public class CafeteriaMenuServiceImpl implements CafeteriaMenuService {

	@Autowired
	CafeteriaMenuRepository cafeteriaMenuRepository;
	@Autowired
	private UniversityFetchServiceImpl universityFetchService;

	@Override
	public CafeteriaMenu getCafeteriaMenuById(Integer id) {
		return Utils.unwrap(cafeteriaMenuRepository.findById(id), id);
	}

	@Override
	public CafeteriaMenu saveCafeteriaMenu(CafeteriaMenu cafeteriaMenu) {
		return cafeteriaMenuRepository.save(cafeteriaMenu);
	}

	@Override
	public void deleteCafeteriaMenuById(Integer id) {
		cafeteriaMenuRepository.deleteById(id);
	}

	@Override
	public List<CafeteriaMenu> getAllCafeteriaMenu() {
		return cafeteriaMenuRepository.findAll();
	}

	@Override
	public List<CafeteriaMenu> getAllCafeteriaMenuByUniversityId(Integer universityId) {
		return cafeteriaMenuRepository.findByUniversityId(universityId);
	}

	@Override
	public void deleteAllCafeteriaMenusByUniversityId(Integer universityId) {
		cafeteriaMenuRepository.deleteAllByUniversityId(universityId);
		List<UniversityFetch> universityFetch = universityFetchService.findAllByUniversityId(universityId);
		for (UniversityFetch currUniversityFetch : universityFetch) {
			currUniversityFetch.setCafeteriaLastFetchDate(new Timestamp(0));
			universityFetchService.saveUniversityFetch(currUniversityFetch);
		}
	}
}