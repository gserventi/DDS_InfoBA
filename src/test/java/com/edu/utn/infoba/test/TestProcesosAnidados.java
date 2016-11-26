package com.edu.utn.infoba.test;

import com.edu.utn.infoba.*;
import com.edu.utn.infoba.procesos.ProcesoActualizaLocales;
import com.edu.utn.infoba.procesos.ProcesoActualizaLocalesListener;
import com.edu.utn.infoba.procesos.ProcesoListener;
import com.edu.utn.infoba.repositorios.RepositorioBusqueda;
import com.edu.utn.infoba.repositorios.RepositorioPois;
import com.edu.utn.utils.Configuracion;
import com.edu.utn.utils.MongoDBConnection;
import com.edu.utn.utils.PoisMySQL;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.edu.utn.infoba.modelo.*;

public class TestProcesosAnidados
{
    // Tiempo para esperar luego de arrancar el planificador.
    private Integer SCHEDULER_WAIT_TIME = 2000;
    private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
	
    @Before
    public void setUp() throws Exception {}

    @Test
    public void ProcesosAnidados() throws SchedulerException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
    	Configuracion config = new Configuracion();

        try
        {
        	Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            /* ---------------------  VARIABLES DEL PROCESO 1  --------------------- */
        	emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        	FuenteDeDatos fuente = new FuenteDeDatos(new OrigenDeDatosDummy(),new PoisMySQL(emFactory.createEntityManager()));
        	Comercio comercio = new Comercio("LosMuebles1",null,new Coordenadas(15.0, 55.0),new Rubro("Muebleria", 200.0));
     		comercio.agregarTag(new Tag("tag3"));
     		fuente.agregarPuntoDeInteres(comercio);
            /* -----------------------   ----------------------- */

     		/* ---------------------  VARIABLES DEL PROCESO 2  --------------------- */
    		Datastore ds = MongoDBConnection.getInstance().getDatastore();
    		RepositorioBusqueda repoBusqueda = new RepositorioBusqueda(ds,new MockMailSender());
    		RepositorioPois repobaja = new RepositorioPois(repoBusqueda,emFactory.createEntityManager());

    		Banco banco = new Banco("ITAU", null, new Coordenadas(1.0,10.0));
    		banco.agregarServicio(new Servicio("Cobros"));
    		banco.agregarServicio(new Servicio("Pagos"));
    		banco.agregarServicio(new Servicio("Atencion al cliente"));
    		banco.agregarTag(new Tag("tag1"));

    		CGP cgp = new CGP("Comuna10",null,new Coordenadas(2.0,13.0));
    		cgp.agregarServicio(new Servicio("Atencion ciudadana"));
    		cgp.agregarServicio(new Servicio("Pagos"));
    		cgp.agregarServicio(new Servicio("Tramites"));
    		cgp.agregarTag(new Tag("Tag2"));

    		Comercio comerciob = new Comercio("LosMuebles",null,new Coordenadas(15.0, 55.0), new Rubro("Muebleria", 200.0));
    		comerciob.agregarTag(new Tag("tag3"));

    		ParadaColectivos colectivo = new ParadaColectivos("7y101",null,new Coordenadas(2.0, 13.0));
    		colectivo.agregarTag(new Tag("UTN"));

    		fuente.agregarPuntoDeInteres(banco);
    		fuente.agregarPuntoDeInteres(cgp);
    		fuente.agregarPuntoDeInteres(comerciob);
    		fuente.agregarPuntoDeInteres(colectivo);
    		
    		repobaja.agregarFuenteDeDatos(fuente);
    		repobaja.setFuenteDeDatosLocal(fuente);
    		/* -----------------------   ----------------------- */

     		/* ---------------------  VARIABLES DEL PROCESO 3  --------------------- */
            ArrayList<String> listaAcciones = new ArrayList<>();
            ArrayList<Terminal> listaUsuarios = new ArrayList<>();
            Terminal terminal1 = new Terminal("Supermercado", "Passsword");
            listaUsuarios.add(terminal1);
            listaAcciones.add("BorrarPOI");

            ProcesoListener.setIntentos(Integer.valueOf(config.obtenerPropiedad("intentos")));
            scheduler.getContext().put("listaUsuarios", listaUsuarios);
            scheduler.getContext().put("listaAcciones", listaAcciones);
            scheduler.getContext().put("repositorio", fuente);
            scheduler.getContext().put("repobaja", repobaja);
    		/* -----------------------   ----------------------- */

            scheduler.start();
            
            JobKey key = new JobKey(ProcesoActualizaLocales.class.getSimpleName());
            JobDetail job = JobBuilder.newJob(ProcesoActualizaLocales.class).withIdentity(key).requestRecovery(true).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger").startNow().build();
            ProcesoActualizaLocales procesoInicial = new ProcesoActualizaLocales();
            ProcesoActualizaLocalesListener procesoListener =  (ProcesoActualizaLocalesListener) procesoInicial.getProcesoListener();

            scheduler.getListenerManager().addJobListener(procesoListener, KeyMatcher.keyEquals(key));
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(SCHEDULER_WAIT_TIME);
            scheduler.shutdown();
        }
        catch(Exception e)
        {
            System.out.println("Excepción!");
            System.out.println(e.getMessage());
        }
    }
}
