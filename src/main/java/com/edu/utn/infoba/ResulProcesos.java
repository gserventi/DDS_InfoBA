package com.edu.utn.infoba;

public class ResulProcesos
{
	private String nombre;
	private long tiempo;
	private String fechainicio;
	private String fechafinal;
	private Boolean respuesta;
	private String msjresultado;
	
	public ResulProcesos(String nombre, long tiempo, String fechainicio, String fechafinal, Boolean respuesta,String msjresultado)
	{
		this.nombre = nombre;
		this.tiempo = tiempo;
		this.fechainicio = fechainicio;
		this.fechafinal = fechafinal;
		this.respuesta = respuesta;
		this.msjresultado = msjresultado;
	}

	public String getMsjresultado() {
		return this.msjresultado;
	}
	public void setMsjresultado(String msjresultado) {
		this.msjresultado = msjresultado;
	}
	public Boolean getRespuesta()
    {
		return this.respuesta;
	}
	public void setRespuesta(Boolean resultado) {
		this.respuesta = resultado;
	}
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getTiempo() {
		return this.tiempo;
	}
	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}
	public String getFechainicio() {
		return this.fechainicio;
	}
	public void setFechainicio(String string) {
		this.fechainicio = string;
	}
	public String getFechafinal() {
		return this.fechafinal;
	}
	public void setFechafinal(String fechafinal) {
		this.fechafinal = fechafinal;
	}

}
