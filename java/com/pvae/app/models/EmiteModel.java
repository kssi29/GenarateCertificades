package com.pvae.app.models;

import jakarta.persistence.*;


@Entity
@Table(name = "emite")
public class EmiteModel {

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique =true , nullable =false)
    private Long idemite;

    

    @ManyToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "idevento")
    private EventoModel evento;

    @ManyToOne
    @JoinColumn(name = "unidad_id", referencedColumnName = "idunidad")
    private UnidadModel unidad;

    public EmiteModel() {
    }

    public EmiteModel(EventoModel evento, UnidadModel unidad) {
        this.evento = evento;
        this.unidad = unidad;
    }



    public Long getIdemite() {
        return idemite;
    }

    public void setIdemite(Long idemite) {
        this.idemite = idemite;
    }

    public EventoModel getEvento() {
        return evento;
    }

    public void setEvento(EventoModel evento) {
        this.evento = evento;
    }

    public UnidadModel getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadModel unidad) {
        this.unidad = unidad;
    }

    

}
