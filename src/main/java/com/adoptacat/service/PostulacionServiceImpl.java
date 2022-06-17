package com.adoptacat.service;


import com.adoptacat.model.Gato;
import com.adoptacat.model.Postulacion;
import com.adoptacat.repository.PostulacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostulacionServiceImpl implements PostulacionService {

	@Autowired
	private PostulacionRepository postulacionRepository;
	
	@Override
	public Postulacion savePostulacion(Postulacion postulacion) {
		return postulacionRepository.save(postulacion);
	}

	@Override
	public List<Postulacion> getPostulacionesByGato(Gato gato) {
		return postulacionRepository.getPostulacionesByGato(gato);
	}

	@Override
	public List<Postulacion> findPostulacionesByGatoAndTuvoGato(Gato gato, String term) {
		return postulacionRepository.findPostulacionesByGatoAndTuvoGato(gato, term);
	}

	@Override
	public List<Postulacion> findPostulacionesByGatoAndTieneEspacio(Gato gato, String term) {
		return postulacionRepository.findPostulacionesByGatoAndTieneEspacio(gato, term);
	}

	@Override
	public Postulacion findOne(long id) {
		return postulacionRepository.findById(id).orElse(null);
	}

}
