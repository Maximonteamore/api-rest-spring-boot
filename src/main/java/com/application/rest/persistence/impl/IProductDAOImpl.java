package com.application.rest.persistence.impl;

import com.application.rest.entities.Maker;
import com.application.rest.entities.Product;
import com.application.rest.persistence.IProductDAO;
import com.application.rest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class IProductDAOImpl implements IProductDAO {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Product> listar() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Optional<Product> buscar_por_id(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> listar_rango_precios(BigDecimal minPrecio, BigDecimal maxPrecio) {
        return productRepository.buscar_rango_precios(minPrecio,maxPrecio);
    }

    @Override
    public void guardar(Product product) {
        productRepository.save(product);
    }

    @Override
    public void borrar(Long id) {
        productRepository.deleteById(id);
    }
}
