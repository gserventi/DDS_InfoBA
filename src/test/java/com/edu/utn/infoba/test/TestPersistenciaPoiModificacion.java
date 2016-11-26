package com.edu.utn.infoba.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.edu.utn.utils.PoisMySQL;

import com.edu.utn.infoba.*;
import com.edu.utn.infoba.modelo.*;

public class TestPersistenciaPoiModificacion {

	private FuenteDeDatos fuente;
    private Banco banco;
    private Banco banco2;
    private Comercio comercio;
    private ParadaColectivos colectivo;
	private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
    private Coordenadas coordenadas3 = new Coordenadas(22.0, 15.0);
	
    @Before
    public void setUp() throws Exception
    {
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		fuente = new FuenteDeDatos(new OrigenDeDatosDummy(),new PoisMySQL(emFactory.createEntityManager()));

        Coordenadas coordenadas  = new Coordenadas(-34.603075, -58.381653); // Obelisco
        Coordenadas coordenadas2 = new Coordenadas(-34.608333, -58.371944); // Plaza de mayo
        Rubro rubro = new Rubro("Muebleria", 200.0);

        banco = new Banco("ITAU", null, coordenadas);
        banco2 = new Banco("CGP1", null, coordenadas2);
        comercio = new Comercio("LosMuebles", null, coordenadas3, rubro);
        colectivo = new ParadaColectivos("7y101",null, coordenadas3);

        fuente.agregarPuntoDeInteres(banco);
        fuente.agregarPuntoDeInteres(banco2);
        fuente.agregarPuntoDeInteres(comercio);
        fuente.agregarPuntoDeInteres(colectivo);
    }

	@Test
	public void test() {
		PuntoDeInteres poi;
		Iterator<PuntoDeInteres> iterador;
		List<PuntoDeInteres> pois;
		pois = fuente.buscarPuntoDeInteresPorTexto("ITAU");
		iterador = pois.iterator();
		poi = iterador.next();
		poi.setCoordenadas(coordenadas3);
		fuente.modificarPuntoDeInteres(poi);
		pois = fuente.buscarPuntoDeInteresPorTexto("ITAU");
		iterador = pois.iterator();
		poi = iterador.next();

		assertTrue("Las coordenadas no cinciden despues de persistir",poi.getCoordenadas()==coordenadas3);
	}

	@After
    public void cleanUp()
    {
        //fuente.eliminarPuntoDeInteres(banco);
        //fuente.eliminarPuntoDeInteres(banco2);
        //fuente.eliminarPuntoDeInteres(comercio);
        //fuente.eliminarPuntoDeInteres(colectivo);
    }

}
