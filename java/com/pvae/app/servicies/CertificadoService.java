package com.pvae.app.servicies;

import java.util.List;

import com.pvae.app.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvae.app.repositories.CertificadoRepository;

import jakarta.transaction.Transactional;

@Service
public class CertificadoService {
    private final CertificadoRepository certificadoRepository;

    public CertificadoService(CertificadoRepository certificadoRepository) {
        this.certificadoRepository = certificadoRepository;
    }


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
    public CertificadoModel guardarCertificado(EventoModel evento, ParticipanteModel participante) {
        CertificadoModel certificado = new CertificadoModel();
        certificado.setEvento(evento);
        certificado.setParticipante(participante);
        return certificadoRepository.save(certificado);


    }

    public boolean existeCertificado(Long participanteId, Long eventoId) {
        return certificadoRepository.existsByParticipante_IdpersonaAndEvento_Idevento(participanteId, eventoId);
    }






}
