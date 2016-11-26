package com.edu.utn.infoba.modelo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class HorarioServicio extends Horario {
	@ManyToOne
	@JoinColumn(name = "servicio_id", referencedColumnName = "id")
	private Servicio servicio;
	
	public HorarioServicio() {
	}
	
	public HorarioServicio(int dia, int horaDesde, int minutoDesde, int horaHasta, int minutoHasta) {
		super(dia, horaDesde, minutoDesde, horaHasta, minutoHasta);
	}

	public Servicio getServicio() {
		return servicio;
	}
	
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
}
