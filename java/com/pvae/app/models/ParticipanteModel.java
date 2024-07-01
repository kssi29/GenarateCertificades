package com.pvae.app.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "participante")
public class ParticipanteModel extends PersonaModel{
    private String tipo;
    
    @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL)
    private List<CertificadoModel> certificados;

    public ParticipanteModel() {
    
    }


    public ParticipanteModel(String tipo, int ci, String email,  String materno, String nombre, String paterno) {
        super(ci, email,  materno, nombre, paterno);
        this.tipo = tipo;
    }
    public ParticipanteModel(String tipo, int ci, String email,  String materno, String nombre, String paterno, List<CertificadoModel> certificados) {
        super(ci, email,  materno, nombre, paterno);
        this.tipo = tipo;
        this.certificados = certificados;
        
    }

    public String getNombre(){
        return super.getnombre();
    }

    public String getPaterno(){
        return this.getpaterno();
    }

    public String getMaterno(){
        return this.getmaterno();
    }   
    public int getCi(){
        return this.getci();
    }   
    public String getEmail(){
        return this.getemail();
    }

    
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public List<CertificadoModel> getCertificados() {
        return certificados;
    }


    public void setCertificados(List<CertificadoModel> certificados) {
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
