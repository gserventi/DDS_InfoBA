package com.edu.utn.infoba.modelo;

import java.util.Date;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@NamedQuery(name = "buscarBancoPorServicio", query = "SELECT p FROM PuntoDeInteres p LEFT OUTER JOIN p.servicios s WHERE s.nombre LIKE :texto")
public class Banco extends PuntoDeInteres
{
	private String sucursal;
	private String gerente;
	
	public Banco() {}
	
    public Banco(String nombre, Direccion direccion, Coordenadas coordenadas) {
        super(nombre, direccion, coordenadas);
    }
    
    public Banco(String nombre,  Direccion direccion, Coordenadas coordenadas, String sucursal, String gerente) {
    	super(nombre, direccion, coordenadas);
		this.sucursal = sucursal;
		this.gerente = gerente;
    }
    
    @Column(name = "sucursal")
    public String getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    @Column(name = "gerente")
    public String getGerente() {
        return this.gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

	@SuppressWarnings("deprecation")
	public boolean disponibilidad(Date tiempo , Servicio ser) {
		 if(tiempo.getHours() <= 15 && tiempo.getHours() >= 10) {
	    	if( ser != null)
	    		return ser.estaDisponible(tiempo);
	    	return true;
	    }
		 return false;
	}

}
