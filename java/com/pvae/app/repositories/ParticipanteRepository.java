package com.pvae.app.repositories;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.pvae.app.models.ParticipanteModel;

@Repository
public interface ParticipanteRepository extends CrudRepository<ParticipanteModel, Long>{
      ParticipanteModel findByCi(int ci);



      
}
