package com.pvae.app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class UsuarioModel extends PersonaModel {
    private String rol;

    public UsuarioModel() {
    }

    public UsuarioModel(String rol, int ci, String email, String materno, String nombre, String paterno) {
        super(ci, email, materno, nombre, paterno);
        this.rol = rol;
    }



    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    



}
