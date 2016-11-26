package com.edu.utn.infoba.repositorios;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.edu.utn.infoba.FuenteDeDatos;
import com.edu.utn.infoba.MockMailSender;
import com.edu.utn.infoba.ReportesProcesos;
import com.edu.utn.infoba.ResulProcesos;
import com.edu.utn.infoba.modelo.PuntoDeInteres;
import com.edu.utn.utils.ServicioRestBaja;

public class RepositorioPois
{
	protected EntityManager em;
	private List<FuenteDeDatos> fuentesDeDatos;
	private RepositorioBusqueda repoBusqueda;
	//private String usuario = "Usuario123";
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private FuenteDeDatos fuenteDeDatosLocal;
    private ReportesProcesos reporteProcesos = new ReportesProcesos(new MockMailSender());
	private Boolean estadisticasHabilitadas;

	public RepositorioPois(RepositorioBusqueda repoBusc, EntityManager em)
    {
		this.em = em;
		this.fuentesDeDatos = new ArrayList<>();
		this.repoBusqueda = repoBusc;
		this.estadisticasHabilitadas = true;
	}

	// TODO: revisar a ver si esto sirve para algo. Pareciera que login no hace nada, al menos en principio.
	/*
	public Boolean login(String usuario, String password)
	{
		return false;
	}
	
	public void agregarAdministrador(Administrador admin)
	{
		this.administradores.add(admin);
	}
	
	public void eliminarAdministrador(Administrador admin)
	{
		this.administradores.remove(admin);
	}
	*/
	
	public void agregarFuenteDeDatos(FuenteDeDatos fuente)
	{
		this.fuentesDeDatos.add(fuente);
	}
	
	public void eliminarFuenteDeDatos(FuenteDeDatos fuente)
	{
		this.fuentesDeDatos.remove(fuente);
	}
	
	public List<PuntoDeInteres> buscarPuntoDeInteresPorTexto(String texto,String usuario)
	{
		List<PuntoDeInteres> puntosDeInteres;
		Double tiempoInicio = (double) System.currentTimeMillis();
		Double tiempoFinal;
		puntosDeInteres = new ArrayList<PuntoDeInteres>();
		
		for(FuenteDeDatos fuente : this.fuentesDeDatos)
		{
			puntosDeInteres.addAll(fuente.buscarPuntoDeInteresPorTexto(texto));
		}

		tiempoFinal = (double) System.currentTimeMillis();
        Date date = new Date();
        String fechaFormato = this.format.format(new Date());
        int resultados = puntosDeInteres.size();

        if(this.estadisticasHabilitadas)
        {
			this.repoBusqueda.registrarBusqueda(usuario, puntosDeInteres, resultados, tiempoInicio , tiempoFinal, texto, date, fechaFormato);
        }

		return puntosDeInteres;
	}

//	public String getUsuario()
//    {
//		return this.usuario;
//	}
//
//	public void setUsuario(String usuario)
//    {
//		this.usuario = usuario;
//	}

	public void deshabilitarEstadisticas()
	{
     	this.estadisticasHabilitadas = false;
	}

	public RepositorioBusqueda getRepoBusqueda() {
		return repoBusqueda;
	}

	public void setRepoBusqueda(RepositorioBusqueda repoBusqueda) {
		this.repoBusqueda = repoBusqueda;
	}

	public void habilitarEstadisticas()
	{
		this.estadisticasHabilitadas = true;
	}
	
	public void reportePorFecha()
	{
       if(this.estadisticasHabilitadas)
       {
		   this.repoBusqueda.reportePorFecha();
       }
	}

	public void reportePorUsuario()
	{
        if(this.estadisticasHabilitadas)
        {
			this.repoBusqueda.reportePorUsuario();
        }
	}
	
	public FuenteDeDatos getFuenteDeDatosLocal()
    {
		return this.fuenteDeDatosLocal;
	}

	public void setFuenteDeDatosLocal(FuenteDeDatos fuenteDeDatosLocal)
    {
		this.fuenteDeDatosLocal = fuenteDeDatosLocal;
	}
	
	public void ProcesoBajas() throws IOException, ParseException, Exception
	{
		ServicioRestBaja servicio = new ServicioRestBaja();
		List<String> listaBajas = servicio.obtenerListaBajas();

		fuenteDeDatosLocal.bajaDePOIs(listaBajas);
	}
	
	public void registrarResultadoJob(ResulProcesos resultado)
	{
		this.reporteProcesos.registrar(resultado);
	}
}
