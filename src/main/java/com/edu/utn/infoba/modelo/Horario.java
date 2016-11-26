package com.edu.utn.infoba.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "horario")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Horario extends Persistible {
	private int dia;
	private int horaDesde;
	private int minutoDesde;
	private int horaHasta;
	private int minutoHasta;
	
	public Horario() {
	}
	
	public Horario(int dia, int horaDesde, int minutoDesde, int horaHasta, int minutoHasta) {
		this.dia = dia;
		this.horaDesde = horaDesde;
		this.minutoDesde = minutoDesde;
		this.horaHasta = horaHasta;
		this.minutoHasta = minutoHasta;
	}

	@Column(name = "dia")
	public int getDia() {
		return dia;
	}
	
	public void setDia(int dia) {
		this.dia = dia;
	}
	
	@Column(name = "hora_desde")
	public int getHoraDesde() {
		return horaDesde;
	}
	
	public void setHoraDesde(int horaDesde) {
		this.horaDesde = horaDesde;
	}
	
	@Column(name = "minuto_desde")
	public int getMinutoDesde() {
		return minutoDesde;
	}
	
	public void setMinutoDesde(int minutoDesde) {
		this.minutoDesde = minutoDesde;
	}
	
	@Column(name = "hora_hasta")
	public int getHoraHasta() {
		return horaHasta;
	}
	
	public void setHoraHasta(int horaHasta) {
		this.horaHasta = horaHasta;
	}
	
	@Column(name = "minuto_hasta")
	public int getMinutoHasta() {
		return minutoHasta;
	}
	
	public void setMinutoHasta(int minutoHasta) {
		this.minutoHasta = minutoHasta;
	}
	
	@SuppressWarnings("deprecation")
	public boolean coincideTiempo(Date tiempo) {
		String diaNumero = new SimpleDateFormat("u").format(tiempo);

		if(Integer.parseInt(diaNumero) == this.getDia()) {
			if(tiempo.getHours() < this.getHoraHasta() && tiempo.getHours() > this.getHoraDesde()) {	
				return true;
			}
			else if (tiempo.getHours() == this.getHoraHasta()) {
				if(tiempo.getMinutes() <= this.getMinutoHasta() ) {
					return true;
				}
			}
			else if (tiempo.getHours() == this.getHoraDesde()) {
				if(tiempo.getMinutes() >= this.getMinutoDesde()) {
					return true;
				}
			}
		}
		return false;
	}
}
