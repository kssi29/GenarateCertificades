package com.pvae.app.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "certificado")
public class CertificadoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idcertificado;

    @ManyToOne
    @JoinColumn(name = "participante_id", referencedColumnName = "idpersona")
    private ParticipanteModel participante;

    @ManyToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "idevento")
    private EventoModel evento;

    @OneToMany(mappedBy = "certificado")
    private List<FirmaCertModel> firmaCerts;





    

    public Long getIdcertificado() {
        return idcertificado;
    }

    public void setIdcertificado(Long idcertificado) {
        this.idcertificado = idcertificado;
    }

    public ParticipanteModel getParticipante() {
        return participante;
    }

    public void setParticipante(ParticipanteModel participante) {
        this.participante = participante;
    }

    public EventoModel getEvento() {
        return evento;
    }

    public void setEvento(EventoModel evento) {
        this.evento = evento;
    }

    /* 
    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }
*/

}
