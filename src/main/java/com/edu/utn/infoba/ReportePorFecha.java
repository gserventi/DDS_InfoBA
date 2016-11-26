package com.edu.utn.infoba;

import com.edu.utn.utils.ReportExporter;

import com.edu.utn.utils.ReportWriter;
import java.util.List;
import com.edu.utn.infoba.modelo.Busqueda;

public class ReportePorFecha
{
    private ReportExporter exporter;

	public ReportePorFecha(ReportExporter exporter)
    {
        this.exporter = exporter;
    }

	public void mostrarReporte(List<Busqueda> busquedas)
	{
   		  try
		  {
              String[] ColumnasReporte = new String[] {"Fecha", "Cantidad Busquedas"};

              ReportePorFechaAdapter dataAdapter = new ReportePorFechaAdapter();

              ReportWriter reporte = new ReportWriter(ColumnasReporte, dataAdapter, this.exporter);
              reporte.cargarDatos(busquedas);
              reporte.exportar();
          }
          catch (Exception e)
          {
              e.printStackTrace();
          }
	}
}