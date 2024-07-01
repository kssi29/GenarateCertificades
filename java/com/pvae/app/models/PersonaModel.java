package com.pvae.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PersonaModel {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique =true , nullable =false)
    private Long idpersona;
    private String paterno;
    private String materno;
    private String nombre;
    private String email;
    private int ci;

    public PersonaModel() {
    }

    public PersonaModel(int ci, String email,  String materno, String nombre, String paterno) {
        this.ci = ci;
        this.email = email;
        this.materno = materno;
        this.nombre = nombre;
        this.paterno = paterno;
    }


    
    public Long getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Long idpersona) {
        this.idpersona = idpersona;
    }

    public String getpaterno() {
        return paterno;
    }

    public void setpaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getmaterno() {
        return materno;
    }

    public void setmaterno(String materno) {
        this.materno = materno;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public int getci() {
        return ci;
    }

    public void setci(int ci) {
        this.ci = ci;
    }


    

}
