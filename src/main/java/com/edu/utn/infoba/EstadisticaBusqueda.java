package com.edu.utn.infoba;

import java.util.Date;

public class EstadisticaBusqueda
{
	private String usuario;
	private Integer cantResultados;
	private Double duracion;
	private String texto;
	private Date fechaBusqueda;
	private String fechaFormato;
	
	public EstadisticaBusqueda(String usr, int cantRes, Double dur, String txt, Date fecha, String fechaF)
	{
		this.usuario = usr;
		this.cantResultados = cantRes;
		this.duracion = dur;
		this.texto = txt;
		this.fechaBusqueda = fecha;
		this.fechaFormato = fechaF;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public int getCantResultados() {
		return this.cantResultados;
	}

	public Double getDuracion() {
		return this.duracion;
	}

	public String getTexto() {
		return this.texto;
	}

	public Date getFechaBusqueda() {
		return this.fechaBusqueda;
	}

	public String getFechaFormato() {
		return this.fechaFormato;
	}
}
