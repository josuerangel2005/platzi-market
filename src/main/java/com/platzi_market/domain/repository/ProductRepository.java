package com.platzi_market.domain.repository;

import com.platzi_market.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAll();

    Optional<Product> getProduct(int productId);

    Optional<List<Product>> getByCategory(int categoryId);

    Optional<List<Product>> getScarseProducts(int quantity);

    Product save(Product product);

    void delete(int productId);

}
