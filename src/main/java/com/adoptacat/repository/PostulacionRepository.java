package com.adoptacat.repository;

import com.adoptacat.model.Gato;
import com.adoptacat.model.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

	@Transactional(readOnly=true)
	List<Postulacion> getPostulacionesByGato(Gato gato);

	@Transactional(readOnly=true)
	List<Postulacion> findPostulacionesByGatoAndTuvoGato(Gato gato, String term);

	@Transactional(readOnly=true)
	List<Postulacion> findPostulacionesByGatoAndTieneEspacio(Gato gato, String term);

}
