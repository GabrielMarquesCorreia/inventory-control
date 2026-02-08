package com.inventory.resource;

import java.util.stream.Collectors;

import com.inventory.dto.ProductDTO;
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

    // ✅ FIND BY ID (using DTO)
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {

        Product product = productService.findById(id);

        // Map to DTO
        ProductDTO dto = new ProductDTO();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.value = product.getValue();

        if (product.getMaterials() != null) {
            dto.materials = product.getMaterials().stream().map(m -> {
                ProductDTO.ProductRawMaterialDTO matDto = new ProductDTO.ProductRawMaterialDTO();
                matDto.id = m.getRawMaterial().getId();
                matDto.name = m.getRawMaterial().getName();
                matDto.quantity = m.getQuantity();
                return matDto;
            }).toList();
        }

        return Response.ok(dto).build();
    }

    // ✅ FIND ALL (using DTOs)
    @GET
    public Response findAll() {

        var products = productService.findAll();

        var dtoList = products.stream().map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.id = product.getId();
            dto.name = product.getName();
            dto.value = product.getValue();

            if (product.getMaterials() != null) {
                dto.materials = product.getMaterials().stream().map(m -> {
                    ProductDTO.ProductRawMaterialDTO matDto = new ProductDTO.ProductRawMaterialDTO();
                    matDto.id = m.getRawMaterial().getId();
                    matDto.name = m.getRawMaterial().getName();
                    matDto.quantity = m.getQuantity();
                    return matDto;
                }).toList();
            }

            return dto;
        }).toList();

        return Response.ok(dtoList).build();
    }

    // ✅ UPDATE
    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Product product) {

        Product updated = productService.update(id, product);

        // Map to DTO
        ProductDTO dto = new ProductDTO();
        dto.id = updated.getId();
        dto.name = updated.getName();
        dto.value = updated.getValue();

        if (updated.getMaterials() != null) {
            dto.materials = updated.getMaterials().stream().map(m -> {
                ProductDTO.ProductRawMaterialDTO matDto = new ProductDTO.ProductRawMaterialDTO();
                matDto.id = m.getRawMaterial().getId();
                matDto.name = m.getRawMaterial().getName();
                matDto.quantity = m.getQuantity();
                return matDto;
            }).toList();
        }

        return Response.ok(dto).build();
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

   // ✅ AVAILABLE PRODUCTS FOR PRODUCTION
    @GET
    @Path("/available-production")
    public Response availableProduction() {

        return Response
                .ok(productService.getAvailableProducts())
                .build();
    }

}
