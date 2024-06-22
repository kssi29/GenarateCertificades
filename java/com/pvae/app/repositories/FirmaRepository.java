package com.pvae.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pvae.app.models.FirmaModel;
@Repository
public interface FirmaRepository extends CrudRepository<FirmaModel, Long>{
}
