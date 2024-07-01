package com.pvae.app.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvae.app.models.EstadoModel;
import com.pvae.app.repositories.EstadoRepository;

import jakarta.transaction.Transactional;

@Service
public class EstadoService {
      @Autowired
      private EstadoRepository estadoRepository;

      public List<EstadoModel> listarEstados() {
            return (List<EstadoModel>) estadoRepository.findAll();
      }
      public EstadoModel buscarEstado(Long id){
            return estadoRepository.findById(id).orElse(null);
      }
      @Transactional
      public void guardarEstado(EstadoModel estado){
            estadoRepository.save(estado);
      }
      @Transactional
      public void eliminarEstado(Long idestado){
            estadoRepository.deleteById(idestado);
      }

            
}
