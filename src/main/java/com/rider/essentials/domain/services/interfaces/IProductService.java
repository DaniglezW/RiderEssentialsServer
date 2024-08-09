package com.rider.essentials.domain.services.interfaces;

import com.rider.essentials.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    Page<Product> getAllProducts(Pageable pageable);

}
