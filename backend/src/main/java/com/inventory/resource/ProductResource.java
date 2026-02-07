package com.inventory.resource;

import com.inventory.dto.ProductionCapacityDTO;
import com.inventory.entity.Product;
import com.inventory.service.ProductService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    // ✅ CREATE PRODUCT
    @POST
    @Transactional
    public Response create(Product product) {

        Product created = productService.create(product);

        return Response
                .status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    // ✅ ADD MATERIAL
    @POST
    @Path("/{productId}/materials")
    @Transactional
    public Response addMaterial(
            @PathParam("productId") Long productId,
            AddMaterialDTO dto) {

        productService.addMaterial(
                productId,
                dto.materialId,
                dto.quantity
        );

        return Response.status(Response.Status.CREATED).build();
    }

    public static class AddMaterialDTO {
        public Long materialId;
        public int quantity;
    }

    // ✅ PRODUCTION PLAN
    @GET
    @Path("/production-plan")
    public Response productionPlan() {

        return Response.ok(
                productService.calculateProductionPlan()
        ).build();
    }

    // ✅ FIND BY ID
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {

        Product product = productService.findById(id);

        return Response.ok(product).build();
    }

    @GET
    public Response findAll() {

        return Response.ok(
                productService.findAll()
        ).build();
    }

    // ✅ UPDATE
    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Product product) {

        Product updated = productService.update(id, product);

        return Response.ok(updated).build();
    }

    // ✅ DELETE
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {

        productService.delete(id);

        return Response.noContent().build();
    }

    // ✅ PRODUCTION CAPACITY
    @GET
    @Path("/{id}/production-capacity")
    public Response productionCapacity(@PathParam("id") Long productId) {

        ProductionCapacityDTO result =
                productService.calculateProductionCapacity(productId);

        return Response.ok(result).build();
    }
}
