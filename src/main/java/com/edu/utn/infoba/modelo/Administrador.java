package com.edu.utn.infoba.modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="Administrador")
public class Administrador extends Usuario
{
    //private FuenteDeDatos repositorio;
    
    public Administrador() {}

    public Administrador(String nombre, String password) {
		super(nombre, password);
	}

    /*public void crear(String nombre, Direccion direccion, Coordenadas coordenadas, Rubro rubro) {
        Comercio Comercio  = new Comercio (nombre,direccion,coordenadas,rubro);
        repositorio.agregarPuntoDeInteres(Comercio);
    }
    
    public void crear(String nombre, Direccion direccion, Coordenadas coordenadas) {
        ParadaColectivos Parada_Colectivos  = new ParadaColectivos ( nombre,  direccion,  coordenadas);
        repositorio.agregarPuntoDeInteres(Parada_Colectivos);
    }
    
    public void delete(String texto) {
        Collection<PuntoDeInteres> puntosDeInteres;
        puntosDeInteres = repositorio.buscarPuntoDeInteresPorTexto(texto);
        for(PuntoDeInteres poi : puntosDeInteres) {
            repositorio.eliminarPuntoDeInteres(poi);
            poi=null;
        }
    }

    public void modificar(Comercio poi ,String nombre, Direccion direccion, Coordenadas coordenadas, Rubro rubro) {
        poi.setNombre(nombre);
        poi.setDireccion(direccion);
        poi.setCoordenadas(coordenadas);
        poi.setRubro(rubro);
    }
    
    public void modificar(ParadaColectivos poi ,String nombre, Direccion direccion, Coordenadas coordenadas) {
        poi.setNombre(nombre);
        poi.setDireccion(direccion);
        poi.setCoordenadas(coordenadas);
    }

    public Collection<PuntoDeInteres> consulta(String texto) {
        Collection<PuntoDeInteres> puntosDeInteres;
        puntosDeInteres = repositorio.buscarPuntoDeInteresPorTexto(texto);
        return puntosDeInteres;
    }

    public void setFuenteDeDatos(FuenteDeDatos fuenteDeDatos) {
        this.repositorio = fuenteDeDatos;
    }*/

}