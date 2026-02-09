package com.inventory.resource;

import com.inventory.dto.ProductionPlanDTO;
import com.inventory.service.ProductService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/production-plan")
@Produces(MediaType.APPLICATION_JSON)
public class ProductionPlanResource {

    @Inject
    ProductService productService;

    @GET
    public ProductionPlanDTO getProductionPlan() {
        return productService.calculateProductionPlan();
    }
}
