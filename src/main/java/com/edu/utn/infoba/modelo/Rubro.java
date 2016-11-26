package com.edu.utn.infoba.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "rubro")
public class Rubro extends Persistible
{
	private String nombre;
	private Double radioCercania;
	
	public Rubro(){
	}
	
	public Rubro(String nombre, Double radioCercania) {
		this.nombre = nombre;
		this.radioCercania = radioCercania;
	}
	
	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "radio_cercania")
	public Double getRadioCercania() {
		return radioCercania;
	}
	
	public void setRadioCercania(Double radioCercania) {
		this.radioCercania = radioCercania;
	}
}
