package com.inventory.resource;

import com.inventory.entity.RawMaterial;
import com.inventory.service.RawMaterialService;

import jakarta.inject.Inject;
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

    @POST
    public Response create(@Valid RawMaterial rawMaterial) {
        return Response
                .status(Response.Status.CREATED)
                .entity(service.create(rawMaterial))
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
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
