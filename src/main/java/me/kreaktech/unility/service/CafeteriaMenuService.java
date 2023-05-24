package me.kreaktech.unility.service;

import java.util.List;

import me.kreaktech.unility.entity.CafeteriaMenu;

public interface CafeteriaMenuService {
    CafeteriaMenu getCafeteriaMenuById(Integer id);

    CafeteriaMenu saveCafeteriaMenu(CafeteriaMenu cafeteriaMenu);

    void deleteCafeteriaMenuById(Integer id);

    List<CafeteriaMenu> getAllCafeteriaMenu();

    List<CafeteriaMenu> getAllCafeteriaMenuByUniversityId(Integer universityId);

    void deleteAllCafeteriaMenusByUniversityId(Integer universityId);
}
