package com.edu.utn.infoba;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.HashMap;

import com.edu.utn.infoba.repositorios.RepositorioUsuarios;


public class ManagerAutenticacion
{
	//private RepositorioUsuario repoUsuario;
	private RepositorioUsuarios repositorioUsuarios;
	private HashMap<String,String> tokenUsuarios;
	
	//public ManagerAutenticacion(RepositorioUsuario repoUsuarioH)
	public ManagerAutenticacion(RepositorioUsuarios repoUsuarioH)
	{
		repositorioUsuarios = repoUsuarioH;
		tokenUsuarios = new HashMap<>();
	}
	
	public String iniciarSesion(String nombre, String password) throws NoSuchAlgorithmException, NoSuchProviderException
	{
		String token=null;
		if (repositorioUsuarios.existeUsuario(nombre)) {
			if (repositorioUsuarios.getPasswordDe(nombre).equals(password))  {
				token=calcularToken(nombre,repositorioUsuarios.getPasswordDe(nombre));
				tokenUsuarios.put(nombre, token);
			}
		}
		return token;
	}
	
	public void cerrarSesion(String nombre) {
		if ( tokenUsuarios.containsKey(nombre) ) {
			tokenUsuarios.remove(nombre);
		}
	}
	
	public String calcularToken(String nombre,String password) throws NoSuchAlgorithmException, NoSuchProviderException {
		nombre+=password;
		SecureRandom sr=SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(salt);
		byte[] bytes = md.digest(nombre.getBytes());
		StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
		return sb.toString();
	}
	
	public boolean validarToken(String nombre,String token) {
		boolean resultado = false;
		if ( tokenUsuarios.containsKey(nombre) ) {
			resultado=tokenUsuarios.get(nombre).equals(token);
		}
		else
			resultado=false;
		return resultado;
	}
}
