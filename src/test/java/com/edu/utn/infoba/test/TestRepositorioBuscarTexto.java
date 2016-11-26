package com.edu.utn.infoba.test;

import static org.junit.Assert.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.edu.utn.infoba.*;
import com.edu.utn.infoba.modelo.*;
import com.edu.utn.utils.PoisMySQL;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestRepositorioBuscarTexto
{
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

		fuente.agregarPuntoDeInteres(banco);
		fuente.agregarPuntoDeInteres(cgp);
		fuente.agregarPuntoDeInteres(comercio);
		fuente.agregarPuntoDeInteres(colectivo);
		
	}

	@Test
	public void testBanco()
	{
        PuntoDeInteres poiNombre = fuente.buscarPuntoDeInteresPorNombre("ITAU");

        List<PuntoDeInteres> cobros = fuente.buscarPuntoDeInteresPorTexto("Cobros");
        List<PuntoDeInteres> pagos = fuente.buscarPuntoDeInteresPorTexto("Pagos");
        List<PuntoDeInteres> atencion = fuente.buscarPuntoDeInteresPorTexto("Atencion");
        List<PuntoDeInteres> tagBanco = fuente.buscarPuntoDeInteresPorTexto("tagBanco");

		assertTrue("Falló busqueda de banco por nombre", poiNombre != null);
		assertTrue("Falló busqueda de banco por servicio cobros", cobros.contains(banco));
        assertTrue("Falló busqueda de banco por servicio pagos", pagos.contains(banco));
		assertTrue("Falló busqueda de banco por servicio atención", atencion.contains(banco));
        assertTrue("Falló busqueda de banco por tags", tagBanco.contains(banco));

	}

	@Test
	public void testCGP()
	{
        PuntoDeInteres poiNombre = fuente.buscarPuntoDeInteresPorNombre("Comuna10");
        List<PuntoDeInteres> pagos = fuente.buscarPuntoDeInteresPorTexto("Pagos");
		List<PuntoDeInteres> tramites = fuente.buscarPuntoDeInteresPorTexto("Tramites");
        List<PuntoDeInteres> atencion = fuente.buscarPuntoDeInteresPorTexto("ciudadana");
		List<PuntoDeInteres> tagCGP = fuente.buscarPuntoDeInteresPorTexto("tagCGP");

		assertTrue("Falló busqueda de CGP por nombre", poiNombre!= null);
		assertTrue("Falló busqueda de CGP por tag", tagCGP.contains(cgp));
		assertTrue("Falló busqueda de CGP por servicio pagos.",pagos.contains(cgp));
		assertTrue("Falló busqueda de CGP por servicio trámites.",tramites.contains(cgp));
		assertTrue("Falló busqueda de CGP por servicio Atención Ciudadana.", atencion.contains(cgp));

	}
	
	@Test
	public void testComercio()
	{
        PuntoDeInteres poiNombre = fuente.buscarPuntoDeInteresPorNombre("Muebles");
        List<PuntoDeInteres> tagComercio = fuente.buscarPuntoDeInteresPorTexto("tagComercio");
        List<PuntoDeInteres> muebleria = fuente.buscarPuntoDeInteresPorTexto("Muebleria");

		assertTrue("Falló busqueda de comercio por nombre", poiNombre != null);
		assertTrue("Falló busqueda de comercio por tags", tagComercio.contains(comercio));
		assertTrue("Falló busqueda de comercio por rubro", muebleria.contains(comercio));
	}
	
	@Test
	public void testColectivo()
	{
        PuntoDeInteres poiNombre = fuente.buscarPuntoDeInteresPorNombre("7y101");
        List<PuntoDeInteres> utn = fuente.buscarPuntoDeInteresPorTexto("UTN");

		assertTrue("Falló busqueda de colectivo por nombre", poiNombre != null);
		assertTrue("Falló busqueda de colectivo por tags", utn.contains(colectivo));
	}
	
}
