package com.pvae.app.servicies;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pvae.app.models.ParticipanteModel;
import com.pvae.app.repositories.ParticipanteRepository;

import jakarta.transaction.Transactional;

@Service
public class ParticipanteService {

      private final ParticipanteRepository participanteRepository;

      public ParticipanteService(ParticipanteRepository participanteRepository) {
            this.participanteRepository = participanteRepository;
      }

      public List<ParticipanteModel> listarParticipantes() {
            return (List<ParticipanteModel>) participanteRepository.findAll();
      }

      public ParticipanteModel buscarParticipante(Long id) {
            return participanteRepository.findById(id).orElse(null);
      }

      @Transactional
      public void guardarParticipante(ParticipanteModel participante) {
            participanteRepository.save(participante);
      }

      @Transactional
      public void eliminarParticipante(Long idparticipante) {
            participanteRepository.deleteById(idparticipante);
      }

      @Transactional
      public ParticipanteModel obtenerOPersistirParticipante(int ci, String email, String paterno, String materno,
                  String nombre, String tipo) {

            ParticipanteModel participante = participanteRepository.findByCi(ci);

            if (participante == null) {

                  participante = new ParticipanteModel();
                  participante.setci(ci);
                  participante.setemail(email);
                  participante.setpaterno(paterno);
                  participante.setmaterno(materno);
                  participante.setnombre(nombre);
                  participante.setTipo(tipo);

                  participanteRepository.save(participante);
            } else {

                  if (!participante.getTipo().equals(tipo)) {

                        participante = new ParticipanteModel();
                        participante.setci(ci);
                        participante.setemail(email);
                        participante.setpaterno(paterno);
                        participante.setmaterno(materno);
                        participante.setnombre(nombre);
                        participante.setTipo(tipo);

                        participanteRepository.save(participante);
                  }

            }

            return participante;
      }

}
