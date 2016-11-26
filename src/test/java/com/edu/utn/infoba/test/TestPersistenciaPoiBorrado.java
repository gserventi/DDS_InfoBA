package com.edu.utn.infoba.test;

import static org.junit.Assert.*;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.edu.utn.utils.PoisMySQL;

import com.edu.utn.infoba.*;
import com.edu.utn.infoba.modelo.*;

public class TestPersistenciaPoiBorrado
{
	private FuenteDeDatos fuente;
    private Banco banco;
	private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
	
    @Before
    public void setUp() throws Exception
    {
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		fuente = new FuenteDeDatos(new OrigenDeDatosDummy(),new PoisMySQL(emFactory.createEntityManager()));

        Coordenadas coordenadas  = new Coordenadas(-34.603075, -58.381653); // Obelisco
        banco = new Banco("ITAU", null, coordenadas);
        banco.agregarServicio(new Servicio("Cobros"));

        fuente.agregarPuntoDeInteres(banco);
    }

	@Test
	public void test()
    {
		PuntoDeInteres poi, poi2;
        List<PuntoDeInteres> busquedaPois;

        // Buscar el POI y darlo de baja (lógica).
        busquedaPois = fuente.buscarPuntoDeInteresPorTexto("ITAU");
        poi = busquedaPois.get(0);
		fuente.bajaPuntoDeInteres(poi);

        // Buscar el POI y comprobar que siga existiendo.
        busquedaPois  = fuente.buscarPuntoDeInteresPorTexto("ITAU");
        poi2 = busquedaPois.get(0);
        Boolean poiExists = poi2 != null && poi2.getBaja() == true;
		assertTrue("El POI sigue existiendo", poiExists);
	}

	@After
    public void cleanUp()
    {
        fuente.eliminarPuntoDeInteres(banco);
    }

}
