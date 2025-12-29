package com.platzi_market.domain.repository;

import com.platzi_market.domain.DTO.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {
    List<Purchase> getAll();
    Optional<List<Purchase>> getByClient(String clienteId);
    Purchase save(Purchase purchase);
}
