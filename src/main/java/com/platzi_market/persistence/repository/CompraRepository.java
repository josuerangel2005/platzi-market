package com.platzi_market.persistence.repository;

import com.platzi_market.domain.DTO.Purchase;
import com.platzi_market.domain.repository.PurchaseRepository;
import com.platzi_market.persistence.crud.CompraCrudRepository;
import com.platzi_market.persistence.entity.Compra;
import com.platzi_market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {
    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public List<Purchase> getAll() {
        return this.purchaseMapper.toPurchases(this.compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clienteId) {
        return this.compraCrudRepository.findByIdCliente(clienteId).map(compras -> this.purchaseMapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = this.purchaseMapper.toCompra(purchase);
        compra.getComprasProductos().forEach(producto -> producto.setCompra(compra));
        return this.purchaseMapper.toPurchase(this.compraCrudRepository.save(compra));
    }
}
