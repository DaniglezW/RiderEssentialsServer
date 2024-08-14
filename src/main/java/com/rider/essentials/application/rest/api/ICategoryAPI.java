package com.rider.essentials.application.rest.api;

import com.rider.essentials.domain.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.rider.essentials.application.constants.Constants.API_BASE;

@RequestMapping(API_BASE)
public interface ICategoryAPI {

    @GetMapping("/categories")
    List<Category> getAllCategories();

}
