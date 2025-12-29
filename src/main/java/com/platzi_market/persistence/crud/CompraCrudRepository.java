package com.platzi_market.persistence.crud;

import com.platzi_market.persistence.entity.Compra;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompraCrudRepository extends JpaRepository<Compra, Integer> {
    Optional<List<Compra>> findByIdCliente(String idCliente);
}
