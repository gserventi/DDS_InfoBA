package com.edu.utn.infoba.modelo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class HorarioComercio extends Horario {
	@ManyToOne
	@JoinColumn(name = "poi_id", referencedColumnName = "id")
	private Comercio comercio;
	
	public HorarioComercio() {
	}
	
	public HorarioComercio(int dia, int horaDesde, int minutoDesde, int horaHasta, int minutoHasta) {
		super(dia, horaDesde, minutoDesde, horaHasta, minutoHasta);
	}

	public Comercio getComercio() {
		return comercio;
	}
	
	public void setComercio(Comercio comercio) {
		this.comercio = comercio;
	}
}
