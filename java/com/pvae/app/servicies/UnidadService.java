package com.pvae.app.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvae.app.models.UnidadModel;
import com.pvae.app.repositories.UnidadRepository;

@Service
public class UnidadService {
      @Autowired
      private UnidadRepository unidadRepository;

      public List<UnidadModel> listarUnidades() {
            return (List<UnidadModel>) unidadRepository.findAll();
      }

      public UnidadModel guardarUnidad(UnidadModel unidad) {
            return unidadRepository.save(unidad);
      }

      public UnidadModel buscarUnidad(Long id) {
            return unidadRepository.findById(id).orElse(null);
      }     

      public void eliminarUnidad(Long id) {
            unidadRepository.deleteById(id);
      }

}
