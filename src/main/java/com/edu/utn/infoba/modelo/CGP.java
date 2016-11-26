package com.edu.utn.infoba.modelo;

import java.util.List;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@NamedQuery(name = "buscarCGPPorServicio", query = "SELECT p FROM PuntoDeInteres p LEFT OUTER JOIN p.servicios s WHERE s.nombre LIKE :texto")
public class CGP extends PuntoDeInteres
{
    private int comuna;
    private String telefono;
    private String director;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "poi_id", referencedColumnName = "id")
    private List<Zona> zonas;
    
    public CGP() {}
		
	public CGP(String nombre, Direccion direccion, Coordenadas coordenadas) {
        super(nombre, direccion, coordenadas);
    }
	
	public CGP(int comuna, String director, String domicilio, String telefono) {
		super();
		String nombre = "CGP " + Integer.toString(comuna);
		String[] separar = domicilio.split("");
		String calle = null;
		String numero = null;
		if (separar.length >= 2) {
			calle = separar[0];
			numero = separar[1];
		}
		Direccion direccion = new Direccion (calle,numero,null,null,null,null,null,null);
		this.setNombre(nombre);
		this.setDireccion(direccion);
		this.director = director;
		this.telefono = telefono;
	}
	

	public boolean disponibilidad(Date tiempo , Servicio servicio) {
    	if( servicio != null)
    		return servicio.estaDisponible(tiempo);
    	else
    	{
    		//Si esta vacio recorre todos los servicios a ver si alguno esta disponible en ese horario
    		return this.servicios.stream().anyMatch(serv -> serv.estaDisponible(tiempo));
    	}
    }

    @Column(name = "comuna")
    public int getComuna() {
        return comuna;
    }

    public void setComuna(int comuna) {
        this.comuna = comuna;
    }

    @Column(name = "telefono")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Column(name = "director")
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
    
    public List<Zona> getZona() {
		return this.zonas;
	}

	public void setZona(List<Zona> zona) {
		this.zonas = zona;
	}
	
	public void agregarZona(Zona zona) {
 		if (zonas == null) {
 			zonas = new ArrayList<>();
 		}
 		if (!zonas.contains(zona))
 			zonas.add(zona);
 	}
 	
 	public void removerZona(Zona zona) {
 		if(zonas.contains(zona))
 			zonas.remove(zona);
 	}
 	
 	public void limpiarZona() {
 		zonas.clear();
 	}

	public List<Zona> getZonas() {
		return zonas;
	}

	public void setZonas(List<Zona> zonas) {
		this.zonas = zonas;
	}
}
