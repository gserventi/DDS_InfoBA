package com.edu.utn.infoba.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "usuario")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@NamedQueries({
	@NamedQuery(name="buscarUsuarioPorNombre",query="SELECT u FROM Usuario u WHERE u.nombre LIKE :unombre"),
	@NamedQuery(name="buscarUsuarioPorTipoYNombre",query="SELECT u FROM Usuario u WHERE u.nombre LIKE :unombre AND DTYPE LIKE :utipo")
})
public class Usuario extends Persistible
{
	
	private String nombre;
	private String password;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
    private Set<AccionesUsuario> acciones;
    
	public Usuario() {}
	
	public Usuario(String nombre, String password) {
		this.nombre = nombre;
		this.password = password;
		this.acciones = new HashSet<>();
	}
	
	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<AccionesUsuario> getAcciones() {
	   return this.acciones;
	}
	
	public void setAcciones(Set<AccionesUsuario> acciones) {
		this.acciones = acciones;
	}
	
	public void agregarAccion(AccionesUsuario accion) {
		if (acciones == null) {
			acciones = new HashSet<>();
		}
		if (!acciones.contains(accion))
			acciones.add(accion);
	}
	
	public void removerAccion(AccionesUsuario accion) {
		if(acciones.contains(accion))
			acciones.remove(accion);
	}
	
	public void limpiarAcciones() {
	   acciones.clear();
	}
	
	public Boolean puedeRealizarAccion(AccionesUsuario acc)
	{
		return acciones.contains(acc);
	}
}
