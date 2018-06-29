package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    private static final String EXISTING_CATEGORY_NAME = "Sport";
    private static final String NON_EXISTING_CATEGORY_NAME = "avantura";

    @Autowired
    private CategoryService categoryService;

    @Test
    public void CategoryServiceAutowired() {
        assertNotNull(categoryService);
    }

    @Test
    public void findAll() {
        assertNotNull(categoryService.findAll());
    }

    @Test
    public void existsByNameIgnoreCase_WhenNameExists() {
        // arrange ...

        // act ...
        Boolean existsByName = categoryService.existsByNameIgnoreCase(EXISTING_CATEGORY_NAME);

        // assert ...
        assertNotNull(existsByName);
        assertTrue(existsByName);
    }

    @Test
    public void existsByNameIgnoreCase_WhenNameDoesNotExist() {
        // arrange ...

        // act ...
        Boolean existsByName = categoryService.existsByNameIgnoreCase(NON_EXISTING_CATEGORY_NAME);

        // assert ...
        assertNotNull(existsByName);
        assertFalse(existsByName);
    }

}