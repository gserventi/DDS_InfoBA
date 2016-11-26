package com.edu.utn.infoba.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "coordenadas")
public class Coordenadas extends Persistible {
	/*
	    *  Coordenadas de ejemplo
	    *  (-34.603075, -58.381653) - Obelisco
	    *  (-34.608333, -58.371944) - Plaza de mayo.
   */

	private Double radioTerrestre = 6371e3; // Radio de la tierra.
	private Double latitud;
	private Double longitud;

	public Coordenadas() {
	}
	
	public Coordenadas(Double latitud, Double longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
	}

	   /*
	    * Calcula la distancia de una coordenada con otra.
	    * OtraCoordenada: juego de coordenadas.
	    * @returns: distancia entre las dos coordenadas (en metros).
	    */
	public Double distanciaCon(Coordenadas OtraCoordenada)
	{
	   Double lat1 = this.latitud;
	   Double lat2 = OtraCoordenada.latitud;
	   Double lon1 = this.longitud;
	   Double lon2 = OtraCoordenada.longitud;
       Double phi1 = Math.toRadians(this.latitud);
	   Double phi2 = Math.toRadians(OtraCoordenada.latitud);
	   Double dphi = Math.toRadians(lat2 - lat1);
	   Double dDelta = Math.toRadians(lon2 - lon1);
       Double a = Math.sin(dphi/2) * Math.sin(dphi/2) + Math.cos(phi1) * Math.cos(phi2) * Math.sin(dDelta/2) * Math.sin(dDelta/2);
	   Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	   Double d = radioTerrestre * c;
       return d;
	}
	
	@Column(name = "latitud")
	public Double getLatitud() {
	   return latitud;
	}

	public void setLatitud(Double latitud) {
	   this.latitud = latitud;
	}
  
	@Column(name = "longitud")
	public Double getLongitud() {
	   return longitud;
	}

	public void setLongitud(Double longitud) {
	   this.longitud = longitud;
	}
}
