package com.edu.utn.infoba.modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "provincia")
public class Provincia extends Persistible {
	
	@Column(name = "nombre")
	private String nombre;
	
    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL)
	private List<Direccion> direcciones;
	
	public Provincia() {}
	
	public Provincia(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Direccion> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(List<Direccion> direcciones) {
		this.direcciones = direcciones;
	}
}
