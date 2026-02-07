package com.inventory.resource;

import com.inventory.entity.Product;
import com.inventory.entity.ProductRawMaterial;
import com.inventory.entity.RawMaterial;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.RawMaterialRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/product-materials")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductRawMaterialResource {

    @Inject
    ProductRepository productRepository;

    @Inject
    RawMaterialRepository rawMaterialRepository;

    @POST
    @Transactional
    public Response addMaterial(
            @QueryParam("productId") Long productId,
            @QueryParam("materialId") Long materialId,
            @QueryParam("quantity") int quantity
    ) {

        Product product = productRepository.findById(productId);
        RawMaterial material = rawMaterialRepository.findById(materialId);

        if (product == null || material == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ProductRawMaterial prm = new ProductRawMaterial();

        prm.setProduct(product);
        prm.setRawMaterial(material);
        prm.setQuantity(quantity);

        prm.persist();

        return Response.ok(prm).build();
    }
}
