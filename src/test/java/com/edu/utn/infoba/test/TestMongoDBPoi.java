package com.edu.utn.infoba.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mongodb.morphia.Datastore;

import com.edu.utn.infoba.modelo.*;
import com.edu.utn.utils.MongoDBConnection;
import com.edu.utn.utils.PoisMongoDB;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMongoDBPoi {
	private Datastore datastore;
	private PoisMongoDB poisMongoDB;
	
	@Before
    public void setUp() throws Exception {
    	datastore = MongoDBConnection.getInstance().getDatastore();
    	poisMongoDB = new PoisMongoDB(datastore);
    }
	
	@Test
    public void agregarDocumento()  {
		Direccion dir1 = new Direccion("Azul","345", " ", " ", " ", new Localidad("") ,  new Provincia("Buenos Aires"),new Pais("Argentina"));
		Banco banco1 = new Banco("ITAU Centro", dir1, new Coordenadas(22.0, 15.0));
		banco1.setRubro(new Rubro("Bancario",(double) 10));
		banco1.setGerente("Lopez");
		banco1.setSucursal("Centro");
		banco1.agregarServicio(new Servicio("Cobros"));
		poisMongoDB.persistirPuntoDeInteres(banco1);
		assertNotNull("No se persistio el banco ITAU Centro", poisMongoDB.buscarPuntoDeInteresPorNombre("ITAU Centro"));
		
		Direccion dir2 = new Direccion("Laprida","345", " ", " ", " ", new Localidad("") ,  new Provincia("Buenos Aires"),new Pais("Argentina"));
		Banco banco2 = new Banco("ITAU Sur", dir2, new Coordenadas(22.0, 15.0));
		poisMongoDB.persistirPuntoDeInteres(banco2);
		assertNotNull("No se persistio el banco ITAU Sur", poisMongoDB.buscarPuntoDeInteresPorNombre("ITAU Sur"));
		
		Direccion dir3 = new Direccion("Azul","345", " ", " ", " ", new Localidad("") ,  new Provincia("Buenos Aires"),new Pais("Argentina"));
		CGP cgp = new CGP("Comuna10", dir3, new Coordenadas(2.0,13.0));
		cgp.setDescripcion("Floresta");
		cgp.agregarServicio(new Servicio("Atencion ciudadana"));
		cgp.agregarServicio(new Servicio("Pagos"));
		cgp.agregarServicio(new Servicio("Tramites"));
		cgp.agregarTag(new Tag("tagCGP"));
		poisMongoDB.persistirPuntoDeInteres(cgp);
		assertNotNull("No se persistio el CGP Comuna10", poisMongoDB.buscarPuntoDeInteresPorNombre("Comuna10"));
	}
	
	@Test
	public void eliminarDocumento() {
		Direccion dir2 = new Direccion("Laprida","345", " ", " ", " ", new Localidad("") ,  new Provincia("Buenos Aires"),new Pais("Argentina"));
		Banco banco2 = new Banco("ITAU Sur", dir2, new Coordenadas(22.0, 15.0));
		poisMongoDB.eliminarPuntoDeInteres(banco2);
		assertNull("Se encontro el banco ITAU Sur que deberia estar borrado", poisMongoDB.buscarPuntoDeInteresPorNombre("ITAU Sur"));
	}
	
	@Test
	public void modificarDocumento() {
		Direccion dir1 = new Direccion("Rivadavia","345", " ", " ", " ", new Localidad("") ,  new Provincia("Buenos Aires"),new Pais("Argentina"));
		Banco banco1 = new Banco("ITAU Centro", dir1, new Coordenadas(22.0, 15.0));
		banco1.setRubro(new Rubro("Bancario",(double) 10));
		banco1.agregarTag(new Tag("tagModificado"));
		banco1.setGerente("Lopez");
		banco1.setSucursal("Centro");
		banco1.agregarServicio(new Servicio("Cobros"));
		poisMongoDB.modificarPuntoDeInteres(banco1);
		assertTrue("No se modifico el tag de ITAU Centro", poisMongoDB.buscarPuntoDeInteresPorTexto("tagModificado").size() > 0);
	}
	
	@Test
	public void modificarDocumentoBaja() {
		Direccion dir1 = new Direccion("Rivadavia","345", " ", " ", " ", new Localidad("") ,  new Provincia("Buenos Aires"),new Pais("Argentina"));
		Banco banco1 = new Banco("ITAU Centro", dir1, new Coordenadas(22.0, 15.0));
		banco1.setRubro(new Rubro("Bancario",(double) 10));
		banco1.agregarTag(new Tag("tagModificado"));
		banco1.setGerente("Perez");
		banco1.setSucursal("Almagro");
		banco1.agregarServicio(new Servicio("Cobros"));
		poisMongoDB.bajaPuntoDeInteres(banco1);
		assertTrue("No se realizo la baja logica de ITAU Centro", poisMongoDB.buscarPuntoDeInteresPorNombre("ITAU Centro").getBaja());
	}
	
	@Test
	public void recuperarPorNombre() {
		assertNotNull("No se encontro el punto existente con nombre Comuna", poisMongoDB.buscarPuntoDeInteresPorNombre("Comuna"));
		assertNotNull("No se encontro el punto existente con nombre ITAU", poisMongoDB.buscarPuntoDeInteresPorNombre("ITAU"));
		assertNull("Se encontro el punto no existente con nombre XXXXX", poisMongoDB.buscarPuntoDeInteresPorNombre("XXXXX"));
	}
	
	@Test
	public void recuperarPorTextoEnCampoDescripcion() {
		assertNotNull("No se encontro el punto existente con nombre ITAU", poisMongoDB.buscarPuntoDeInteresPorTexto("Floresta"));
	}
	
	@Test
	public void recuperarPorTextoEnCampoNombre() {
		assertNotNull("No se encontro el punto existente con nombre ITAU", poisMongoDB.buscarPuntoDeInteresPorTexto("Comuna"));
	}
	
	@Test
	public void recuperarPorTextoEnCampoRubro() {
		assertNotNull("No se encontro el punto existente con nombre ITAU", poisMongoDB.buscarPuntoDeInteresPorTexto("Bancario"));
	}
	
	@Test
	public void recuperarPorTextoEnCampoServicio() {
		assertNotNull("No se encontro el punto existente con nombre ITAU", poisMongoDB.buscarPuntoDeInteresPorTexto("Tramites"));
	}
	
	@Test
	public void recuperarPorTextoEnCampoTag() {
		assertNotNull("No se encontro el punto existente con nombre ITAU", poisMongoDB.buscarPuntoDeInteresPorTexto("tag"));
	}
}
