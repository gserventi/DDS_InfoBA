package com.edu.utn.infoba.modelo;

import com.edu.utn.utils.Configuracion;

import java.util.Date;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
public class ParadaColectivos extends PuntoDeInteres
{

    public ParadaColectivos() {}
    
    public ParadaColectivos(String nombre, Direccion direccion, Coordenadas coordenadas) {
        super(nombre, direccion, coordenadas);
        Configuracion config = new Configuracion();
        this.distanciaCercania = Double.parseDouble(config.obtenerPropiedad("DistanciaDefaultParadaColectivos"));
    }

    public Boolean cercaDe(PuntoDeInteres otroPunto) {
        return distanciaMenorA(distanciaCercania, otroPunto);
    }
    
    @Column(name = "distanciaCercania")
    public Double getDistanciaCercania() {
    	return this.distanciaCercania;
    }
    
    public void setDistanciaCercania(Double distanciaCercania) {
    	this.distanciaCercania = distanciaCercania;
    }

    public boolean disponibilidad(Date tiempo , Servicio servicio) {
    	return true;
    }
}