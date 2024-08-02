package com.pvae.app.models;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="evento")
public class EventoModel {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique =true , nullable =false)
    private Long idevento;
    private String nombre;
    @Column(name = "imagen_fondo", nullable = true)
    private String imagenFondo;

    @ManyToOne
    @JoinColumn(name = "unidad_id",referencedColumnName = "idunidad")
    private UnidadModel unidad;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<CertificadoModel> certificados = new ArrayList<>();

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<FirmaCertModel> firmaCerts = new ArrayList<>();






    public Long getIdevento() {
        return idevento;
    }


    public void setIdevento(Long idevento) {
        this.idevento = idevento;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public List<CertificadoModel> getCertificados() {
        return certificados;
    }


    public void setCertificados(List<CertificadoModel> certificados) {
        this.certificados = certificados;
    }




    public String getImagenFondo() {
        return imagenFondo;
    }


    public void setImagenFondo(String imagenFondo) {
        this.imagenFondo = imagenFondo;
    }



    public UnidadModel getUnidad() {
        return unidad;
    }


    public void setUnidad(UnidadModel unidad) {
        this.unidad = unidad;
    }
    
    
    

}
