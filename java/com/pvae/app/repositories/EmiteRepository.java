package com.pvae.app.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.pvae.app.models.EmiteModel;


@Repository
public interface EmiteRepository extends CrudRepository<EmiteModel, Long>{
      

      
} 
