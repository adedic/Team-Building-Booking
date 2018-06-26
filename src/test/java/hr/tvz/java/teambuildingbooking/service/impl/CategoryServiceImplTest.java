package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {


    @Autowired
    CategoryService categoryService;

    @Test
    public void CategoryServiceAutowired() {
        assertNotNull(categoryService);
    };

    @Test
    public void findAll() {
        assertNotNull(categoryService.findAll());
    };

    @Test
    public void existsByNameIgnoreCase() {
        String name = "avantura";
        assertFalse(categoryService.existsByNameIgnoreCase(name));
    };

}