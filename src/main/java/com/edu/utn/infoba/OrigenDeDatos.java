package com.edu.utn.infoba;

import java.util.ArrayList;
import com.edu.utn.infoba.modelo.PuntoDeInteres;

public interface OrigenDeDatos
{
	public abstract  ArrayList<PuntoDeInteres> leerDatos();
	public abstract ArrayList<PuntoDeInteres> consultar(String query);
}
