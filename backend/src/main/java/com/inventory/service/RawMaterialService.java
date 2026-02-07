package com.inventory.service;

import com.inventory.entity.RawMaterial;
import com.inventory.repository.RawMaterialRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class RawMaterialService {

    @Inject
    RawMaterialRepository repository;

    // ✅ CREATE
    @Transactional
    public RawMaterial create(RawMaterial rawMaterial) {
        repository.persist(rawMaterial);
        return rawMaterial;
    }

    // ✅ GET ALL
    public List<RawMaterial> findAll() {
        return repository.listAll();
    }

    // ✅ GET BY ID
    public RawMaterial findById(Long id) {

        return repository
                .findByIdOptional(id)
                .orElseThrow(() ->
                    new NotFoundException("Material not found: " + id)
                );
    }


    // ✅ UPDATE
    @Transactional
    public RawMaterial update(Long id, RawMaterial data) {
        RawMaterial entity = findById(id);

        entity.setName(data.getName());
        entity.setStock(data.getStock());

        return entity;
    }

    // ✅ DELETE
    @Transactional
    public void delete(Long id) {
        RawMaterial entity = findById(id);
        repository.delete(entity);
    }
}
