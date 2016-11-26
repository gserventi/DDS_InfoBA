package com.edu.utn.infoba.test;

import com.edu.utn.infoba.procesos.ProcesoActualizaLocalesListener;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import com.edu.utn.infoba.FuenteDeDatos;
import com.edu.utn.infoba.OrigenDeDatos;
import com.edu.utn.infoba.OrigenDeDatosDummy;
import com.edu.utn.infoba.procesos.ProcesoActualizaLocales;
import com.edu.utn.infoba.procesos.ProcesoListener;
import com.edu.utn.infoba.procesos.ProcesoPoi;
import com.edu.utn.utils.PoisMySQL;
import com.edu.utn.infoba.modelo.Tag;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;
import com.edu.utn.infoba.modelo.*;

public class TestProcesoActualizaComercio
{
	private FuenteDeDatos fuente;
	private Comercio comercio;
	private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;

    private Integer THREAD_SLEEP_TIME = 2000; // Thread sleep time (in ms).

	@Before
	public void setUp() throws Exception, SchedulerException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		fuente = new FuenteDeDatos(new OrigenDeDatosDummy(),new PoisMySQL(emFactory.createEntityManager()));

		Rubro rubro = new Rubro("Muebleria", 200.0);
		Coordenadas coordenadas = new Coordenadas(15.0, 55.0);
        Tag tag3 = new Tag("tag3");
		comercio = new Comercio("LosMuebles",null,coordenadas, rubro);
        tag3.setPuntoDeInteres(comercio);
        comercio.agregarTag(tag3);

		fuente.agregarPuntoDeInteres(comercio);
	}

	@Test
	public void testActualiza() throws IOException, SchedulerException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException
    {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		
	    scheduler.getContext().put("fuente", fuente);

		// Inicia el planificador
		scheduler.start();

		JobKey key = new JobKey(ProcesoActualizaLocales.class.getSimpleName());
		JobDetail job = JobBuilder.newJob(ProcesoActualizaLocales.class).withIdentity(key).requestRecovery(true).build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger").startNow().build();

		ProcesoPoi procesoInicial = new ProcesoActualizaLocales();
		ProcesoListener procesoInicialListener = procesoInicial.getProcesoListener();

		scheduler.getListenerManager().addJobListener(procesoInicialListener, KeyMatcher.keyEquals(key));

		// Agrega el proceso al planificador junto con su disparador (trigger)
		StdSchedulerFactory.getDefaultScheduler().scheduleJob(job, trigger);
		Thread.sleep(THREAD_SLEEP_TIME);
		scheduler.shutdown();

		PuntoDeInteres poi = fuente.buscarPuntoDeInteresPorNombre("LosMuebles");

        // Ver si se agrego los tags al local existente

        if(poi == null)
            fail("No existen puntos de interés que coincidan con la búsqueda");

        List<String> tags;

        tags = poi.getTags();

        assertTrue("No se actualizo el tag muebles del local LosMuebles", tags.contains("muebles"));
		assertTrue("No se actualizo el tag carpinteria del local LosMuebles", tags.contains("carpinteria"));

        poi = fuente.buscarPuntoDeInteresPorNombre("Carrousel");

        tags = poi.getTags();

		// El Local Comercial Carrousel que aparece en el archivo no existe en la fuente de datos, se debio agregar
		assertEquals("No se dio de alta el nuevo Local Comercial Carrousel", poi.getNombre(),"Carrousel");

		// Ver si se agrego los tags al local nuevo
		assertTrue("No se agrego el tag colegio al local Carrousel", tags.contains("colegio"));
		assertTrue("No se agrego el tag escolar al local Carrousel", tags.contains("escolar"));
		assertTrue("No se agrego el tag uniformes al local Carrousel", tags.contains("uniformes"));

		fuente.eliminarPuntoDeInteres(comercio);
	}


	@Test
	public void testRollback() throws IOException, SchedulerException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException
	{
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		// Inicia el planificador
		scheduler.start();

		JobKey key = new JobKey(ProcesoActualizaLocales.class.getSimpleName());
		JobDetail job = JobBuilder.newJob(ProcesoActualizaLocales.class).withIdentity(key).requestRecovery(true).build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger").startNow().build();

		ProcesoListener procesoInicialListener = new ProcesoActualizaLocalesListener();

		scheduler.getListenerManager().addJobListener(procesoInicialListener, KeyMatcher.keyEquals(key));

		// Agrega el proceso al planificador junto con su disparador (trigger)
		StdSchedulerFactory.getDefaultScheduler().scheduleJob(job, trigger);
		Thread.sleep(THREAD_SLEEP_TIME);
		scheduler.shutdown();
	}

}
