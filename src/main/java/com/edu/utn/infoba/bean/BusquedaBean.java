package com.edu.utn.infoba.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.mongodb.morphia.Datastore;

import com.edu.utn.infoba.ApiRestBancos;
import com.edu.utn.infoba.ApiRestCgp;
import com.edu.utn.infoba.FuenteDeDatos;
import com.edu.utn.infoba.MailSender;
import com.edu.utn.infoba.MockMailSender;
import com.edu.utn.infoba.OrigenDeDatosDummy;
import com.edu.utn.infoba.modelo.PuntoDeInteres;
import com.edu.utn.infoba.repositorios.Repositorio;
import com.edu.utn.utils.MongoDBConnection;
import com.edu.utn.utils.PoisMongoDB;
import com.edu.utn.utils.PoisMySQL;

@ManagedBean
public class BusquedaBean {
	
	private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
	private Repositorio repositorio;
	private MailSender mailSender;
	private FuenteDeDatos fuente;
	private List<PuntoDeInteres> pois;
	private String criterio;

	public BusquedaBean() {

		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		mailSender = new MockMailSender();
		Datastore datastore =  MongoDBConnection.getInstance().getDatastore();
		PoisMongoDB poisMongo = new PoisMongoDB(datastore);
		repositorio = new Repositorio(emFactory.createEntityManager(),datastore, mailSender);

		fuente = new FuenteDeDatos(new OrigenDeDatosDummy(),new PoisMySQL(emFactory.createEntityManager()));
		repositorio.getPuntosDeInteres().agregarFuenteDeDatos(fuente);
		repositorio.getPuntosDeInteres().agregarFuenteDeDatos(new FuenteDeDatos(new ApiRestBancos(), poisMongo));
		repositorio.getPuntosDeInteres().agregarFuenteDeDatos(new FuenteDeDatos(new ApiRestCgp(), poisMongo));
	}

	public List<PuntoDeInteres> getPois() {
		return pois;
	}

	public void setPois(List<PuntoDeInteres> pois) {
		this.pois = pois;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public void buscar() {
		FacesContext context = FacesContext.getCurrentInstance();
		String user = (String) context.getExternalContext().getSessionMap().get("user");
		System.out.println(this.criterio);
		List<PuntoDeInteres> resultado = repositorio.getPuntosDeInteres().buscarPuntoDeInteresPorTexto(this.criterio,user);
		setPois(resultado);
	}
	
	public String historial() {
		return "historial.xhtml?faces-redirect=true";
	}
	
	public String acciones() {
		return "accionesConsulta.xhtml?faces-redirect=true";
	}
}
