package com.msrr.inventory_service.service;

import com.msrr.inventory_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
