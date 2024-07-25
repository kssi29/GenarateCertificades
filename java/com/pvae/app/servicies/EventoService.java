package com.pvae.app.servicies;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pvae.app.models.EventoModel;

import com.pvae.app.repositories.EventoRepository;

import jakarta.transaction.Transactional;

@Service
public class EventoService {
      private final EventoRepository eventoRepository;

      public EventoService(EventoRepository eventoRepository) {
            this.eventoRepository = eventoRepository;
      }

      public List<EventoModel> listarEventos() {
            return (List<EventoModel>) eventoRepository.findAll();
      }

      public EventoModel buscarEvento(Long id) {
            return eventoRepository.findById(id).orElse(null);
      }

      @Transactional
      public void guardarEvento(EventoModel evento) {
            eventoRepository.save(evento);
      }

      @Transactional
      public void eliminarEvento(Long idevento) {
            eventoRepository.deleteById(idevento);
      }

}
