package com.pvae.app.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "participante")
public class ParticipanteModel extends PersonaModel {
    private String tipo;

    @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL)
    private List<CertificadoModel> certificados;

    public String getNombre() {
        return super.getnombre();
    }

    public String getPaterno() {
        return this.getpaterno();
    }

    public String getMaterno() {
        return this.getmaterno();
    }

    public int getCi() {
        return this.getci();
    }

    public String getEmail() {
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


}
