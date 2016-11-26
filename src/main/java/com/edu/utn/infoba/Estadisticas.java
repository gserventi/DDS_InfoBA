//package com.edu.utn.infoba;
//
//import com.edu.utn.utils.CSVExporter;
//import com.edu.utn.utils.Configuracion;
//
//import java.io.File;
//import java.util.Date;
//import java.util.HashSet;
//
//public class Estadisticas
//{
//	private HashSet<EstadisticaBusqueda> busquedas;
//	private Double tiempoLimite;
//	private MailSender mailSender;
//
//    Configuracion config = new Configuracion();
//
//	public Estadisticas(MailSender sender)
//	{
//		this.tiempoLimite = Double.valueOf(config.obtenerPropiedad("tiempoLimite"));
//		this.busquedas = new HashSet<>();
//		this.mailSender = sender;
//	}
//	
//	public void registrar(String usuario, int cantResultados, double tInicio, double tFin,
//                          String texto, Date fecha, String fechaFormato)
//	{
//		Double TiempoDeEjecucion = tFin - tInicio;
//
//		this.busquedas.add(new EstadisticaBusqueda(usuario, cantResultados, TiempoDeEjecucion, texto, fecha, fechaFormato));
//
//		if(TiempoDeEjecucion > this.tiempoLimite)
//		{
//			notificarTiempo(TiempoDeEjecucion);
//		}
//	}
//
//	public void notificarTiempo(Double tiempoDeEjecucion )
//	{
//		final String fromEmail = config.obtenerPropiedad("fromEmail"); //Email desde el cual se va a mandar
//		final String password = config.obtenerPropiedad("password"); // Pass del mail desde donde se va a mandar
//		final String toEmail = config.obtenerPropiedad("toEmail"); // Email al cual se va a mandar
//		String asunto = config.obtenerPropiedad("asunto");
//		String body = config.obtenerPropiedad("asuntoBody").replaceAll("_", tiempoDeEjecucion.toString());
//
//		mailSender.send(fromEmail , password, toEmail, asunto, body);
//	}
//
//	public void reportePorFecha()
//	{
//        // TODO: mover afuera de la clase con un strategy. (Como ?? )
//        Configuracion config = new Configuracion();
//        String nombreArchivo = config.obtenerPropiedad("archivoReporteFecha");
//        String archivoReportes = config.obtenerPropiedad("archivoReportes") + File.separator + nombreArchivo;
//        CSVExporter exporter = new CSVExporter(archivoReportes);
//
//		ReportePorFecha reporte = new ReportePorFecha(exporter);
//		reporte.mostrarReporte(this.busquedas);
//	}
//
//	public void reportePorUsuario()
//	{
//        // TODO: mover afuera de la clase con un strategy. (Como ?? )
//        Configuracion config = new Configuracion();
//        String nombreArchivo = config.obtenerPropiedad("archivoReporteUsuario");
//        String archivoReportes = config.obtenerPropiedad("archivoReportes") + File.separator + nombreArchivo;
//        CSVExporter exporter = new CSVExporter(archivoReportes);
//
//		ReportePorUsuario reporte = new ReportePorUsuario(exporter);
//		reporte.mostrarReporte(this.busquedas);
//	}
//
//}