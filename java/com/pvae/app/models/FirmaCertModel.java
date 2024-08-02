package com.pvae.app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "firmaCertificado")
public class FirmaCertModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idfirmaCertificado;

    @ManyToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "idevento")
    private EventoModel evento;

    @ManyToOne
    @JoinColumn(name = "autoridad_id", referencedColumnName = "idpersona")
    private AutoridadModel autoridad;

    public Long getIdfirmaCertificado() {
        return idfirmaCertificado;
    }

    public void setIdfirmaCertificado(Long idfirmaCertificado) {
        this.idfirmaCertificado = idfirmaCertificado;
    }

    public EventoModel getEvento() {
        return evento;
    }

    public void setEvento(EventoModel evento) {
        this.evento = evento;
    }

    public AutoridadModel getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(AutoridadModel autoridad) {
        this.autoridad = autoridad;
    }

}


