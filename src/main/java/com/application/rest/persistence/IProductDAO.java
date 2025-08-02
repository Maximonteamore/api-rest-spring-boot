package com.application.rest.persistence;

import com.application.rest.entities.Maker;
import com.application.rest.entities.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


//uso el patron de diseño dao data acces object para manejar la persistencian bd
public interface IProductDAO {

    List<Product> listar();

    Optional<Product> buscar_por_id(Long id);

    List<Product> listar_rango_precios(BigDecimal minPrecio,BigDecimal maxPrecio);

    void guardar(Product product);

    void borrar(Long id);
}
