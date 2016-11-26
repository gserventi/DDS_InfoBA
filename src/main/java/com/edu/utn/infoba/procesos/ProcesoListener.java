package com.edu.utn.infoba.procesos;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.KeyMatcher;

import com.edu.utn.infoba.ResulProcesos;
import com.edu.utn.infoba.repositorios.RepositorioPois;
import com.edu.utn.utils.Configuracion;


public abstract class ProcesoListener implements JobListener
{
	Configuracion config = new Configuracion();
	private static int intentos;

	public static void setIntentos(int intentos)
	{
		ProcesoListener.intentos = intentos;
	}

	public String getName()
	{
		return getClass().getName();
	}

	// Las subclases concretas que hereden de esta clase abstracta deben implementar este método
	protected abstract void rollback();

	@Override
	public void jobToBeExecuted(JobExecutionContext context)
	{
		System.out.println("Antes de ejecutar el proceso: " + context.getJobDetail().getKey().getName());
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {}

	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException)
	{
		String jobName = context.getJobDetail().getKey().getName();
		ResulProcesos resultado = new ResulProcesos(null, 0, null, null, null, null);

		System.out.println("Fecha y hora de inicio de ejecucion: "+ context.getFireTime().toString());

		resultado.setFechainicio(context.getFireTime().toString());

		System.out.println("Se ejecuto durante "+ context.getJobRunTime() + " milisegundos");

		resultado.setTiempo(context.getJobRunTime());

		System.out.println("Fecha y hora de fin de ejecucion: "+ sumarMilisegundos(context.getFireTime(),context.getJobRunTime()));

		resultado.setFechafinal((sumarMilisegundos(context.getFireTime(),context.getJobRunTime())).toString());

		System.out.println("Proceso ejecutado: "+ jobName);

		resultado.setNombre(jobName);
		RepositorioPois repo;

		try
		{
			repo = (RepositorioPois)context.getScheduler().getContext().get("repobaja");
			repo.registrarResultadoJob(resultado);
		}
		catch (SchedulerException e1)
		{
			//No se registran los resultados
		}

		if (jobException == null)
		{
			System.out.println("Proceso : " + jobName + " ejecutado con normalidad");
			resultado.setMsjresultado("Proceso : " + jobName + " ejecutado con normalidad");
			resultado.setRespuesta(true);

			try
			{
               ejecutarProcesoAnidado(context);
			}
			catch (SchedulerException e)
			{
               System.out.println(e.getMessage());
			}
		}
		else
		{
           System.out.println("Hubo una excepción en el proceso: " + jobName + " La excepción lanzada fue: " + jobException);
           resultado.setMsjresultado("Hubo una excepción en el proceso: " + jobName + " La excepción lanzada fue: " + jobException);
		   resultado.setRespuesta(false);
		   if(config.obtenerPropiedad("Reintentar").equals("true"))
			{
			   //int restan = Integer.valueOf(config.obtenerPropiedad("restan"));
				if(intentos > 0)
				{
					intentos--;

					try {
						/*
						 * Hay que buscar al forma de que vaya decrementando los restantes , por que al volver a
						 * ejecutar el proceso vuelve a leer del archivo de config las veces que hay que repetirlo y
						 * hace un loop infinito.
						*/
						ejecutarElMismo(context);
						System.out.println(intentos);
					}
					catch (SchedulerException e)
                    {
						e.printStackTrace();
					}
					
				}
				else
                {
					intentos = Integer.valueOf(config.obtenerPropiedad("intentos"));
				}
			}

     	    rollback();
		}
	}

	@SuppressWarnings("unchecked")
	public void ejecutarProcesoAnidado(JobExecutionContext context) throws SchedulerException
	{
		Scheduler scheduler = context.getScheduler();
		String nombreProcesoActual = getClass().getName().replace("Listener", "");

		try
		{
			@SuppressWarnings("rawtypes")
			Class actualProceso = getClass().getClassLoader().loadClass(nombreProcesoActual);
			

			@SuppressWarnings({ "static-access", "rawtypes" })
			Class siguienteProceso = ((ProcesoPoi)actualProceso.newInstance()).getSiguienteProceso();


			if (siguienteProceso != null)
			{
				ProcesoListener siguienteListener = ((ProcesoPoi) siguienteProceso.newInstance()).getProcesoListener();
				JobKey jobKey = new JobKey(siguienteProceso.getSimpleName());
				JobDetail nextJob = JobBuilder.newJob(siguienteProceso).withIdentity(jobKey).requestRecovery(true).build();
				Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobKey + "trigger").startNow().build();
				scheduler.getListenerManager().addJobListener(siguienteListener, KeyMatcher.keyEquals(jobKey));
				scheduler.scheduleJob(nextJob, trigger);
			}
		}
		catch (ClassNotFoundException | InstantiationException cnf)
		{
			System.out.println(cnf.getMessage());
		} catch (IllegalAccessException iae)
		{
			System.out.println(iae.getMessage());
		}
	}
	
	public Date sumarMilisegundos(Date fecha, long milisegundos){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.MILLISECOND, (int) milisegundos);
		return calendar.getTime();
	}
	public void ejecutarElMismo(JobExecutionContext context) throws SchedulerException
	{
		Scheduler scheduler = context.getScheduler();
		String nombreProcesoActual = getClass().getName().replace("Listener", "");
		
		try
		{

			Class actualProceso = getClass().getClassLoader().loadClass(nombreProcesoActual);

			if (actualProceso != null)
			{

				ProcesoListener Listener = ((ProcesoPoi) actualProceso.newInstance()).getProcesoListener();
				JobKey jobKey = new JobKey(actualProceso.getSimpleName());
				JobDetail nextJob = context.getJobDetail();
				Trigger trigger = context.getTrigger();
				scheduler.getListenerManager().addJobListener(Listener, KeyMatcher.keyEquals(jobKey));
				
				if(scheduler.checkExists(trigger.getKey()))
				{
					scheduler.unscheduleJob(trigger.getKey());
				}

				scheduler.scheduleJob(nextJob, trigger);
			}

		}
		catch (ClassNotFoundException | IllegalAccessException | InstantiationException cnf)
		{
			System.out.println(cnf.getMessage());
		}
	}

}
