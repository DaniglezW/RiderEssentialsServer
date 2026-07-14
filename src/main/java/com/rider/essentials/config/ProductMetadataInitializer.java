package com.rider.essentials.config;

import com.rider.essentials.domain.model.Product;
import com.rider.essentials.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.rider.essentials.application.constants.Constants.DEFAULT_IMAGE_PRODUCT_ID;

@Slf4j
@Component
@Order(2)
public class ProductMetadataInitializer implements ApplicationRunner {

    private static final String[] BRANDS = {
            "Akrapovič", "Alpinestars", "Arrow", "Brembo", "Castrol", "Dainese",
            "DID", "Galfer", "K&N", "Michelin", "Motul", "Öhlins", "Pirelli",
            "Rizoma", "Shoei", "Termignoni", "Yuasa", "Puig", "SW-Motech", "Givi"
    };

    private static final String[] MAKES = {
            "Honda", "Yamaha", "Kawasaki", "Suzuki", "BMW", "Ducati", "KTM", "Triumph"
    };

    private final IProductRepository productRepository;

    public ProductMetadataInitializer(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<Product> products = productRepository.findAll();
        int updated = 0;
        for (Product product : products) {
            if (product.getProductId() == DEFAULT_IMAGE_PRODUCT_ID) {
                continue;
            }
            boolean changed = false;
            if (product.getBrand() == null || product.getBrand().isBlank()) {
                product.setBrand(BRANDS[(int) (product.getProductId() % BRANDS.length)]);
                changed = true;
            }
            if (product.getStockQuantity() == null) {
                product.setStockQuantity(product.getProductId() % 5 == 0 ? 0 : 3 + (int) (product.getProductId() % 47));
                changed = true;
            }
            if (product.getRating() == null) {
                double rating = 3.5 + (product.getProductId() % 15) / 10.0;
                product.setRating(Math.min(5.0, Math.round(rating * 10) / 10.0));
                changed = true;
            }
            if (product.getReviewCount() == null) {
                product.setReviewCount(12 + (int) ((product.getProductId() * 7) % 340));
                changed = true;
            }
            if (product.getSku() == null || product.getSku().isBlank()) {
                product.setSku(String.format("RE-%04d", product.getProductId()));
                changed = true;
            }
            if (product.getCompatibleMakes() == null || product.getCompatibleMakes().isBlank()) {
                int count = 2 + (int) (product.getProductId() % 4);
                StringBuilder makes = new StringBuilder();
                for (int i = 0; i < count; i++) {
                    if (i > 0) makes.append(",");
                    makes.append(MAKES[(int) ((product.getProductId() + i) % MAKES.length)]);
                }
                product.setCompatibleMakes(makes.toString());
                changed = true;
            }
            if (changed) {
                productRepository.save(product);
                updated++;
            }
        }
        if (updated > 0) {
            log.info("Initialized metadata for {} products", updated);
        }
    }
}
