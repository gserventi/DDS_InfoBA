package com.edu.utn.infoba.repositorios;

import javax.persistence.EntityManager;

import org.mongodb.morphia.Datastore;

import com.edu.utn.infoba.MailSender;

public class Repositorio {
	protected EntityManager em;
	private Datastore ds;
	private MailSender mailSender;

	private RepositorioUsuarios usuarios;
	private RepositorioAccionesUsuario accionesUsuario;
	private RepositorioPois puntosDeInteres;
	//private RepositorioBusqueda busquedas;

	public Repositorio(EntityManager em, Datastore ds, MailSender mailSender) {
		this.em = em;
		this.ds = ds;
		this.usuarios = new RepositorioUsuarios(em, mailSender);
		this.accionesUsuario = new RepositorioAccionesUsuario(em);
		this.puntosDeInteres = new RepositorioPois(new RepositorioBusqueda(ds, mailSender), em);
		//this.busquedas = new RepositorioBusqueda(ds, mailSender);
	}
	
	public Repositorio(EntityManager em, MailSender mailSender) {
		this.em = em;
		this.usuarios = new RepositorioUsuarios(em, mailSender);
		this.accionesUsuario = new RepositorioAccionesUsuario(em);
		this.puntosDeInteres = new RepositorioPois(new RepositorioBusqueda(ds, mailSender), em);
		//this.busquedas = new RepositorioBusqueda(ds, mailSender);
	}
	
	public Repositorio(Datastore ds, MailSender mailSender) {
		this.ds = ds;
		this.usuarios = new RepositorioUsuarios(em, mailSender);
		this.accionesUsuario = new RepositorioAccionesUsuario(em);
		this.puntosDeInteres = new RepositorioPois(new RepositorioBusqueda(ds, mailSender), em);
		//this.busquedas = new RepositorioBusqueda(ds, mailSender);
	}

	public RepositorioUsuarios usuarios() {
		if (usuarios == null) {
			usuarios = new RepositorioUsuarios(em, mailSender);
		}
		return usuarios;
	}

	public RepositorioAccionesUsuario accionesUsuario() {
		if (accionesUsuario == null) {
			accionesUsuario = new RepositorioAccionesUsuario(em);
		}
		return accionesUsuario;
	}

	public RepositorioPois getPuntosDeInteres() {
		return puntosDeInteres;
	}

	public void setPuntosDeInteres(RepositorioPois puntosDeInteres) {
		this.puntosDeInteres = puntosDeInteres;
	}

	public RepositorioBusqueda getBusquedas() {
		return this.puntosDeInteres.getRepoBusqueda();
	}

	public void setBusquedas(RepositorioBusqueda busquedas) {
		this.puntosDeInteres.setRepoBusqueda(busquedas);
	}

	public void cerrar() {
		em.close();
	}
}
