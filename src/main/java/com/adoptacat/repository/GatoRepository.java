package com.adoptacat.repository;

import com.adoptacat.model.Gato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GatoRepository extends JpaRepository<Gato, Long> {

    @Transactional(readOnly=true)
    List<Gato> findGatosByPeso(double peso);

    @Transactional(readOnly=true)
    List<Gato> findGatoByEstatusVisible(boolean visible);

}
