package com.pvae.app.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<CertificadoModel> certificados = new ArrayList<>();;


    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<FirmaModel> firmas = new ArrayList<>();

     
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List <EmiteModel> emites = new ArrayList<>();;




    public EventoModel() {
    }
  

    public EventoModel(String nombre) {
        this.nombre = nombre;
    }

    public EventoModel(String nombre,ArrayList<CertificadoModel> certificados) {
        this.nombre = nombre;
        this.certificados = certificados;
    }

    public EventoModel(List<CertificadoModel> certificados, List<EmiteModel> emites, String nombre) {
        this.certificados = certificados;
        this.emites = emites;
        this.nombre = nombre;
    }


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


    public List<FirmaModel> getFirmas() {
        return firmas;
    }


    public void setFirmas(List<FirmaModel> firmas) {
        this.firmas = firmas;
    }


    public List<CertificadoModel> getCertificados() {
        return certificados;
    }


    public void setCertificados(List<CertificadoModel> certificados) {
        this.certificados = certificados;
    }


    public List<EmiteModel> getEmites() {
        return emites;
    }


    public void setEmites(List<EmiteModel> emites) {
        this.emites = emites;
    }
    
    

}
