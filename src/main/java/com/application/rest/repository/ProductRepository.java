package com.application.rest.repository;

import com.application.rest.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {

    //creo consulta con @query el ?1 hace referencia al primer atributo "BigDecimal minPrecio".
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> buscar_rango_precios(BigDecimal minPrecio,BigDecimal maxPrecio);

}
