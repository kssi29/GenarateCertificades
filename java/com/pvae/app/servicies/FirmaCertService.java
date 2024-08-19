package com.pvae.app.servicies;

import com.pvae.app.models.AutoridadModel;
import com.pvae.app.models.CertificadoModel;
import com.pvae.app.models.EventoModel;
import com.pvae.app.models.FirmaCertModel;
import com.pvae.app.repositories.FirmaCertRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirmaCertService {


    @Autowired
    private FirmaCertRepository firmaCertRepository;


    public List<FirmaCertModel> listarFirmaCert() {
        return (List<FirmaCertModel>) firmaCertRepository.findAll();
    }

    public FirmaCertModel buscarFirmaCert(Long id) {
        return firmaCertRepository.findById(id).orElse(null);

    }

    @Transactional
    public void guardarFirmaCert(FirmaCertModel firmaCert) {
        firmaCertRepository.save(firmaCert);
    }

    @Transactional
    public void eliminarFirmaCert(Long id) {
        firmaCertRepository.deleteById(id);
    }

    @Transactional
    public void ligarFirmaCert(CertificadoModel certificado, AutoridadModel autoridad ) {
        FirmaCertModel firmaCert = new FirmaCertModel();
        firmaCert.setAutoridad(autoridad);
        firmaCert.setCertificado(certificado);
        firmaCertRepository.save(firmaCert);
    }
}
