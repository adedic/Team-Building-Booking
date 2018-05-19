package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.Category;
import hr.tvz.java.teambuildingbooking.repository.CategoryRepository;
import hr.tvz.java.teambuildingbooking.service.CategoryService;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
=======
>>>>>>> cd5ca9b1b63663291ce4888d8401dea9bdca410e
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> fetchCategories() {
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
