package com.platzi_market.domain.service;

import com.platzi_market.domain.Product;
import com.platzi_market.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return this.productRepository.getAll();
    }

    @Transactional(readOnly = true)
    public Optional<Product> getProduct(int productId) {
        return this.productRepository.getProduct(productId);
    }

    @Transactional(readOnly = true)
    public Optional<List<Product>> getByCategory(int categoryId) {
        return this.productRepository.getByCategory(categoryId);
    }

    @Transactional
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Transactional
    public boolean delete(int productId) {
        return this.getProduct(productId).map(product -> {
            this.productRepository.delete(productId);
            return true;
        }).orElse(false);
    }
}
