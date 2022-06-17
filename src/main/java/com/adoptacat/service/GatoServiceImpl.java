package com.adoptacat.service;

import com.adoptacat.model.Gato;
import com.adoptacat.model.dao.IGatoDato;
import com.adoptacat.repository.GatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class GatoServiceImpl implements GatoService {

    @Autowired
    private GatoRepository repository;

    @Autowired
    private IGatoDato gatoDato;

    @Override
    public List<Gato> getAllGatos() {
        return repository.findAll();
    }

    @Override
    public Gato findOne(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Gato saveGato(Gato gato) {
        return repository.save(gato);
    }

    @Override
    public void deleteGato(Gato gato) {
        repository.delete(gato);
    }

    @Override
    public Gato fetchByIdWithPostulaciones(Long id) {
    	return gatoDato.fetchByIdWithPostulaciones(id);
    }

    @Override
    public List<Gato> findGatosByPeso(double peso) {
        return repository.findGatosByPeso(peso);
    }

    @Override
    public List<Gato> findGatoByEstatusVisible(boolean visible) {
        return repository.findGatoByEstatusVisible(visible);
    }

}
