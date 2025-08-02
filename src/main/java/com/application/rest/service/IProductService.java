package com.application.rest.service;

import com.application.rest.entities.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> listar();

    Optional<Product> buscar_por_id(Long id);

    List<Product> listar_rango_precios(BigDecimal minPrecio, BigDecimal maxPrecio);

    void guardar(Product product);

    void borrar(Long id);
}
