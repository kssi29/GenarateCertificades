package com.pvae.app.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pvae.app.models.CertificadoModel;
import com.pvae.app.models.EventoModel;
import com.pvae.app.models.ParticipanteModel;

@Repository
public interface CertificadoRepository extends CrudRepository<CertificadoModel, Long>{

       @Query("SELECT cert.participante FROM CertificadoModel cert WHERE cert.evento.idevento = :eventoId")
       List<ParticipanteModel> findParticipantesByEventoId(@Param("eventoId") Long eventoId);

       
       CertificadoModel findByEventoAndParticipante(EventoModel evento, ParticipanteModel participante);
      

      
} 