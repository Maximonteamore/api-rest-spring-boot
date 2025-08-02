package com.application.rest.service.impl;

import com.application.rest.entities.Maker;
import com.application.rest.persistence.IMakerDAO;
import com.application.rest.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MakerServiceImpl implements IMakerService {
    @Autowired
    private IMakerDAO iMakerDAO;

    @Override
    public List<Maker> listar() {
        return iMakerDAO.listar();
    }

    @Override
    public Optional<Maker> buscar_por_id(Long id) {
        return iMakerDAO.buscar_por_id(id);
    }

    @Override
    public void guardar(Maker maker) {
        iMakerDAO.guardar(maker);
    }

    @Override
    public void borrar(Long id) {
        iMakerDAO.borrar(id);
    }
}
