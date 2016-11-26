package com.edu.utn.infoba.procesos;

import com.edu.utn.infoba.modelo.AccionesUsuario;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;

import com.edu.utn.infoba.modelo.Terminal;

import java.util.ArrayList;

public class ProcesoAccionesPorUsuario extends ProcesoPoi
{
    private ArrayList<AccionesUsuario> accionesAAsignar;
    private ArrayList<Terminal> usuarios;

    //	En el constructor es donde queda definido el proceso siguiente
    public ProcesoAccionesPorUsuario()
    {
        setSiguienteProceso(null);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        this.cargar(context);

        try
        {
            for (Terminal usuario : usuarios)
            {
                accionesAAsignar.forEach(usuario::agregarAccion);
            }
        }
        catch(Exception e)
        {
            throw new JobExecutionException();
        }
    }

    public void cargar(JobExecutionContext context) throws JobExecutionException
    {
        SchedulerContext schedulerContext;

        try
        {
            schedulerContext = context.getScheduler().getContext();
            @SuppressWarnings("unchecked")
			ArrayList<Terminal> usuarios = (ArrayList<Terminal>)schedulerContext.get("listaUsuarios");
            @SuppressWarnings("unchecked")
			ArrayList<AccionesUsuario> acciones = (ArrayList<AccionesUsuario>)schedulerContext.get("listaAcciones");

            this.accionesAAsignar = acciones;
            this.usuarios = usuarios;
        }
        catch(Exception e)
        {
            throw new JobExecutionException();
        }
    }

}