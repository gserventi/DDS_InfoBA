package com.edu.utn.infoba.procesos;

import com.edu.utn.infoba.repositorios.RepositorioPois;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.DisallowConcurrentExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution

public class ProcesoBajaPois extends ProcesoPoi
{

	public ProcesoBajaPois()
	{
		setSiguienteProceso(ProcesoAccionesPorUsuario.class);
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		SchedulerContext schedulerContext = null;

		try
		{
			schedulerContext = context.getScheduler().getContext();
		} 
		catch (SchedulerException se)
		{
			se.printStackTrace();
		}

		RepositorioPois repobaja = (RepositorioPois) (schedulerContext != null ? schedulerContext.get("repobaja") : null);

		try
		{
			repobaja.ProcesoBajas();
		} 
		catch (Exception e)
		{
			throw new JobExecutionException();
		}
	}
}
