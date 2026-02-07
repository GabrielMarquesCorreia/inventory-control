package com.inventory.service;

import com.inventory.entity.Product;
import com.inventory.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Transactional
    public Product create(Product product) {
        productRepository.persist(product);
        return product;
    }
}
