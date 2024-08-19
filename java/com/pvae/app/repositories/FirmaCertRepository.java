package com.pvae.app.repositories;

import com.pvae.app.models.AutoridadModel;
import com.pvae.app.models.FirmaCertModel;
import com.pvae.app.models.ParticipanteModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirmaCertRepository extends CrudRepository<FirmaCertModel, Long> {



    @Query("SELECT firma.autoridad FROM FirmaCertModel firma WHERE firma.certificado.evento.idevento= :eventoId ")
    List<AutoridadModel> findAutoridadesByEventoId(@Param("eventoId") Long eventoId);
}
