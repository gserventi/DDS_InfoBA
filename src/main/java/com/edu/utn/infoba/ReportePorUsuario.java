package com.edu.utn.infoba;
import com.edu.utn.utils.ReportExporter;
import com.edu.utn.utils.ReportWriter;

import java.util.List;
import com.edu.utn.infoba.modelo.Busqueda;

public class ReportePorUsuario
{
    private ReportExporter exporter;

    public ReportePorUsuario(ReportExporter exporter)
    {
        this.exporter = exporter;
    }
		
	public void mostrarReporte(List<Busqueda> busquedas)
	{
            ReportePorUsuarioAdapter dataAdapter = new ReportePorUsuarioAdapter();
            String[] ColumnasReporte = new String[] {"Usuario", "Cantidad Resultados"};

            ReportWriter reporte = new ReportWriter(ColumnasReporte, dataAdapter, this.exporter);
            reporte.cargarDatos(busquedas);
            reporte.exportar();
	}
}
