package com.adoptacat.service;

import com.adoptacat.model.Gato;
import com.adoptacat.model.Postulacion;
import java.util.List;


public interface PostulacionService  {

	Postulacion savePostulacion(Postulacion postulacion);

	List<Postulacion> getPostulacionesByGato(Gato gato);

	List<Postulacion> findPostulacionesByGatoAndTuvoGato(Gato gato, String term);

	List<Postulacion> findPostulacionesByGatoAndTieneEspacio(Gato gato, String term);

	Postulacion findOne(long id);

}
