package com.pvae.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pvae.app.models.AutoridadModel;
@Repository
public interface AutoridadRepository extends CrudRepository<AutoridadModel, Long>{
    AutoridadModel findByCi(int ci);

      
} 
