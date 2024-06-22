package com.pvae.app.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pvae.app.models.CertificadoModel;

@Repository
public interface CertificadoRepository extends CrudRepository<CertificadoModel, Long>{
      

      
} 