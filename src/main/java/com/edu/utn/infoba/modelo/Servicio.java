package com.edu.utn.infoba.modelo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "servicio")
public class Servicio extends Persistible {
	private String nombre;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "servicio_id", referencedColumnName = "id")
	private Set<HorarioServicio> horarioServicios;
	
	@ManyToOne
	@JoinColumn(name = "poi_id", referencedColumnName = "id")
	private PuntoDeInteres puntoDeInteres;
	
	public Servicio(){
	}
	
	public Servicio(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public PuntoDeInteres getPuntoDeInteres() {
		return puntoDeInteres;
	}
	
	public void setPuntoDeInteres(PuntoDeInteres puntoDeInteres) {
		this.puntoDeInteres = puntoDeInteres;
	}
	
	public Set<HorarioServicio> getHorarioServicio() {
		return this.horarioServicios;
	}
		
	public void setHorarioServicio(Set<HorarioServicio> horarioServicio) {
		this.horarioServicios = horarioServicio;
	}
		
	public void agregarHorarioServicio(HorarioServicio horarioServicio) {
		if (horarioServicios == null) {
			horarioServicios = new HashSet<>();
		}
		if (!horarioServicios.contains(horarioServicio))
			horarioServicios.add(horarioServicio);
	}
		
	public void removerHorarioServicio(HorarioServicio horarioServicio) {
		if(horarioServicios.contains(horarioServicio))
			horarioServicios.remove(horarioServicio);
	}
		
	public void limpiarHorarioServicio() {
		horarioServicios.clear();
	}
	
	public boolean estaDisponible(Date tiempo) {
		for (HorarioServicio s : this.horarioServicios) {
		    if(s.coincideTiempo(tiempo)) {
		    	return true;
		    }
		}
		return false;
	}
}
