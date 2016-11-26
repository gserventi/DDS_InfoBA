package com.edu.utn.infoba.modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="Terminal")
public class Terminal extends Usuario
{
	public Terminal(String nombre, String password) {
		super(nombre, password);
	}
	public Terminal() {
	}
}
