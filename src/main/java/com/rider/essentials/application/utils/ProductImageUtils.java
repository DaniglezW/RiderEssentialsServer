package com.rider.essentials.application.utils;

import com.rider.essentials.domain.model.Product;

import java.util.Base64;

public final class ProductImageUtils {

    private ProductImageUtils() {
    }

    public static String toBase64DataUrl(Product product) {
        if (product == null || product.getImage() == null || product.getImage().length == 0) {
            return null;
        }
        return Base64.getEncoder().encodeToString(product.getImage());
    }
}
