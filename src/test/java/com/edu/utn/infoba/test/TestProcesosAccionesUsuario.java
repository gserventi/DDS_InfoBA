package com.edu.utn.infoba.test;

import com.edu.utn.infoba.*;
import com.edu.utn.infoba.modelo.AccionesUsuario;
import com.edu.utn.infoba.modelo.Terminal;
import com.edu.utn.infoba.procesos.*;
import com.edu.utn.infoba.repositorios.RepositorioBusqueda;
import com.edu.utn.infoba.repositorios.RepositorioPois;
import com.edu.utn.utils.MongoDBConnection;
import com.edu.utn.utils.PoisMySQL;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import javax.persistence.*;
import java.util.ArrayList;

public class TestProcesosAccionesUsuario
{
    // Tiempo para esperar luego de arrancar el planificador.
    private Integer SCHEDULER_WAIT_TIME = 2000;

    @Before
    public void setUp() throws Exception {}

    @Test
    public void procesoAccionesUsuario()
    {
        try
        {
        	Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            ArrayList<AccionesUsuario> listaAcciones = new ArrayList<>();
            ArrayList<Terminal> listaUsuarios = new ArrayList<>();
            Terminal terminal1 = new Terminal("Supermercado", "Passsword");
            AccionesUsuario accion = new AccionesUsuario();
            listaUsuarios.add(terminal1);
            accion.setNombre("BorrarPOI");
            accion.setUsuario(listaUsuarios.get(0));
            listaAcciones.add(accion);

            EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("infoba");
            FuenteDeDatos fuente = new FuenteDeDatos(null,new PoisMySQL(emFactory.createEntityManager()));
    		Datastore ds = MongoDBConnection.getInstance().getDatastore();
    		RepositorioBusqueda repoBusqueda = new RepositorioBusqueda(ds,new MockMailSender());
            RepositorioPois repositorio = new RepositorioPois(repoBusqueda,emFactory.createEntityManager());
            repositorio.agregarFuenteDeDatos(fuente);

            scheduler.getContext().put("listaUsuarios", listaUsuarios);
            scheduler.getContext().put("listaAcciones", listaAcciones);
            scheduler.getContext().put("repobaja", repositorio);

            scheduler.start();

            JobKey key = new JobKey(ProcesoAccionesPorUsuario.class.getSimpleName());
            JobDetail job = JobBuilder.newJob(ProcesoAccionesPorUsuario.class).withIdentity(key).requestRecovery(true).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger").startNow().build();
            ProcesoAccionesPorUsuarioListener procesoListener = new ProcesoAccionesPorUsuarioListener();

            // Crea una instancia del proceso inicial y su listener
            ProcesoAccionesPorUsuarioListener listener = new ProcesoAccionesPorUsuarioListener();
            listener.setEstadoInicial(listaUsuarios);

            // Asocia el listener al planificador
            scheduler.getListenerManager().addJobListener(listener, KeyMatcher.keyEquals(key));


            scheduler.getListenerManager().addJobListener(procesoListener, KeyMatcher.keyEquals(key));
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(SCHEDULER_WAIT_TIME);
            scheduler.shutdown();
        }
        catch(Exception e)
        {
            System.out.println("Excepción: " + e.getMessage());
        }
    }


    @Test
    public void procesoAccionesUsuarioRollback()
    {
        try
        {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            ArrayList<AccionesUsuario> listaAcciones = new ArrayList<>();
            ArrayList<Terminal> listaUsuarios = new ArrayList<>();
            Terminal terminal1 = new Terminal("Supermercado", "Passsword");
            AccionesUsuario accion = new AccionesUsuario();
            listaUsuarios.add(terminal1);
            accion.setNombre("BorrarPOI");
            accion.setUsuario(terminal1);
            listaAcciones.add(accion);

            EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("infoba");
            FuenteDeDatos fuente = new FuenteDeDatos(null,new PoisMySQL(emFactory.createEntityManager()));
    		Datastore ds = MongoDBConnection.getInstance().getDatastore();
    		RepositorioBusqueda repoBusqueda = new RepositorioBusqueda(ds,new MockMailSender());
            RepositorioPois repositorio = new RepositorioPois(repoBusqueda,emFactory.createEntityManager());
            repositorio.agregarFuenteDeDatos(fuente);

            scheduler.getContext().put("listaUsuarios", listaUsuarios);
            scheduler.getContext().put("repobaja", repositorio);

            scheduler.start();

            JobKey key = new JobKey(ProcesoAccionesPorUsuario.class.getSimpleName());
            JobDetail job = JobBuilder.newJob(ProcesoAccionesPorUsuario.class).withIdentity(key).requestRecovery(true).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger").startNow().build();
            ProcesoAccionesPorUsuarioListener procesoListener = new ProcesoAccionesPorUsuarioListener();

            // Crea una instancia del proceso inicial y su listener
            ProcesoAccionesPorUsuarioListener listener = new ProcesoAccionesPorUsuarioListener();
            listener.setEstadoInicial(listaUsuarios);

            // Asocia el listener al planificador
            scheduler.getListenerManager().addJobListener(listener, KeyMatcher.keyEquals(key));

            //scheduler.getListenerManager().addJobListener(procesoListener, KeyMatcher.keyEquals(key));
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(SCHEDULER_WAIT_TIME);
            scheduler.shutdown();
        }
        catch(Exception e)
        {
            System.out.println("Excepción: " + e.getMessage());
        }
    }

}
