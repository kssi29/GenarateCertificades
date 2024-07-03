package com.pvae.app.servicies;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvae.app.models.ParticipanteModel;
import com.pvae.app.repositories.ParticipanteRepository;

import jakarta.transaction.Transactional;
@Service
public class ParticipanteService {
      @Autowired
      private ParticipanteRepository participanteRepository;
      
      public List<ParticipanteModel> listarParticipantes() {
            return (List<ParticipanteModel>) participanteRepository.findAll();
      }
      public ParticipanteModel buscarParticipante(Long id){
            return participanteRepository.findById(id).orElse(null);
      }
      @Transactional
      public void guardarParticipante(ParticipanteModel participante){
            participanteRepository.save(participante);
      }
      @Transactional
      public void eliminarParticipante(Long idparticipante){
            participanteRepository.deleteById(idparticipante);
      }
      @Transactional
public ParticipanteModel obtenerOPersistirParticipante(int ci, String email, String paterno, String materno, String nombre, String tipo) {
    // Buscar participante por CI
    ParticipanteModel participante = participanteRepository.findByCi(ci);

    if (participante == null) {
        // Si no se encuentra, crear un nuevo participante
        participante = new ParticipanteModel();
        participante.setci(ci);
        participante.setemail(email);
        participante.setpaterno(paterno);
        participante.setmaterno(materno);
        participante.setnombre(nombre);
        participante.setTipo(tipo);

        // Guardar el participante en la base de datos
        participanteRepository.save(participante);
    } else {
        // Si se encuentra un participante con el mismo CI, verificar el tipo
        if (!participante.getTipo().equals(tipo)) {
            // Si el tipo es diferente, crear un nuevo participante
            participante = new ParticipanteModel();
            participante.setci(ci);
            participante.setemail(email);
            participante.setpaterno(paterno);
            participante.setmaterno(materno);
            participante.setnombre(nombre);
            participante.setTipo(tipo);

            // Guardar el nuevo participante en la base de datos
            participanteRepository.save(participante);
        }
        // Si el tipo es el mismo, devolver el participante existente
    }

    return participante;
}



     

}
