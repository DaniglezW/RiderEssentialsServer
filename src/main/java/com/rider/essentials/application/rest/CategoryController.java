package com.rider.essentials.application.rest;

import com.rider.essentials.application.rest.api.ICategoryAPI;
import com.rider.essentials.domain.model.Category;
import com.rider.essentials.domain.services.interfaces.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CategoryController implements ICategoryAPI {

    private final ICategoryService iCategoryService;

    @Autowired
    public CategoryController(ICategoryService iCategoryService) {
        this.iCategoryService = iCategoryService;
    }


    @Override
    public List<Category> getAllCategories() {
        try {
            return iCategoryService.getAllCategory();
        } catch (Exception e) {
            log.error("Error in ProductController.getAllProducts controller");
            throw e;
        }
    }

}
