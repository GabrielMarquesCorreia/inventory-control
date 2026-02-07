package com.inventory.repository;

import com.inventory.entity.RawMaterial;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RawMaterialRepository implements PanacheRepository<RawMaterial> {
}
