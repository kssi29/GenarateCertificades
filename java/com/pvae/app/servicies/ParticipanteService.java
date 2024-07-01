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



}
