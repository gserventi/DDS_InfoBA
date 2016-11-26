package com.edu.utn.infoba.modelo.mongodb;

public class RangosServicioMongoDB {
	
	private int diaSemana;
	private int horaDesde;
	private int minutoDesde;
	private int horaHasta;
	private int minutoHasta;
	
	public RangosServicioMongoDB(int diaSemana, int horaDesde, int minutoDesde, int horaHasta, int minutoHasta) {
		this.diaSemana = diaSemana;
		this.horaDesde = horaDesde;
		this.minutoDesde = minutoDesde;
		this.horaHasta = horaHasta;
		this.minutoHasta = minutoHasta;
	}

	public int getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}

	public int getHoraDesde() {
		return horaDesde;
	}

	public void setHoraDesde(int horaDesde) {
		this.horaDesde = horaDesde;
	}

	public int getMinutoDesde() {
		return minutoDesde;
	}

	public void setMinutoDesde(int minutoDesde) {
		this.minutoDesde = minutoDesde;
	}

	public int getHoraHasta() {
		return horaHasta;
	}

	public void setHoraHasta(int horaHasta) {
		this.horaHasta = horaHasta;
	}

	public int getMinutoHasta() {
		return minutoHasta;
	}

	public void setMinutoHasta(int minutoHasta) {
		this.minutoHasta = minutoHasta;
	}
}
