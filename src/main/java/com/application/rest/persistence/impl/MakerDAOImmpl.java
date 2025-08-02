package com.application.rest.persistence.impl;

import com.application.rest.entities.Maker;
import com.application.rest.persistence.IMakerDAO;
import com.application.rest.repository.MakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

//implemento interface IMakerDAO
@Component
    public class MakerDAOImmpl implements IMakerDAO {

    @Autowired
    private MakerRepository makerRepository;

    @Override
    public List<Maker> listar() {
        return (List<Maker>) makerRepository.findAll();
    }

    @Override
    public Optional<Maker> buscar_por_id(Long id) {
        return makerRepository.findById(id);
    }

    @Override
    public void guardar(Maker maker) {
        makerRepository.save(maker);
    }

    @Override
    public void borrar(Long id) {
        makerRepository.deleteById(id);
    }
}
