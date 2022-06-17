package com.adoptacat.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Entity
@Table(name="tb_gatos")

public class Gato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_gato")
	private long id;
	
	@Column(name = "raza")
	@NotEmpty
	private String raza;
	
	@Column(name = "nombre", columnDefinition = "VARCHAR(45)")
	@NotEmpty
	private String nombre;
	
	@Column(name = "peso", columnDefinition="Decimal(10,0)")
	private double peso;
	
	@Column(name = "edad", columnDefinition = "VARCHAR(45)")
	@NotEmpty
	private String edad;
	
	@Column(name = "estatus")
	private Boolean estatusVisible;
	
	@Column(name = "situacion")
	private Boolean situacion;

	@Column(name = "owner_name", columnDefinition = "VARCHAR(20)")
	private String ownerName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="gato", fetch = FetchType.LAZY)
    private List<Postulacion> postulaciones;

	private String foto;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String icod_raza) {
		this.raza = icod_raza;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String vnombre_gato) {
		this.nombre = vnombre_gato;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double dpeso_gato) {
		this.peso = dpeso_gato;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String vedad_gato) {
		this.edad = vedad_gato;
	}

	public Boolean getEstatusVisible() {
		return estatusVisible;
	}

	public void setEstatusVisible(Boolean flag_estado) {
		this.estatusVisible = flag_estado;
	}

	public Boolean getSituacion() {
		return situacion;
	}

	public void setSituacion(Boolean flg_situacion) {
		this.situacion = flg_situacion;
	}

	public List<Postulacion> getPostulaciones() {
		return postulaciones;
	}

	public void setPostulaciones(List<Postulacion> postulaciones) {
		this.postulaciones = postulaciones;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String idOwner) {
		this.ownerName = idOwner;
	}

	public String getVisible() {
		return getEstatusVisible() ? "Disponible" : "Adoptado";
	}

	public String getEstatusDesc() {
		return getEstatusVisible() ? " Público" : " No Visible";
	}

	@Override
	public String toString() {
		return "Nombre: " + getNombre() + "   - Edad: " + getEdad() + " años " + "   - Peso: " + getPeso() + " kgs "  +
				"   - Raza: " + getRaza();
	}

	public String getReportData() {
		return "Nombre: " + getNombre() + ",    Edad: " + getEdad() + " años " + ",    Peso: " + getPeso() + " kgs "  +
				",    Raza: " + getRaza() + ",  Estado: " + getVisible() +  ",  Dueño: " + getOwnerName();
	}
}
