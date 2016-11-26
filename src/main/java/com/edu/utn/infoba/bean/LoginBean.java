package com.edu.utn.infoba.bean;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.primefaces.context.RequestContext;

import com.edu.utn.infoba.*;
import com.edu.utn.infoba.repositorios.Repositorio;

@ManagedBean(name = "login")
@SessionScoped
public class LoginBean
{

	private String usuario;
	private String contrasena;
	private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
	private Repositorio repositorio;
	private ManagerAutenticacion authManager;
	private MailSender mailSender;
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public String login() throws NoSuchAlgorithmException, NoSuchProviderException {
		String token = null;
		String redireccion = null;
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		repositorio = new Repositorio(emFactory.createEntityManager(), mailSender);
		authManager = new ManagerAutenticacion(repositorio.usuarios());	

		token = authManager.iniciarSesion(this.usuario, this.contrasena);
		
		if (token != null) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", this.usuario);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("token", token);
			redireccion="busqueda.xhtml?faces-redirect=true";
		}
		else {
			RequestContext.getCurrentInstance().update("growl");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o Contraseña no validos"));
		}
		
		return redireccion;
	}
	
	public void verificarSesion() {
		try {
			Boolean valida = false;
			FacesContext context = FacesContext.getCurrentInstance();
			String user = (String) context.getExternalContext().getSessionMap().get("user");
			String token = (String) context.getExternalContext().getSessionMap().get("token");

			if (user != null && token != null) {
				valida = authManager.validarToken(user, token);
			}
			if (!valida) {
				context.getExternalContext().redirect("permisos.xhtml");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void verificarSesionAdministrador() {
		try {
			Boolean valida = false;
			FacesContext context = FacesContext.getCurrentInstance();
			String user = (String) context.getExternalContext().getSessionMap().get("user");
			String token = (String) context.getExternalContext().getSessionMap().get("token");

			if (user != null && token != null) {
				valida = authManager.validarToken(user, token);
				if (repositorio.usuarios().buscarUsuarioPorTipoYNombre(user, "Administrador").isEmpty()) {
					valida = false;
				}
			}
						
			if (!valida) {
				context.getExternalContext().redirect("permisos.xhtml");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar() {
		setUsuario(null);
		setContrasena(null);
	}
	
}
