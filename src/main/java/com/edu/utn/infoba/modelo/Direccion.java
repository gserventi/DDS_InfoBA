package com.edu.utn.infoba.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "direccion")
public class Direccion extends Persistible {
	
	private String calle;
	private String numero;
	private String piso;
	private String departamento;
	private String codigoPostal;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Localidad localidad;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Provincia provincia;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Pais pais;
    
    public Direccion() {
    }
    
    public Direccion(String calle, String numero, String piso, String departamento, String codigoPostal, Localidad localidad, Provincia provincia, Pais pais) {
    	this.calle = calle;
    	this.numero = numero;
    	this.piso = piso;
    	this.departamento = departamento;
    	this.codigoPostal = codigoPostal;
    	this.localidad = localidad;
    	this.provincia = provincia;
    	this.pais = pais;
    }
    
	@Column(name = "calle")
	public String getCalle() {
		return calle;
	}
	
	public void setCalle(String calle) {
		this.calle = calle;
	}
	
	@Column(name = "numero")
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@Column(name = "piso")
	public String getPiso() {
		return piso;
	}
	
	public void setPiso(String piso) {
		this.piso = piso;
	}
	
	@Column(name = "departamento")
	public String getDepartamento() {
		return departamento;
	}
	
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	@Column(name = "codigo_postal")
	public String getCodigoPostal() {
		return codigoPostal;
	}
	
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
}
