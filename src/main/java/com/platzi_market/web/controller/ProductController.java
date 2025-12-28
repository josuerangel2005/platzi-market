package com.platzi_market.web.controller;

import com.platzi_market.domain.Product;
import com.platzi_market.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public List<Product> getAll() {
        return this.productService.getAll();
    }

    @GetMapping("/product/{id}")
    public Optional<Product> getProduct(@PathVariable("id") int productId) {
        return this.productService.getProduct(productId);
    }

    @GetMapping("/category/{id}")
    public Optional<List<Product>> getByCategory(@PathVariable("id") int categoryId) {
        return this.productService.getByCategory(categoryId);
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return this.productService.save(product);
    }

    @DeleteMapping("/product/del/{id}")
    public boolean delete(@PathVariable int productId) {
        return this.productService.delete(productId);
    }

}
