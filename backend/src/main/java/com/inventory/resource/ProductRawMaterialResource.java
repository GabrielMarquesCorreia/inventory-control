package com.inventory.resource;

import com.inventory.dto.ProductRawMaterialDTO;
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

import java.util.ArrayList;
import java.util.List;

@Path("/product-materials")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductRawMaterialResource {

    @Inject
    ProductRepository productRepository;

    @Inject
    RawMaterialRepository rawMaterialRepository;

    // ================= GET =================
    @GET
    public Response getMaterials(@QueryParam("productId") Long productId) {

        if (productId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("productId is required")
                           .build();
        }

        Product product = productRepository.findById(productId);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Product not found")
                           .build();
        }

        List<ProductRawMaterialDTO> dtoList = new ArrayList<>();

        if (product.getMaterials() != null) {
            for (ProductRawMaterial prm : product.getMaterials()) {
                ProductRawMaterialDTO dto = new ProductRawMaterialDTO();
                dto.id = prm.id;
                dto.quantity = prm.getQuantity();

                ProductRawMaterialDTO.RawMaterialDTO rmDTO = new ProductRawMaterialDTO.RawMaterialDTO();
                rmDTO.id = prm.getRawMaterial().getId();
                rmDTO.name = prm.getRawMaterial().getName();
                rmDTO.stock = prm.getRawMaterial().getStock();

                dto.rawMaterial = rmDTO;
                dtoList.add(dto);
            }
        }

        return Response.ok(dtoList).build();
    }

    // ================= POST =================
    @POST
    @Transactional
    public Response addMaterial(
            @QueryParam("productId") Long productId,
            @QueryParam("materialId") Long materialId,
            @QueryParam("quantity") int quantity
    ) {

        if (quantity <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Quantity must be greater than 0")
                           .build();
        }

        Product product = productRepository.findById(productId);
        RawMaterial material = rawMaterialRepository.findById(materialId);

        if (product == null || material == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Product or Material not found")
                           .build();
        }

        if (product.getMaterials() == null) {
            product.setMaterials(new ArrayList<>());
        }

        boolean exists = product.getMaterials()
                .stream()
                .anyMatch(m -> m.getRawMaterial().getId().equals(materialId));

        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                           .entity("Material already added to product")
                           .build();
        }

        ProductRawMaterial prm = new ProductRawMaterial();
        prm.setProduct(product);
        prm.setRawMaterial(material);
        prm.setQuantity(quantity);

        prm.persist();
        product.getMaterials().add(prm);

        return Response.ok(prm.id).build(); // Retorna apenas o ID da associação
    }

    // ================= PUT =================
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateMaterial(@PathParam("id") Long id, @QueryParam("quantity") int quantity) {

        if (quantity <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Quantity must be greater than 0")
                           .build();
        }

        ProductRawMaterial prm = ProductRawMaterial.findById(id);

        if (prm == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Association not found")
                           .build();
        }

        prm.setQuantity(quantity);

        return Response.ok(prm.id).build();
    }

    // ================= DELETE =================
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteMaterial(@PathParam("id") Long id) {

        ProductRawMaterial prm = ProductRawMaterial.findById(id);

        if (prm == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Association not found")
                           .build();
        }

        prm.delete();

        return Response.noContent().build();
    }
}
