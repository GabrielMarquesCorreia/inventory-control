package com.inventory.repository;

import com.inventory.entity.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import com.inventory.repository.ProductRepository;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    @Inject
    ProductRepository productRepository;

}
