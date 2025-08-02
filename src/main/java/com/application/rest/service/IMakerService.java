package com.application.rest.service;

import com.application.rest.entities.Maker;

import java.util.List;
import java.util.Optional;

public interface IMakerService {
    List<Maker> listar();

    Optional<Maker> buscar_por_id(Long id);

    void guardar(Maker maker);

    void borrar(Long id);

}
