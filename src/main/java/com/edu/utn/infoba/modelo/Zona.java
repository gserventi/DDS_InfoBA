package com.edu.utn.infoba.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "zona")
public class Zona extends Persistible
{
	private String nombre;

	@ManyToOne
	@JoinColumn(name = "poi_id", referencedColumnName = "id")
	private CGP cgp;

	public Zona() {}
	
	public Zona(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public CGP getCGP() {
		return this.cgp;
	}
	
	public void setCGP(CGP cgp) {
		this.cgp = cgp;
	}
}
