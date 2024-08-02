package com.pvae.app.models;


import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "autoridad")
public class AutoridadModel extends PersonaModel {
    private String cargo;
    @Column(name = "imagen_firma", nullable = true)
    private String imagenFirma;


    @OneToMany(mappedBy = "autoridad", cascade = CascadeType.ALL)
    private List<FirmaCertModel> firmanCertificados;


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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public List<FirmaCertModel> getFirmanCertificados() {
        return firmanCertificados;
    }

    public void setFirmanCertificados(List<FirmaCertModel> firmanCertificados) {
        this.firmanCertificados = firmanCertificados;
    }
}
