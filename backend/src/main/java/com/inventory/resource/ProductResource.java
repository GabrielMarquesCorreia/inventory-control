package com.inventory.resource;

import com.inventory.entity.Product;
import com.inventory.service.ProductService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @POST
    public Response create(Product product) {
        Product saved = productService.create(product);
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }
}
