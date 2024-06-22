package com.pvae.app.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "estado")
public class EstadoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long idestado;
    private String descripcion;

    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL)
    private List<CertificadoModel> certificados = new ArrayList<>();;

    public EstadoModel() {
    }

    public long getIdestado() {
        return idestado;
    }

    public void setIdestado(long idestado) {
        this.idestado = idestado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<CertificadoModel> getCertificados() {
        return certificados;
    }

    public void setCertificados(List<CertificadoModel> certificados) {
        this.certificados = certificados;
    }

}
