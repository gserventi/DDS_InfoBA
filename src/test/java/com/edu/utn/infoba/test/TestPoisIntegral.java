package com.edu.utn.infoba.test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.edu.utn.infoba.*;
import com.edu.utn.infoba.modelo.*;
import com.edu.utn.infoba.repositorios.RepositorioBusqueda;
import com.edu.utn.infoba.repositorios.RepositorioPois;
import com.edu.utn.utils.MongoDBConnection;
import com.edu.utn.utils.PoisMongoDB;
import com.edu.utn.utils.PoisMySQL;

public class TestPoisIntegral {

	private FuenteDeDatos fuenteMySql;
	private Banco banco;
	private CGP cgp;
	private Comercio comercio;
	private ParadaColectivos colectivo;
    private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
	private RepositorioPois repositorioPois;
	private FuenteDeDatos fuenteBancosMongo;
	private FuenteDeDatos fuenteCentrosMongo;
	
	@Before
	public void setUp() throws Exception {
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emFactory.createEntityManager();
		MailSender mailSender = new MockMailSender();
		Datastore ds = MongoDBConnection.getInstance().getDatastore();
		repositorioPois = new RepositorioPois(new RepositorioBusqueda(ds, mailSender),em);
		fuenteMySql = new FuenteDeDatos(new OrigenDeDatosDummy(),new PoisMySQL(emFactory.createEntityManager()));
		repositorioPois.agregarFuenteDeDatos(fuenteMySql);
		fuenteBancosMongo = new FuenteDeDatos(new ApiRestBancos(),new PoisMongoDB(MongoDBConnection.getInstance().getDatastore()));
		repositorioPois.agregarFuenteDeDatos(fuenteBancosMongo);
		fuenteCentrosMongo = new FuenteDeDatos(new ApiRestCgp(),new PoisMongoDB(MongoDBConnection.getInstance().getDatastore()));
		repositorioPois.agregarFuenteDeDatos(fuenteCentrosMongo);
		
		Direccion dir = new Direccion("Alverdi","345", " ", " ", " ", new Localidad("") ,  new Provincia("Buenos Aires"),new Pais("Argentina")); 
		// Banco
		banco = new Banco("ITAU", dir, new Coordenadas(1.0,10.0));

		banco.agregarServicio(new Servicio("Cobros"));
		banco.agregarServicio(new Servicio("Pagos"));
		banco.agregarServicio(new Servicio("Atencion al cliente"));
		banco.agregarTag(new Tag("tagBanco"));

		// CGP
		Direccion dir2 = new Direccion("Azul","345", " ", " ", " ", new Localidad("") ,  new Provincia("Buenos Aires"),new Pais("Argentina")); 
		cgp = new CGP("Comuna10",dir2,new Coordenadas(2.0,13.0));
		cgp.agregarServicio(new Servicio("Atencion ciudadana"));
		cgp.agregarServicio(new Servicio("Pagos"));
		cgp.agregarServicio(new Servicio("Tramites"));
		cgp.agregarTag(new Tag("tagCGP"));

		// Comercio
		Direccion dir3 = new Direccion("Avellaneda","34335", " ", " ", " ", new Localidad("") ,  new Provincia("Buenos Aires"),new Pais("Argentina")); 
		comercio = new Comercio("LosMuebles",dir3,new Coordenadas(15.0, 55.0), new Rubro("Muebleria", 200.0));
		comercio.agregarTag(new Tag("tagComercio"));

		// Colectivo.
		colectivo = new ParadaColectivos("7y101",null,new Coordenadas(2.0, 13.0));
		colectivo.agregarTag(new Tag("UTN"));

		fuenteMySql.agregarPuntoDeInteres(banco);
		fuenteMySql.agregarPuntoDeInteres(cgp);
		fuenteMySql.agregarPuntoDeInteres(comercio);
		fuenteMySql.agregarPuntoDeInteres(colectivo);
		
	}

	@Test
	public void test1() {
		assertTrue("No se encotro UTN",repositorioPois.buscarPuntoDeInteresPorTexto("UTN","testuser").size() > 0);
	}
	
	@Test
	public void test2() {
		assertTrue("No se encotro Santander",repositorioPois.buscarPuntoDeInteresPorTexto("santander","testuser").size() > 0);

	}
	
	@Test
	public void test3() {
		assertTrue("No se encotro cobros",repositorioPois.buscarPuntoDeInteresPorTexto("cobros","testuser").size() > 0);
	}
	
	@Test
	public void test4() {
		assertFalse("Se encuentra cualquier cosa",repositorioPois.buscarPuntoDeInteresPorTexto("NoSeDeberiaEncontrarNada","testuser").size() > 0);
	}

}
