package com.platzi_market.persistence.repository;

import com.platzi_market.domain.Product;
import com.platzi_market.domain.repository.ProductRepository;
import com.platzi_market.persistence.crud.ProductoCrudRepository;
import com.platzi_market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {
    @Autowired // debo poner esto, ya que sin ello, al tener solo 'ProductoCrudRepository productoCrudRepository', no los tengo instanciados por lo que su valor es null, al ponerlo le decimos a spring que el se encargue de crear estas instancias, pero DEBE SER UN COMPONENTE DE SPRING el que inyectamos.
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getAll() {
        return this.productMapper.toProducts(this.productoCrudRepository.findAll());
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        return Optional.of(this.productMapper.toProducts(this.productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId)));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        return this.productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true).map(productos -> this.productMapper.toProducts(productos));
    }

    @Override
    public Optional<Product> getProduct(int idPorducto) {
        return this.productoCrudRepository.findById(idPorducto).map(producto -> this.productMapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        return this.productMapper.toProduct(this.productoCrudRepository.save(this.productMapper.toProducto(product)));
    }

    @Override
    public void delete(int productId) {
        this.productoCrudRepository.deleteById(productId);
    }
}
