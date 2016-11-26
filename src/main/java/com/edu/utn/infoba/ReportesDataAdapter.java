package com.edu.utn.infoba;

import java.util.LinkedHashSet;
import com.edu.utn.infoba.modelo.Busqueda;

public interface ReportesDataAdapter
{
    LinkedHashSet<String> dataFromSource(Busqueda busqueda);
}