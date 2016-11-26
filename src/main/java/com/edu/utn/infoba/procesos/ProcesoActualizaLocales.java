package com.edu.utn.infoba.procesos;

import com.edu.utn.infoba.*;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.DisallowConcurrentExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution

public class ProcesoActualizaLocales extends ProcesoPoi
{
	// En el constructor es donde queda definido el proceso siguiente
	public ProcesoActualizaLocales()
	{
		setSiguienteProceso(ProcesoBajaPois.class);
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		SchedulerContext schedulerContext = null;

		try
		{
			schedulerContext = context.getScheduler().getContext();
            FuenteDeDatos fuente = (FuenteDeDatos)schedulerContext.get("fuente");
            fuente.actualizarLocales();
		}
		catch (SchedulerException se)
		{
            throw new JobExecutionException();
		}
		catch (Exception e)
        {
			throw new JobExecutionException();
		}
	}
}
