package com.edu.utn.infoba;

import java.util.LinkedHashSet;
import java.util.List;
import com.edu.utn.infoba.modelo.Busqueda;

public class ReportePorUsuarioAdapter implements ReportesDataAdapter
{
    public LinkedHashSet<String> dataFromSource(Busqueda busqueda)
    {
        LinkedHashSet<String> datos = new LinkedHashSet<>();

        datos.add(busqueda.getUsuario());
        datos.add(String.valueOf(busqueda.getCantResultados())); // TODO: resultados parciales?

        return datos;
    }
}
