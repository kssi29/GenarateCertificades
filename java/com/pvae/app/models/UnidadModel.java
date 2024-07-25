package com.pvae.app.models;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "unidad",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
public class UnidadModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idunidad", unique = true, nullable = false)
    private Long idunidad;

    @Column(name = "nombre", unique = true, nullable = false)
    @NotEmpty
    private String nombre;
   
    @OneToMany(mappedBy = "unidad", cascade = CascadeType.ALL)
    private List <EmiteModel> emites;

    @OneToMany(mappedBy = "unidadPadre", cascade = CascadeType.ALL)
    private List<UnidadModel> subunidades; 

    @NotNull
    @ManyToOne
    @JoinColumn(name = "unidad_padre_id")
    private UnidadModel unidadPadre;

    @OneToMany(mappedBy = "unidad", cascade = CascadeType.ALL)
    private List<EventoModel> eventos;
 

    public UnidadModel() {
    }

    public UnidadModel(String nombre) {
        this.nombre = nombre;
    }

    public UnidadModel(List<EmiteModel> emites,String nombre) {
        this.emites = emites;
        this.nombre = nombre;
    }

    

    public UnidadModel(String nombre, List<UnidadModel> subunidades) {

        this.nombre = nombre;
        this.subunidades = subunidades;
    }


    public Long getIdunidad() {
        return idunidad;
    }

    public void setIdunidad(Long idunidad) {
        this.idunidad = idunidad;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<EmiteModel> getEmites() {
        return emites;
    }

    public void setEmites(List<EmiteModel> emites) {
        this.emites = emites;
    }

    public List<UnidadModel> getSubunidades() {
        return subunidades;
    }

    public void setSubunidades(List<UnidadModel> subunidades) {
        this.subunidades = subunidades;
    }

    public UnidadModel getUnidadPadre() {
        return unidadPadre;
    }

    public void setUnidadPadre(UnidadModel unidadPadre) {
        this.unidadPadre = unidadPadre;
    }

    public List<EventoModel> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoModel> eventos) {
        this.eventos = eventos;
    }



}
