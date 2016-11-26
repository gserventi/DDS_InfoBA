package com.edu.utn.infoba.repositorios;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.edu.utn.infoba.MailSender;
import com.edu.utn.infoba.ReportePorFecha;
import com.edu.utn.infoba.ReportePorUsuario;
import com.edu.utn.infoba.modelo.Busqueda;
import com.edu.utn.infoba.modelo.PuntoDeInteres;
import com.edu.utn.infoba.modelo.Usuario;
import com.edu.utn.utils.CSVExporter;
import com.edu.utn.utils.Configuracion;
import java.io.File;

public class RepositorioBusqueda
{
	private Datastore datastore;
	private Double tiempoLimite;
	private MailSender mailSender;
	Configuracion config = new Configuracion();
	
	public RepositorioBusqueda(Datastore datastore, MailSender sender)
    {
		this.datastore = datastore;
		this.tiempoLimite = Double.valueOf(config.obtenerPropiedad("tiempoLimite"));
		this.mailSender = sender;
	}

    public List<Busqueda> buscarBusquedaPorTexto(String texto)
    {
		Query<Busqueda> query = datastore.find(Busqueda.class).field("texto").contains(texto);
    	List<Busqueda> busquedas = query.asList();
		return busquedas;
	}
    public List<Busqueda> buscarBusquedaPorUsuario(String usuario)
    {
		Query<Busqueda> query = datastore.find(Busqueda.class).field("usuario").contains(usuario);
    	List<Busqueda> busquedas = query.asList();
		return busquedas;
	}
    public List<Busqueda> buscarBusquedaPorFecha(Date fechame, Date fechama)
    {
		Query<Busqueda> query = datastore.find(Busqueda.class).filter("fechaFormato >", fechame).filter("fechaFormato <", fechama);
    	List<Busqueda> busquedas = query.asList();
		return busquedas;
	}

	public boolean existeBusqueda(String texto) {
		return !buscarBusquedaPorTexto(texto).isEmpty();
    }
	
	public void registrarBusqueda(String usuario, List<PuntoDeInteres> puntosDeInteres, int cantResultados, Double tInicio,
                          Double tFin, String texto, Date fecha, String fechaFormato)
    {
		Double TiempoDeEjecucion = tFin - tInicio;
		Busqueda busqueda = new Busqueda(usuario, puntosDeInteres, cantResultados, TiempoDeEjecucion, texto, fecha, fechaFormato);
		datastore.save(busqueda);

		if(TiempoDeEjecucion > this.tiempoLimite)
			notificarTiempo(TiempoDeEjecucion);

	}
	
	public void registrarBusqueda(Busqueda busqueda) {
		datastore.save(busqueda);
	}

	public void notificarTiempo(Double tiempoDeEjecucion)
    {
		final String fromEmail = config.obtenerPropiedad("fromEmail"); //Email desde el cual se va a mandar
		final String password = config.obtenerPropiedad("password"); // Pass del mail desde donde se va a mandar
		final String toEmail = config.obtenerPropiedad("toEmail"); // Email al cual se va a mandar
		String asunto = config.obtenerPropiedad("asunto");
		String body = config.obtenerPropiedad("asuntoBody").replaceAll("_", tiempoDeEjecucion.toString());
		mailSender.send(fromEmail , password, toEmail, asunto, body);
	}

	public void reportePorFecha() {
		// TODO: mover afuera de la clase con un strategy. (Como ?? )
		Configuracion config = new Configuracion();
		String nombreArchivo = config.obtenerPropiedad("archivoReporteFecha");
		String archivoReportes = config.obtenerPropiedad("archivoReportes") + File.separator + nombreArchivo;
		CSVExporter exporter = new CSVExporter(archivoReportes);

		ReportePorFecha reporte = new ReportePorFecha(exporter);
		reporte.mostrarReporte(this.buscarBusquedaPorTexto(""));
	}

	public void reportePorUsuario()
	{
		// TODO: mover afuera de la clase con un strategy. (Como ?? )
		Configuracion config = new Configuracion();
		String nombreArchivo = config.obtenerPropiedad("archivoReporteUsuario");
		String archivoReportes = config.obtenerPropiedad("archivoReportes") + File.separator + nombreArchivo;
		CSVExporter exporter = new CSVExporter(archivoReportes);

		ReportePorUsuario reporte = new ReportePorUsuario(exporter);
		reporte.mostrarReporte(this.buscarBusquedaPorTexto(""));
	}
}
