package com.pvae.app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "firma")
public class FirmaCertModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idfirmaCertificado;

    @ManyToOne
    @JoinColumn(name = "certificado_id", referencedColumnName = "idcertificado")
    private CertificadoModel certificado;

    @ManyToOne
    @JoinColumn(name = "autoridad_id", referencedColumnName = "idpersona")
    private AutoridadModel autoridad;

    public Long getIdfirmaCertificado() {
        return idfirmaCertificado;
    }

    public CertificadoModel getCertificado() {
        return certificado;
    }

    public void setCertificado(CertificadoModel certificado) {
        this.certificado = certificado;
    }

    public AutoridadModel getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(AutoridadModel autoridad) {
        this.autoridad = autoridad;
    }

}


