package com.inventory.resource;

import com.inventory.entity.RawMaterial;
import com.inventory.service.RawMaterialService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/raw-materials")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RawMaterialResource {

    @Inject
    RawMaterialService service;

    @POST
    public Response create(RawMaterial rawMaterial) {
        return Response.status(Response.Status.CREATED)
                .entity(service.create(rawMaterial))
                .build();
    }
}
