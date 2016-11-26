package com.edu.utn.infoba.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.edu.utn.infoba.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.edu.utn.utils.MongoDBConnection;
import com.edu.utn.utils.PoisMySQL;
import com.edu.utn.utils.ServicioRestBaja;
import com.edu.utn.infoba.modelo.*;
import com.edu.utn.infoba.repositorios.RepositorioBusqueda;
import com.edu.utn.infoba.repositorios.RepositorioPois;

public class TestProcesoBajas
{

	private FuenteDeDatos fuente;
	private Banco banco, banco2;
	private CGP cgp;
	private Comercio comercio;
	private ParadaColectivos colectivo;
	private RepositorioPois repo;
	private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
	
	@Before
	public void setUp() throws Exception
	{
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = emFactory.createEntityManager();
		fuente = new FuenteDeDatos(new OrigenDeDatosDummy(),new PoisMySQL(entityManager));
		Datastore ds = MongoDBConnection.getInstance().getDatastore();
		RepositorioBusqueda repoBusqueda = new RepositorioBusqueda(ds,new MockMailSender());
		repo = new RepositorioPois(repoBusqueda, entityManager);

        // Banco
		banco = new Banco("ITAU Baja", null, new Coordenadas(1.0,10.0));
		banco.agregarServicio(new Servicio("Cobros"));
		banco.agregarServicio(new Servicio("Pagos"));
		banco.agregarServicio(new Servicio("Atencion al cliente"));
		banco.agregarTag(new Tag("tag1"));

        // Banco 2
        banco2 = new Banco("Galicia", null, new Coordenadas(1.0,15.0));
        banco2.agregarServicio(new Servicio("Cobros"));
        banco2.agregarServicio(new Servicio("Pagos"));
        banco2.agregarServicio(new Servicio("Atencion al cliente"));
        banco2.agregarTag(new Tag("tag14"));

        // CGP
		cgp = new CGP("Comuna10 Baja",null,new Coordenadas(2.0,13.0));
		cgp.agregarServicio(new Servicio("Atencion ciudadana"));
		cgp.agregarServicio(new Servicio("Pagos"));
		cgp.agregarServicio(new Servicio("Tramites"));
		cgp.agregarTag(new Tag("tag2"));

        // Comercio
		comercio = new Comercio("LosMuebles",null,new Coordenadas(15.0, 55.0), new Rubro("Muebleria", 200.0));
		comercio.agregarTag(new Tag("tag3"));

        // Colectivo
		colectivo = new ParadaColectivos("7y101",null,new Coordenadas(2.0, 13.0));
		colectivo.agregarTag(new Tag("UTN"));

		fuente.agregarPuntoDeInteres(banco);
        fuente.agregarPuntoDeInteres(banco2);
		fuente.agregarPuntoDeInteres(cgp);
		fuente.agregarPuntoDeInteres(comercio);
		fuente.agregarPuntoDeInteres(colectivo);

		repo.agregarFuenteDeDatos(fuente);
		repo.setFuenteDeDatosLocal(fuente);

	}

	@Test
	public void testBajas1()
	{
		ServicioRestBaja.setParametro("ServicioBajasTest1");

		try
		{
			repo.ProcesoBajas();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail("Fallo de IO");
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			fail("Fallo de parseo");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Fallo en la busqueda");
		}

        List<PuntoDeInteres> Comuna10 =  fuente.buscarPuntoDeInteresPorTexto("Comuna10");
        List<PuntoDeInteres> ITAU =  fuente.buscarPuntoDeInteresPorTexto("ITAU");

		assertTrue("Falló el borrado de comuna10", Comuna10.contains(cgp));
		assertFalse("Falló el borrado de ITAU", ITAU.contains(banco));

	}

	/*
	@Test
	public void testBajas2()
	{
		ServicioRestBaja.setParametro("ServicioBajasTest2");

		try
		{
		   repo.ProcesoBajas();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail("Fallo de IO");
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			fail("Fallo de parseo");
		}
		catch (Exception e)
		{
            List<PuntoDeInteres> Comuna10 = fuente.buscarPuntoDeInteresPorTexto("Comuna10");
            List<PuntoDeInteres> ITAU = fuente.buscarPuntoDeInteresPorTexto("ITAU");

			assertTrue("Falló el borrado de comuna10", Comuna10.contains(cgp));
			assertTrue("Falló el borrado de ITAU", ITAU.contains(banco));
		}

		//fail("Deberia haber fallado la busqueda")
	}*/

	@Test
	public void testBajas3()
	{
		ServicioRestBaja.setParametro("ServicioBajasTest3");

		try
        {
			repo.ProcesoBajas();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail("Fallo de IO");
		}
		catch (ParseException e)
        {
			e.printStackTrace();
			fail("Fallo de parseo");
		}
		catch (Exception e)
        {
			e.printStackTrace();
			fail("Fallo en la busqueda");
		}

        List<PuntoDeInteres> banco = fuente.buscarPuntoDeInteresPorTexto("Galicia");
        assertTrue("Fallo el borrado del banco.", banco.size() == 0);

	}

	@After
    public void cleanUp()
    {
        if(fuente.buscarPuntoDeInteresPorNombre(banco.getNombre()) != null)
            fuente.eliminarPuntoDeInteres(banco);

        if(fuente.buscarPuntoDeInteresPorNombre(banco2.getNombre()) != null)
            fuente.eliminarPuntoDeInteres(banco2);

        if(fuente.buscarPuntoDeInteresPorNombre(cgp.getNombre()) != null)
            fuente.eliminarPuntoDeInteres(cgp);

        if(fuente.buscarPuntoDeInteresPorNombre(comercio.getNombre()) != null)
            fuente.eliminarPuntoDeInteres(comercio);

        if(fuente.buscarPuntoDeInteresPorNombre(colectivo.getNombre()) != null)
            fuente.eliminarPuntoDeInteres(colectivo);
    }

}
