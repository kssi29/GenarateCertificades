package com.pvae.app.servicies;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pvae.app.models.AutoridadModel;
import com.pvae.app.repositories.AutoridadRepository;

import jakarta.transaction.Transactional;


@Service
public class AutoridadService {

      private final AutoridadRepository autoridadRepository;
      public AutoridadService(AutoridadRepository autoridadRepository) {
            this.autoridadRepository = autoridadRepository;
      }     

      public List<AutoridadModel> listarAutoridades() {
            return (List<AutoridadModel>) autoridadRepository.findAll();
      }
      public AutoridadModel buscarAutoridad(Long id) {
            return autoridadRepository.findById(id).orElse(null);
      }
      @Transactional
      public void guardarAutoridad(AutoridadModel autoridad) {
            autoridadRepository.save(autoridad);
      }
      @Transactional
      public void eliminarAutoridad(Long idautoridad) {
            autoridadRepository.deleteById(idautoridad);    
      }
      
}
