package com.edu.utn.infoba.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.mongodb.morphia.Datastore;

import com.edu.utn.infoba.MailSender;
import com.edu.utn.infoba.modelo.Busqueda;
import com.edu.utn.infoba.modelo.PuntoDeInteres;
import com.edu.utn.infoba.repositorios.RepositorioBusqueda;
import com.edu.utn.utils.MongoDBConnection;

@ManagedBean
public class HistorialBean 
{
	
	private List<Busqueda> items;
	private Datastore datastore;
    private RepositorioBusqueda repoBusqueda;
    private MailSender mailSender;
    
    private String texto;
    private String usuario;
    private String fechame;
    private String fechama;
    
	public HistorialBean(){
		
		datastore = MongoDBConnection.getInstance().getDatastore();
    	repoBusqueda = new RepositorioBusqueda(datastore, mailSender);
    	
	}
	public String getCriterio() {
		return texto;
	}

	public void setCriterio(String texto) {
		this.texto = texto;
	}


	public List<Busqueda> getItems() {
		return items;
	}
	public void setItems(List<Busqueda> items) {
		this.items = items;
	}
	public void busquedasTexto()
	{
		
		System.out.println(this.texto);
		List<Busqueda> resultados = repoBusqueda.buscarBusquedaPorTexto(this.texto);
		
		setItems(resultados);
	}
	
	public void busquedasUsuario()
	{
		
		System.out.println(this.texto);
		List<Busqueda> resultados = repoBusqueda.buscarBusquedaPorUsuario(this.usuario);
		setItems(resultados);
	}
	public void busquedasoFecha() throws ParseException
	{
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date dateMin = format.parse(this.fechame);
		Date dateMax = format.parse(this.fechama);
		System.out.println(this.texto);
		List<Busqueda> resultados = repoBusqueda.buscarBusquedaPorFecha(dateMin , dateMax);
		setItems(resultados);
	}
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getFechame() {
		return fechame;
	}
	public void setFechame(String fechame) {
		this.fechame = fechame;
	}
	public String getFechama() {
		return fechama;
	}
	public void setFechama(String fechama) {
		this.fechama = fechama;
	}
	
}
