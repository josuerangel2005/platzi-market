package com.platzi_market.domain.service;

import com.platzi_market.domain.DTO.Purchase;
import com.platzi_market.persistence.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    @Autowired
    private CompraRepository compraRepository;

    @Transactional(readOnly = true)
    public List<Purchase> getAll() {
        return this.compraRepository.getAll();
    }

    @Transactional(readOnly = true)
    public Optional<List<Purchase>> getByClient(String clienteId) {
        return this.compraRepository.getByClient(clienteId);
    }

    @Transactional
    public Purchase save(Purchase purchase) {
        return this.compraRepository.save(purchase);
    }


}
