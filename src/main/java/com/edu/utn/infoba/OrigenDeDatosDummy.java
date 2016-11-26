package com.edu.utn.infoba;

import java.util.ArrayList;

import com.edu.utn.infoba.modelo.PuntoDeInteres;

public class OrigenDeDatosDummy implements OrigenDeDatos {

	@Override
	public ArrayList<PuntoDeInteres> leerDatos() {
		return new ArrayList<PuntoDeInteres>();
	}

	@Override
	public ArrayList<PuntoDeInteres> consultar(String query) {
		return new ArrayList<PuntoDeInteres>();
	}

}
