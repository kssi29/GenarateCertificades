package com.pvae.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pvae.app.models.UnidadModel;

@Repository
public interface UnidadRepository extends CrudRepository<UnidadModel, Long>{
      
}
