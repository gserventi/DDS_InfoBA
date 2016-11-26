package com.edu.utn.infoba.repositorios;

import javax.persistence.EntityManager;

public class RepositorioAccionesUsuario
{
	protected EntityManager em;
	
	RepositorioAccionesUsuario(EntityManager em)
	{
		this.em = em;
	}
}
