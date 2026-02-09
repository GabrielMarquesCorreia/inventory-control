package com.inventory.resource;

import com.inventory.entity.ProductRawMaterial;
import com.inventory.entity.RawMaterial;
import com.inventory.repository.RawMaterialRepository;
import com.inventory.service.RawMaterialService;
import com.inventory.dto.RawMaterialCreateDTO;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/raw-materials")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RawMaterialResource {

    @Inject
    RawMaterialService service;

    @Inject
    RawMaterialRepository rawMaterialRepository;

    @POST
    public Response create(@Valid RawMaterialCreateDTO dto) {

        RawMaterial material = new RawMaterial();
        material.setName(dto.name);
        material.setStock(dto.stockQuantity);

        return Response
                .status(Response.Status.CREATED)
                .entity(service.create(material))
                .build();
    }

    // GET ALL
    @GET
    public List<RawMaterial> findAll() {
        return service.findAll();
    }

    // GET BY ID
    @GET
    @Path("/{id}")
    public RawMaterial findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    // UPDATE
    @PUT
    @Path("/{id}")
    public RawMaterial update(
            @PathParam("id") Long id,
            @Valid RawMaterial rawMaterial
    ) {
        return service.update(id, rawMaterial);
    }

    // DELETE
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        RawMaterial material = rawMaterialRepository.findById(id);
        if (material == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<ProductRawMaterial> related = ProductRawMaterial.list("rawMaterial", material);
        related.forEach(ProductRawMaterial::delete);

        rawMaterialRepository.delete(material);
        return Response.noContent().build();
    }
}
