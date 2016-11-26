package com.edu.utn.infoba.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@SuppressWarnings("serial")
@Entity
@Table(name = "acciones_usuario")
public class AccionesUsuario extends Persistible
{
	private String nombre;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	public AccionesUsuario() {	}
	
	public AccionesUsuario(String nombre) {	
		this.nombre = nombre;
	}
	
	public AccionesUsuario(String nombre, Usuario usuario) {	
		this.nombre = nombre;
		this.usuario = usuario;
	}
	
	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
