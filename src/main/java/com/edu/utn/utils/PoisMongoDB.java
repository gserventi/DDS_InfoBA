package com.edu.utn.utils;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.edu.utn.infoba.modelo.Banco;
import com.edu.utn.infoba.modelo.CGP;
import com.edu.utn.infoba.modelo.PuntoDeInteres;
import com.edu.utn.infoba.modelo.mongodb.BancoMongoDB;
import com.edu.utn.infoba.modelo.mongodb.CGPMongoDB;
import com.edu.utn.infoba.modelo.mongodb.PuntoDeInteresMongoDB;

public class PoisMongoDB implements DataBase {
	private Datastore ds;
	
	public PoisMongoDB(Datastore datastore) {
		this.ds = datastore;
	}
	
	@Override
	public List<PuntoDeInteres> buscarPuntoDeInteresPorTexto(String texto) {
		List<PuntoDeInteres> puntosDeInteres = new ArrayList<>();
		Query<PuntoDeInteresMongoDB> query = ds.createQuery(PuntoDeInteresMongoDB.class);
		query.or(
				query.criteria("nombre").containsIgnoreCase(texto),
				query.criteria("descripcion").containsIgnoreCase(texto),
				query.criteria("rubro.nombre").containsIgnoreCase(texto),
				query.criteria("tags").containsIgnoreCase(texto),
				query.criteria("servicios.nombre").containsIgnoreCase(texto)
				);
		List<PuntoDeInteresMongoDB> puntosDeInteresMongoDB = query.asList();
		if ( puntosDeInteresMongoDB != null ) {
			for (PuntoDeInteresMongoDB poiMongoDB : puntosDeInteresMongoDB) {
				puntosDeInteres.add(poiMongoDB.convertirToPoi());
			}
		}
		return puntosDeInteres;
    }
	
	@Override
	public void borradoFisico(List<PuntoDeInteres> puntosDeInteres) {
		for (PuntoDeInteres poi : puntosDeInteres) {
			this.eliminarPuntoDeInteres(poi);
		}
	}
	
	@Override
	public PuntoDeInteres buscarPuntoDeInteresPorNombre(String texto) {
		Query<PuntoDeInteresMongoDB> query = ds.find(PuntoDeInteresMongoDB.class)
				.field("nombre").contains(texto);
		List<PuntoDeInteresMongoDB> lista = query.asList(); 
		if (lista.size() > 0)
			return lista.get(0).convertirToPoi();
		else
			return null;
	}
		
	@Override
	public void persistirPuntoDeInteres(PuntoDeInteres puntoDeInteres) {
		PuntoDeInteresMongoDB poiMongoDB = new PuntoDeInteresMongoDB(puntoDeInteres);
		ds.save(poiMongoDB);
	}
	
	public void persistirPuntoDeInteres(Banco banco) {
		BancoMongoDB bancoMongoDB = new BancoMongoDB(banco);
		ds.save(bancoMongoDB);
	}
	
	public void persistirPuntoDeInteres(CGP cgp) {
		CGPMongoDB cgpMongoDB = new CGPMongoDB(cgp);
		ds.save(cgpMongoDB);
	}
	
	@Override
	public void eliminarPuntoDeInteres(PuntoDeInteres puntoDeInteres) {
		Query<PuntoDeInteresMongoDB> query = ds.find(PuntoDeInteresMongoDB.class)
				.field("nombre").equal(puntoDeInteres.getNombre());
		ds.delete(query);
	}
	
	@Override
    public void bajaPuntoDeInteres(PuntoDeInteres puntoDeInteres) {
		puntoDeInteres.setBaja(true);
		this.modificarPuntoDeInteres(puntoDeInteres);
	}
	
	@Override
	public void modificarPuntoDeInteres(PuntoDeInteres puntoDeInteres) {
		this.eliminarPuntoDeInteres(puntoDeInteres);
		this.persistirPuntoDeInteres(puntoDeInteres);
	}
}
