package com.edu.utn.infoba.modelo.mongodb;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

public class ServicioMongoDB {
	
	private String nombre;
	@Embedded
	private List<RangosServicioMongoDB> rangosServicio;
	
	public ServicioMongoDB() {}
	
	public ServicioMongoDB(String nombre) {
		this.nombre = nombre;
	}
	
	public ServicioMongoDB(String nombre, List<RangosServicioMongoDB> rangosServicio) {
		this.nombre = nombre;
		this.rangosServicio = rangosServicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<RangosServicioMongoDB> getRangosServicio() {
		return rangosServicio;
	}

	public void setRangosServicio(List<RangosServicioMongoDB> rangosServicio) {
		this.rangosServicio = rangosServicio;
	}
}
