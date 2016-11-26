package com.edu.utn.infoba.modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("busqueda")
public class Busqueda {
	@Id
	private ObjectId id;

	@Embedded
	private List<Long> idsPuntoDeInteres = new ArrayList<Long>();

	private Integer cantResultados;
	private Double duracion;
	private String texto;
	private Date fechaBusqueda;
	private String fechaFormato;
	private String usuario;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public Busqueda() {}
	
	public Busqueda(String user, List<PuntoDeInteres> puntosDeInteres, int cantResultados, Double duracion, 
			String texto, Date fecha, String fechaFormato) {
		this.setPuntoDeInteres(puntosDeInteres);
		this.cantResultados = cantResultados;
		this.duracion = duracion;
		this.texto = texto;
		this.fechaBusqueda = fecha;
		this.fechaFormato = sdf.format(fecha);
		this.usuario = user;
	}
	public List<Long> getIdsPuntoDeInteres() {
		 		return idsPuntoDeInteres;
	}

	public String getUsuario() {
		return this.usuario;
	}
	
	public void setUsuario(String user) {
		this.usuario = user;
	}

	public int getCantResultados() {
		return this.cantResultados;
	}
	
	public void setCantResultados(int cantidad) {
		this.cantResultados = cantidad;
	}

	public Double getDuracion() {
		return this.duracion;
	}
	
	public void setDuracion(Double duracion) {
		this.duracion = duracion;
	}

	public String getTexto() {
		return this.texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getFechaBusqueda() {
		return this.fechaBusqueda;
	}
	
	public void setFechaBusqueda(Date fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
		this.fechaFormato = sdf.format(fechaBusqueda);
	}

	public String getFechaFormato() {
		return this.fechaFormato;
	}
	
	public void setFechaFormato(String fechaFormato) {
		this.fechaFormato = fechaFormato;
	}
	public void setPuntoDeInteres(List<PuntoDeInteres> puntosDeInteres) {
		 		for(PuntoDeInteres poi : puntosDeInteres){
		 			idsPuntoDeInteres.add(poi.getId());
		 		}	
	
	}

}
