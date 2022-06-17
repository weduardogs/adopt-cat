package com.adoptacat.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@Entity
@Table(name = "tb_postulacion")
public class Postulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postulacion")
    private long id;

    @Column(name = "id_usuario")
    private long idUsuario;

    @Column(name = "name")
    @NotEmpty
    private String nombreUsuario;

    @Column(name = "edad")
    private int edad;

    @Column(name = "email")
    @NotEmpty
    private String usuarioEmail;

    @Column(name = "fecha_postulacion")
    private Date fechaPostulacion;

    @Column(name = "motivo_postula")
    @NotEmpty
    private String motivoPostu;

    @Column(name = "estatus")
    private Boolean estatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_gato", referencedColumnName = "id_gato")
    private Gato gato;

    private String tuvoGato;

    private String tieneEspacio;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id_postulacion) {
        this.id = id_postulacion;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long icod_usuario) {
        this.idUsuario = icod_usuario;
    }

    public Date getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(Date dfecha_postulacion) {
        this.fechaPostulacion = dfecha_postulacion;
    }

    public String getMotivoPostu() {
        return motivoPostu;
    }

    public void setMotivoPostu(String tmotivo_postula) {
        this.motivoPostu = tmotivo_postula;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean flag_estado) {
        this.estatus = flag_estado;
    }

    public Gato getGato() {
        return this.gato;
    }

    public void setGato(Gato gato) {
        this.gato = gato;
    }

    public String getTieneEspacio() {
        return tieneEspacio;
    }

    public void setTieneEspacio(String tieneEspacio) {
        this.tieneEspacio = tieneEspacio;
    }

    public String getTuvoGato() {
        return tuvoGato;
    }

    public void setTuvoGato(String tuvoGato) {
        this.tuvoGato = tuvoGato;
    }

    public long getIdGato() {
        return gato.getId();
    }

    public String getEmail() {
        return this.getUsuarioEmail();
    }

    public String getEstatusDesc() {
    	return getEstatus() ? "Aceptado" : "Pendiente";
	}

    public String getTuvoGatoDesc() {
        return "S".equals(getTuvoGato()) ? "Sí" : "No";
    }

    public String getTieneEspDesc() {
        return "S".equals(getTieneEspacio()) ? "Sí" : "No";
    }

    public String applicationDetails() {
        return "Nombre: " + getNombreUsuario() + " - Edad: " + getEdad() + " - Email: " + getEmail() +
                " - Motivo: " + getMotivoPostu() + " - gato id: " + getGato().getId() +  " - Estatus: " + getEstatusDesc() +
                " - Tuvo gato: " + getTuvoGatoDesc() + " - Tiene espacio: " + getTieneEspDesc();
    }



}
