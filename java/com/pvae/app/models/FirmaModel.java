package com.pvae.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "firma")
public class FirmaModel {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idfirma;

    @ManyToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "idevento")
    private EventoModel evento;

    @ManyToOne
    @JoinColumn(name = "autoridad_id", referencedColumnName = "idpersona")
    private AutoridadModel autoridad;

    public FirmaModel() {
    }

    public FirmaModel(AutoridadModel autoridad, EventoModel evento) {
        this.autoridad = autoridad;
        this.evento = evento;
    }



    public long getIdfirma() {
        return idfirma;
    }

    public void setIdfirma(long idfirma) {
        this.idfirma = idfirma;
    }

    public EventoModel getevento() {
        return evento;
    }

    public void setevento(EventoModel evento) {
        this.evento = evento;
    }

    public AutoridadModel getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(AutoridadModel autoridad) {
        this.autoridad = autoridad;
    }



}
