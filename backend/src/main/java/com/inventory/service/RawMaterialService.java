package com.inventory.service;

import com.inventory.entity.RawMaterial;
import com.inventory.repository.RawMaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RawMaterialService {

    @Inject
    RawMaterialRepository repository;

    @Transactional
    public RawMaterial create(RawMaterial rawMaterial) {
        repository.persist(rawMaterial);
        return rawMaterial;
    }
}
