package com.edu.utn.infoba.procesos;

import com.edu.utn.infoba.modelo.AccionesUsuario;
import com.edu.utn.infoba.modelo.Terminal;
import org.quartz.JobListener;

import java.util.ArrayList;
import java.util.Set;

public class ProcesoAccionesPorUsuarioListener extends ProcesoListener implements JobListener
{
    private ArrayList<Terminal> usuarios;
    private ArrayList<Terminal> estadoInicial;

    public ProcesoAccionesPorUsuarioListener()
    {
       this.usuarios = new ArrayList<>();
       this.estadoInicial = new ArrayList<>();
    }

    public void setEstadoInicial(ArrayList<Terminal> usuarios)
    {
        for(Terminal usuario : usuarios)
        {
           this.usuarios.add(usuario);
           this.estadoInicial.add(usuario);
        }
    }

    @Override
    protected void rollback()
    {
        System.out.println("Rollback de ProcesoAccionesPorUsuario.");

        int index;

        for(Terminal usuario: this.usuarios)
        {
            index = this.estadoInicial.indexOf(usuario);
            if(index != -1)
            {
                Set<AccionesUsuario> accionesARestaurar = this.estadoInicial.get(index).getAcciones();
                usuario.limpiarAcciones();

                accionesARestaurar.forEach(usuario::agregarAccion);
            }
        }
    }
}