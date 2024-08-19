package com.pvae.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pvae.app.models.EventoModel;

import java.util.List;

@Repository
public interface EventoRepository extends CrudRepository<EventoModel, Long>{
    List<EventoModel> findAllByOrderByIdeventoAsc();




}