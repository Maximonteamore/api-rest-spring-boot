package com.application.rest.service.impl;

import com.application.rest.entities.Product;
import com.application.rest.persistence.IProductDAO;
import com.application.rest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDAO iProductDAO;

    @Override
    public List<Product> listar() {
        return iProductDAO.listar();
    }

    @Override
    public Optional<Product> buscar_por_id(Long id) {
        return iProductDAO.buscar_por_id(id);
    }

    @Override
    public List<Product> listar_rango_precios(BigDecimal minPrecio, BigDecimal maxPrecio) {
        return iProductDAO.listar_rango_precios(minPrecio,maxPrecio);
    }

    @Override
    public void guardar(Product product) {
        iProductDAO.guardar(product);
    }

    @Override
    public void borrar(Long id) {
        iProductDAO.borrar(id);
    }
}
