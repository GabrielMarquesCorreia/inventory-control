package com.inventory.controller;

import com.inventory.dto.ProductResponseDTO;
import com.inventory.entity.Product;
import com.inventory.repository.ProductRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/products")
public class ProductController {

    @Inject
    ProductRepository repository;

    @GET
    @Path("/{id}")
    public ProductResponseDTO getById(@PathParam("id") Long id){

        Product product = repository.findById(id);

        return new ProductResponseDTO(product);
    }
}
