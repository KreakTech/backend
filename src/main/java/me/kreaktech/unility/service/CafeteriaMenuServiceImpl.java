package me.kreaktech.unility.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import me.kreaktech.unility.entity.CafeteriaMenu;
import me.kreaktech.unility.repository.CafeteriaMenuRepository;

@AllArgsConstructor
@Service
public class CafeteriaMenuServiceImpl implements CafeteriaMenuService {

	CafeteriaMenuRepository cafeteriaMenuRepository;

	@Override
	public CafeteriaMenu getCafeteriaMenuById(Integer id) {
		return cafeteriaMenuRepository.findById(id).orElse(null);
	}

	@Override
	public CafeteriaMenu saveCafeteriaMenu(CafeteriaMenu cafeteriaMenu) {
		return cafeteriaMenuRepository.save(cafeteriaMenu);
	}

	@Override
	public void deleteCafeteriaMenuById(Integer id) {
		cafeteriaMenuRepository.deleteById(id);
		return;
	}

	@Override
	public List<CafeteriaMenu> getAllCafeteriaMenu() {
		return cafeteriaMenuRepository.findAll();
	}

	@Override
	public List<CafeteriaMenu> getAllCafeteriaMenuByUniversityId(Integer universityId) {
		return cafeteriaMenuRepository.findByUniversityId(universityId);
	};
}
