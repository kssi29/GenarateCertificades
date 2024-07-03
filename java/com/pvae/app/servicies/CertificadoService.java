package com.pvae.app.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvae.app.models.CertificadoModel;
import com.pvae.app.models.EventoModel;
import com.pvae.app.models.ParticipanteModel;
import com.pvae.app.repositories.CertificadoRepository;

import jakarta.transaction.Transactional;

@Service    
public class CertificadoService {
      @Autowired
      private CertificadoRepository certificadoRepository;

      public List<CertificadoModel> listarCertificados() {
            return (List<CertificadoModel>) certificadoRepository.findAll();
      }
      public CertificadoModel buscarCertificado(Long id) {
            return certificadoRepository.findById(id).orElse(null);
      }     

      @Transactional                
      public void guardarCertificado(CertificadoModel certificado) {
          certificadoRepository.save(certificado);
      }

      @Transactional
      public void eliminarCertificado(Long idcertificado) {
          certificadoRepository.deleteById(idcertificado);
      }

      @Transactional
      public void guardarCertificado(EventoModel evento, ParticipanteModel participante) {
        CertificadoModel certificado = new CertificadoModel();
        certificado.setEvento(evento);
        certificado.setParticipante(participante);
        certificadoRepository.save(certificado);


    }
    public CertificadoModel buscarCertificadoPorEventoYParticipante(EventoModel evento, ParticipanteModel participante) {
        return certificadoRepository.findByEventoAndParticipante(evento, participante);
    }

      
}
