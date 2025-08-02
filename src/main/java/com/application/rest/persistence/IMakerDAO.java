package com.application.rest.persistence;

import com.application.rest.entities.Maker;

import java.util.List;
import java.util.Optional;

//uso el patron de diseño dao data acces object para manejar la persistencian bd
public interface IMakerDAO {

    List<Maker> listar();

    Optional<Maker> buscar_por_id(Long id);

    void guardar(Maker maker);

    void borrar(Long id);


}
