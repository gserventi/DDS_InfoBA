package com.edu.utn.infoba.test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.edu.utn.infoba.MailSender;
import com.edu.utn.infoba.modelo.AccionesUsuario;
import com.edu.utn.infoba.modelo.Usuario;
import com.edu.utn.infoba.repositorios.Repositorio;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPersisteUsuario
{
	private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
	private Repositorio repositorio;
	private MailSender mailSender;
	
	@Before
	public void setUp() throws Exception
    {
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		repositorio = new Repositorio(emFactory.createEntityManager(),mailSender);
	}
	
	@Test
	public void altaDeUsuario()
	{
		Usuario u1 = new Usuario();
		u1.setNombre("user1");
		u1.setPassword("pass1");
		AccionesUsuario a1 = new AccionesUsuario();
		a1.setNombre("accion1");
		u1.agregarAccion(a1);
		repositorio.usuarios().persistir(u1);	
	}
	
	@Test
	public void altaRecuperar() {
		assertTrue("No se encuentra el usuario user1", repositorio.usuarios().existeUsuario("user1"));
		assertEquals("La password de user1 es incorrecta", repositorio.usuarios().getPasswordDe("user1"),"pass1");
	}
	
	@Test
	public void modificarNombre() {
		repositorio.usuarios().modificarNombre("user1", "user2");
	}
	
	@Test
	public void modificarRecuperar() {
		assertTrue("No se encuentra el usuario user2", repositorio.usuarios().existeUsuario("user2"));
		assertEquals("La password de user2 es incorrecta", repositorio.usuarios().getPasswordDe("user2"),"pass1");
	}
	
	@After
	public void tearDown() throws Exception {
		repositorio.cerrar();
		emFactory.close();
	}
}
