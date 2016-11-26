package com.edu.utn.infoba.procesos;
import org.quartz.Job;

// Todos los procesos deben heredar de esta clase e implementar el método execute() que exige la interfaz Job
public abstract class ProcesoPoi implements Job
{
    @SuppressWarnings("rawtypes")
	private static Class SIGUIENTE_PROCESO;

    public static Class getSiguienteProceso()
    {
        return SIGUIENTE_PROCESO;
    }

    public static void setSiguienteProceso(@SuppressWarnings("rawtypes") Class siguienteProceso)
    {
        SIGUIENTE_PROCESO = siguienteProceso;
    }

    public ProcesoListener getProcesoListener() throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
      return (ProcesoListener) getClass().getClassLoader().loadClass(getClass().getName()+"Listener").newInstance();
    }
}
