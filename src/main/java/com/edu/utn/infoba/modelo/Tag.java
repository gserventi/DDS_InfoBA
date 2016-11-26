package com.edu.utn.infoba.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tag")
public class Tag extends Persistible
{
	@ManyToOne
	@JoinColumn(name = "poi_id", referencedColumnName = "id")
	private PuntoDeInteres puntoDeInteres;
	
	@Column(name = "tag")
	public String getTag()
    {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	private String tag;
	
	public Tag() {}
	
	public Tag(String texto)
    {
		tag = texto;
	}
	
	public PuntoDeInteres getPuntoDeInteres()
    {
		return puntoDeInteres;
	}
	
	public void setPuntoDeInteres(PuntoDeInteres puntoDeInteres)
    {
		this.puntoDeInteres = puntoDeInteres;
	}
}
