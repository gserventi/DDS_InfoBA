package com.edu.utn.infoba;

import java.util.ArrayList;

public class Usuario
{
	private String nombre;
	private String password;
    private ArrayList<String> acciones;

	public Usuario(String nombre, String password)
	{
		this.nombre = nombre;
		this.password = password;
		this.acciones = new ArrayList<>();
	}

	public String getNombre()
	{
		return this.nombre;
	}

	public String getPassword()
	{
		return this.password;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}

	public ArrayList<String> getAcciones()
	{
	   return this.acciones;
	}

	public void agregarAccion(String accion)
	{
	if(!this.acciones.contains(accion))
			this.acciones.add(accion);
	}

	public void removerAccion(String accion)
	{
		if(this.acciones.contains(accion))
			this.acciones.remove(accion);
	}

	public void limpiarAcciones()
	{
	   this.acciones.clear();
	}
}
