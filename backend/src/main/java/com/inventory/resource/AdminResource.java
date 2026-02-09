package com.inventory.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import com.inventory.repository.ProductRawMaterialRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.RawMaterialRepository;

import jakarta.inject.Inject;

@Path("/admin")
public class AdminResource {

    @Inject
    ProductRepository productRepository;

    @Inject
    RawMaterialRepository rawMaterialRepository;

    @Inject
    ProductRawMaterialRepository productRawMaterialRepository;

    @DELETE
    @Path("/clear-all")
    @Transactional
    public Response clearAllData() {

        long associationsDeleted = productRawMaterialRepository.deleteAll();

        long productsDeleted = productRepository.deleteAll();

        long rawMaterialsDeleted = rawMaterialRepository.deleteAll();

        String message = String.format(
            "Data cleared successfully! Deleted %d associations, %d products, %d raw materials.",
            associationsDeleted, productsDeleted, rawMaterialsDeleted
        );

        return Response.ok(message).build();
    }
}
