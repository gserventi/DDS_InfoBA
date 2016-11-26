package com.edu.utn.infoba.test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.edu.utn.infoba.MailSender;
import com.edu.utn.infoba.ManagerAutenticacion;
import com.edu.utn.infoba.modelo.AccionesUsuario;
import com.edu.utn.infoba.modelo.Usuario;
import com.edu.utn.infoba.repositorios.Repositorio;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUsuario {
	private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
	private Repositorio repositorio;
	private ManagerAutenticacion authManager;
	private MailSender mailSender;
	
	@Before
	public void setUp() throws Exception {
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		repositorio = new Repositorio(emFactory.createEntityManager(), mailSender);
		authManager = new ManagerAutenticacion(repositorio.usuarios());
	}
	
	@Test
	public void aPersistir() {
		Usuario u1 = new Usuario();
		u1.setNombre("user1");
		u1.setPassword("pass1");
		AccionesUsuario a1 = new AccionesUsuario();
		a1.setNombre("accion1");
		a1.setUsuario(u1);
		u1.agregarAccion(a1);
		repositorio.usuarios().persistir(u1);	
		
		Usuario u2 = new Usuario();
		u2.setNombre("user2");
		u2.setPassword("pass2");
		AccionesUsuario a2 = new AccionesUsuario();
		a2.setNombre("accion2");
		a2.setUsuario(u2);
		u2.agregarAccion(a2);
		repositorio.usuarios().persistir(u2);
	}
	
	@Test
	public void buscaUsuarioExistente() {
		assertTrue("No se encuentra el usuario user1", repositorio.usuarios().existeUsuario("user1"));
		assertTrue("No se encuentra el usuario user2", repositorio.usuarios().existeUsuario("user2"));
	}
	
	@Test
	public void buscaUsuarioNoExistente() {
		assertFalse("Se encontro al usuario inexistente user5", repositorio.usuarios().existeUsuario("user5"));
	}
	
	@Test
	public void calcularToken() throws NoSuchAlgorithmException, NoSuchProviderException {
		String token;
		token = authManager.calcularToken("user1", "pass1");
		System.out.println("Calcular Token para user1: " + token);
	}
	
	@Test
	public void eliminarUsuario() {
		repositorio.usuarios().eliminar("user2");
		assertFalse("Se encontro al usuario inexistente user2", repositorio.usuarios().existeUsuario("user2"));
	}
	
	@Test
	public void getPasswordDeUsuario() {
		assertEquals("La password de user1 es incorrecta", repositorio.usuarios().getPasswordDe("user1"),"pass1");
	}
	
	@Test
	public void iniciarSesion() throws NoSuchAlgorithmException, NoSuchProviderException {
		String token = authManager.iniciarSesion("user1", "pass1");
		System.out.println("INICIAR SESION: Token user1 pass1: "+ token + " *");
	}
	
	@Test
	public void validaTokenCorrecto() throws NoSuchAlgorithmException, NoSuchProviderException {
		String token = authManager.iniciarSesion("user1", "pass1");
		assertTrue("No se puedo validar el token de user1", authManager.validarToken("user1", token));
	}
	
	@Test
	public void validaTokenIncorrecto() throws NoSuchAlgorithmException, NoSuchProviderException {
		authManager.iniciarSesion("user2", "pass2");
		assertFalse("Se valido incorrectamente el token de user2", authManager.validarToken("user2", "1234567890"));
	}
	
	@Test
	public void vCerrarSesion() throws NoSuchAlgorithmException, NoSuchProviderException {
		String token = authManager.iniciarSesion("user1", "pass1");
		assertTrue("No existe el usuario user1", repositorio.usuarios().existeUsuario("user1"));
		assertTrue("No se pudo validar el token de user1", authManager.validarToken("user1", token));
		authManager.cerrarSesion("user1");
		assertFalse("Se valido el token de user1 despues de cerrar la sesion", authManager.validarToken("user1", token));
	}
	
	@After
	public void tearDown() throws Exception {
		repositorio.cerrar();
		emFactory.close();
	}
}
