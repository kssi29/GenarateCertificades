package com.pvae.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pvae.app.models.EstadoModel;

@Repository
public interface EstadoRepository extends CrudRepository<EstadoModel, Long>{
      
}
