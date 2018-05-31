package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    boolean existsByNameIgnoreCase(String name);
}

