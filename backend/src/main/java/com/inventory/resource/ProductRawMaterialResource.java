package com.inventory.resource;

import com.inventory.dto.ProductRawMaterialDTO;
import com.inventory.entity.Product;
import com.inventory.entity.RawMaterial;
import com.inventory.entity.ProductRawMaterial;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.RawMaterialRepository;
import com.inventory.repository.ProductRawMaterialRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.GET;
import java.util.List;


@Path("/product-materials")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductRawMaterialResource {

    @Inject
    ProductRepository productRepository;

    @Inject
    RawMaterialRepository rawMaterialRepository;

    @Inject
    ProductRawMaterialRepository repository;

    @POST
    @Transactional
    public ProductRawMaterial create(ProductRawMaterialDTO dto) {

        Product product = productRepository.findById(dto.productId);
        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId);

        ProductRawMaterial existing =
            repository.findByProductAndRawMaterial(product, rawMaterial);

        if (existing != null) {
            existing.quantity += dto.quantity;
            return existing;
        }

        ProductRawMaterial prm = new ProductRawMaterial();
        prm.product = product;
        prm.rawMaterial = rawMaterial;
        prm.quantity = dto.quantity;

        repository.persist(prm);
        return prm;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {

        ProductRawMaterial prm = repository.findById(id);

        if (prm == null) {
            throw new NotFoundException("Relacionamento não encontrado");
        }

        repository.delete(prm);

        return Response.noContent().build();
    }

    @GET
    public List<ProductRawMaterial> list() {
        return repository.listAll();
    }

}

