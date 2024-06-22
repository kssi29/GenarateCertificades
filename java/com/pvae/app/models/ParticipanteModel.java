package com.pvae.app.models;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "participante")
public class ParticipanteModel extends PersonaModel{
    private String tipo;
    
    @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL)
    private ArrayList<CertificadoModel> certificados;

    public ParticipanteModel() {
        this.certificados = new ArrayList<>();
    }
    

    public ParticipanteModel(String tipo, int ci, String email,  String materno, String nombre, String paterno) {
        super(ci, email,  materno, nombre, paterno);
        this.tipo = tipo;
    }
    public ParticipanteModel(String tipo, int ci, String email,  String materno, String nombre, String paterno, ArrayList<CertificadoModel> certificados) {
        super(ci, email,  materno, nombre, paterno);
        this.tipo = tipo;
        this.certificados = certificados;
        
    }

    
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public ArrayList<CertificadoModel> getCertificados() {
        return certificados;
    }


    public void setCertificados(ArrayList<CertificadoModel> certificados) {
        this.certificados = certificados;
    }

    public void addCertificado(CertificadoModel certificado) {
        certificados.add(certificado);
        certificado.setParticipante(this);
    }

    public void removeCertificado(CertificadoModel certificado) {
        certificados.remove(certificado);
        certificado.setParticipante(null);
    }

    

}
