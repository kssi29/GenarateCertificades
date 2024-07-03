package com.pvae.app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "estado")
public class EstadoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idestado;
    private String descripcion;

    /*
     * @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL)
     * private List<CertificadoModel> certificados = new ArrayList<>();;
     */
    public EstadoModel() {
    }

    public Long getIdestado() {
        return idestado;
    }

    public void setIdestado(Long idestado) {
        this.idestado = idestado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /*
     * public List<CertificadoModel> getCertificados() {
     * return certificados;
     * }
     * 
     * public void setCertificados(List<CertificadoModel> certificados) {
     * this.certificados = certificados;
     * }
     */

}
