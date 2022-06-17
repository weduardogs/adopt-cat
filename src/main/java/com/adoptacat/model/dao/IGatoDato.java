package com.adoptacat.model.dao;

import com.adoptacat.model.Gato;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IGatoDato extends PagingAndSortingRepository<Gato, Long> {

    @Query("select g from Gato g left join fetch g.postulaciones where g.id=?1")
    public Gato fetchByIdWithPostulaciones(Long id);
}
