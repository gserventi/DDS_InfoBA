package com.edu.utn.infoba.test;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.edu.utn.infoba.modelo.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.edu.utn.infoba.*;
import com.edu.utn.utils.PoisMySQL;

public class TestActualizaComercio
{
	private FuenteDeDatos repositorio;
	private Comercio comercio;
	private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
	
	@Before
	public void setUp() throws Exception
    {
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		repositorio = new FuenteDeDatos(new OrigenDeDatosDummy(),new PoisMySQL(emFactory.createEntityManager()));

		comercio = new Comercio("LosMuebles",null,new Coordenadas(15.0, 55.0),new Rubro("Muebleria", 200.0));
		comercio.agregarTag(new Tag("tag3"));
		repositorio.agregarPuntoDeInteres(comercio);
	}
	
	@Test
	public void testActualiza() throws IOException
    {
		LogEstadoRepositorio("REPOSITORIO ANTES DE PROCESO DE ACTUALIZAR LOCALES");
		repositorio.actualizarLocales();
		LogEstadoRepositorio("REPOSITORIO DESPUES DE PROCESO DE ACTUALIZAR LOCALES");
	}

    @After
    public void cleanUp() throws IOException
    {
        repositorio.eliminarPuntoDeInteres(comercio);
    }

    private void LogEstadoRepositorio(String mensaje)
	{
		System.out.println(mensaje);

		for(PuntoDeInteres poi : repositorio.buscarPuntoDeInteresPorTexto(""))
		{
			System.out.println("POI en Repositorio: " + poi.getNombre());
			for (String tag : poi.getTags()) {
				System.out.println("Tag: "+tag);
			}
		}

		System.out.println("----------------------------------------------------");

	}

}
