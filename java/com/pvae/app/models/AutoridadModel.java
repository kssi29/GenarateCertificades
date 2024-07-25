package com.pvae.app.models;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autoridad")
public class AutoridadModel extends PersonaModel {
    private String cargo;
    

    @OneToMany(mappedBy = "autoridad", cascade = CascadeType.ALL)
    private List <FirmaModel> firmas;

    

    public AutoridadModel() {
    }

    public AutoridadModel(String cargo, int ci, String email,  String materno, String nombre, String paterno) {
        super(ci, email, materno, nombre, paterno);
        this.cargo = cargo;
    }

    @Override
    public String getnombre(){
        return super.getnombre();
    }
    @Override
    public String getpaterno(){
        return super.getpaterno();
    }
    @Override
    public String getmaterno(){
        return super.getmaterno();
    }
    @Override
    public int getci(){
        return super.getci();
    }
    


    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public List<FirmaModel> getFirmas() {
        return firmas;
    }

    public void setFirmas(List<FirmaModel> firmas) {
        this.firmas = firmas;
    }

   
    

}
