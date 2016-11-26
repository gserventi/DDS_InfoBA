package com.edu.utn.infoba.test;

import com.edu.utn.infoba.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.edu.utn.infoba.modelo.*;
import com.edu.utn.infoba.repositorios.RepositorioBusqueda;
import com.edu.utn.infoba.repositorios.RepositorioPois;
import com.edu.utn.utils.MongoDBConnection;
import com.edu.utn.utils.PoisMySQL;

public class TestReporte
{
	
	private RepositorioPois repositorio; 
	private FuenteDeDatos fuente;
	private Banco banco;
	private CGP cgp;
	private Comercio comercio;
	private ParadaColectivos colectivo;
    private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
	
	@Before
	public void setUp() throws Exception
	{
	   	emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		fuente = new FuenteDeDatos(new OrigenDeDatosDummy(),new PoisMySQL(emFactory.createEntityManager()));

		Datastore ds = MongoDBConnection.getInstance().getDatastore();
		RepositorioBusqueda repoBusqueda = new RepositorioBusqueda(ds,new MockMailSender());
		repositorio = new RepositorioPois(repoBusqueda,emFactory.createEntityManager());
		repositorio.agregarFuenteDeDatos(fuente);
		
		banco = new Banco("ITAU", null, new Coordenadas(1.0,10.0));
		banco.agregarServicio(new Servicio("Cobros"));
		banco.agregarServicio(new Servicio("Pagos"));
		banco.agregarServicio(new Servicio("Atencion al cliente"));
		banco.agregarTag(new Tag("tag1"));

		cgp = new CGP("Comuna10",null,new Coordenadas(2.0,13.0));
		cgp.agregarServicio(new Servicio("Atencion ciudadana"));
		cgp.agregarServicio(new Servicio("Pagos"));
		cgp.agregarServicio(new Servicio("Tramites"));
		cgp.agregarTag(new Tag("tag2"));

		comercio = new Comercio("LosMuebles",null,new Coordenadas(15.0, 55.0), new Rubro("Muebleria", 200.0));
		comercio.agregarTag(new Tag("tag3"));

		colectivo = new ParadaColectivos("7y101",null,new Coordenadas(2.0, 13.0));
		colectivo.agregarTag(new Tag("UTN"));

		fuente.agregarPuntoDeInteres(banco);
		fuente.agregarPuntoDeInteres(cgp);
		fuente.agregarPuntoDeInteres(comercio);
		fuente.agregarPuntoDeInteres(colectivo);
		
		repositorio.buscarPuntoDeInteresPorTexto("ITAU","testuser");
		repositorio.buscarPuntoDeInteresPorTexto("tag1","testuser");
		repositorio.buscarPuntoDeInteresPorTexto("Cobros","testuser");
		repositorio.buscarPuntoDeInteresPorTexto("Atencion","testuser");
		repositorio.buscarPuntoDeInteresPorTexto("Comuna","testuser");
		repositorio.buscarPuntoDeInteresPorTexto("tag2","testuser").contains(cgp);
		repositorio.buscarPuntoDeInteresPorTexto("Pagos","testuser").contains(cgp);
		repositorio.buscarPuntoDeInteresPorTexto("ciudadana","testuser").contains(cgp);
		repositorio.buscarPuntoDeInteresPorTexto("Muebles","testuser").contains(comercio);
		repositorio.buscarPuntoDeInteresPorTexto("tag3","testuser").contains(comercio);
		repositorio.buscarPuntoDeInteresPorTexto("Muebleria","testuser").contains(comercio);
		repositorio.buscarPuntoDeInteresPorTexto("101","testuser").contains(colectivo);
		repositorio.buscarPuntoDeInteresPorTexto("UTN","testuser").contains(colectivo);
	
	}

	@Test
	public void testReportePorFecha()
	{
		repositorio.reportePorFecha();
	}
	
	@Test
	public void testReporteUsuario()
	{
		repositorio.reportePorUsuario();
	}

}
