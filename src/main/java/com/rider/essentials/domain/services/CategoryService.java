package com.rider.essentials.domain.services;

import com.rider.essentials.domain.model.Category;
import com.rider.essentials.domain.services.interfaces.ICategoryService;
import com.rider.essentials.repository.ICategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository iCategoryRepository;

    @Autowired
    public CategoryService(ICategoryRepository iCategoryRepository) {
        this.iCategoryRepository = iCategoryRepository;
    }

    @Override
    public List<Category> getAllCategory() {
        return iCategoryRepository.findAll();
    }
}
