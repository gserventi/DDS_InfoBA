package com.edu.utn.infoba.repositorios;

import com.edu.utn.infoba.MailSender;
import com.edu.utn.infoba.modelo.AccionesUsuario;
import com.edu.utn.infoba.modelo.Usuario;

import java.util.List;
import javax.persistence.EntityManager;

public class RepositorioUsuarios {
	protected EntityManager em;
	
	public RepositorioUsuarios(EntityManager em, MailSender mailSender) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> buscarUsuarioPorNombre(String nombre) {
		List<Usuario> usuarios;
		usuarios = em.createNamedQuery("buscarUsuarioPorNombre").setParameter("unombre", "%" + nombre + "%").getResultList();
		return usuarios;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> buscarUsuarioPorTipoYNombre(String nombre, String tipo) {
		List<Usuario> usuarios;
		usuarios = em.createNamedQuery("buscarUsuarioPorTipoYNombre").setParameter("unombre", "%" + nombre + "%").setParameter("utipo", "%" + tipo + "%").getResultList();
		return usuarios;
	}

	
	public boolean existeUsuario(String nombre) {
		return !buscarUsuarioPorNombre(nombre).isEmpty();
    }
	
	public boolean esAdministrador(String nombre) {
		return !buscarUsuarioPorNombre(nombre).isEmpty();
	}
	
	public String getPasswordDe(String nombre) {
		String password = null;
		if (existeUsuario(nombre)) {
			password = buscarUsuarioPorNombre(nombre).get(0).getPassword();
		}
		return password;
	}
	
	public void modificarNombre(String nombreViejo, String nombreNuevo) {
		List<Usuario> usuarios;
		if (existeUsuario(nombreViejo)) {
			usuarios = buscarUsuarioPorNombre(nombreViejo);
			for (Usuario u : usuarios) {
				u.setNombre(nombreNuevo);
				persistir(u);
			}
		}
	}
	
	public void modificarPassword(String nombre, String password) {
		List<Usuario> usuarios;
		if (existeUsuario(nombre)) {
			usuarios = buscarUsuarioPorNombre(nombre);
			for (Usuario u : usuarios) {
				u.setPassword(password);
				persistir(u);
			}
		}
	}
	
	public void persistir(Usuario usuario) {
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
	}
	
	public void eliminar(String nombre) {
		if (existeUsuario(nombre)) {
			List<Usuario> usuarios = buscarUsuarioPorNombre(nombre);
			em.getTransaction().begin();
			for (Usuario usuario : usuarios) {
				em.remove(usuario);
			}
			em.getTransaction().commit();
		}
	}
	
	public Boolean PuedeRealizarAccion(String usr, AccionesUsuario acc)
	{
		return this.buscarUsuarioPorNombre(usr).iterator().next().puedeRealizarAccion(acc);
	}
}
