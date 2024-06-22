package com.pvae.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pvae.app.models.PersonaModel;

@Repository
public interface PersonaRepository extends CrudRepository<PersonaModel, Long>{
      
}
