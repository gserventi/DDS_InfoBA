package com.edu.utn.utils;

import com.edu.utn.infoba.ReportesDataAdapter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import com.edu.utn.infoba.modelo.Busqueda;

public class ReportWriter
{
    private HashSet<String> columnas;
    private HashSet<FilaReporte> datos;
    private ReportesDataAdapter adapter;
    private ReportExporter exporter;

    public ReportWriter(String[] columnas, ReportesDataAdapter dataAdapter, ReportExporter exporter)
    {
       this.columnas = new HashSet<>();
       this.columnas.addAll(Arrays.asList(columnas));
       this.datos = new HashSet<>();
       this.adapter = dataAdapter;
       this.exporter = exporter;
    }

    public void cargarDatos(List<Busqueda> dataSource)
    {
        for (Busqueda dato: dataSource)
        {
            LinkedHashSet<String> datosColumna = this.adapter.dataFromSource(dato);
            FilaReporte fila = new FilaReporte(datosColumna);
            this.datos.add(fila);
        }
    }

    public void exportar()
    {
        this.exporter.export(this.columnas, this.datos);
    }
}
