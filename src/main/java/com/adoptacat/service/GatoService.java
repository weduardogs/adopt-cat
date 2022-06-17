package com.adoptacat.service;

import com.adoptacat.model.Gato;

import java.util.List;

public interface GatoService {

	List<Gato> getAllGatos();

	Gato findOne(long id);

	Gato saveGato(Gato gato);

	void deleteGato(Gato gato);

	Gato fetchByIdWithPostulaciones(Long id);

	List<Gato> findGatosByPeso(double peso);

	List<Gato> findGatoByEstatusVisible(boolean visible);

}
