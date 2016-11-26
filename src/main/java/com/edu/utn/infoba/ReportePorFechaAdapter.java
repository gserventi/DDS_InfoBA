package com.edu.utn.infoba;

import java.util.LinkedHashSet;
import com.edu.utn.infoba.modelo.Busqueda;

public class ReportePorFechaAdapter implements ReportesDataAdapter
{
    public LinkedHashSet<String> dataFromSource(Busqueda busqueda)
    {
        LinkedHashSet<String> datos = new LinkedHashSet<>();

        datos.add(busqueda.getFechaFormato());
        datos.add(String.valueOf(busqueda.getCantResultados()));

        return datos;
    }
}
