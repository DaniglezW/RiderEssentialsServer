package com.rider.essentials.application.rest;

import com.rider.essentials.application.rest.api.IProductAPI;
import com.rider.essentials.domain.model.Product;
import com.rider.essentials.domain.services.interfaces.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class ProductController implements IProductAPI {

    private final IProductService iProductService;

    @Autowired
    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @Override
    public Page<Product> getAllProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            return iProductService.getAllProducts(pageable);
        } catch (Exception e) {
            log.error("Error in ProductController.getAllProducts controller");
            throw e;
        }
    }

}
