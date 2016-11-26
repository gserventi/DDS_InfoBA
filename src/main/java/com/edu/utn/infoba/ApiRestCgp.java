package com.edu.utn.infoba;

import java.util.ArrayList;

import com.edu.utn.infoba.modelo.PuntoDeInteres;
import com.edu.utn.utils.Configuracion;
import com.edu.utn.utils.UtilidadesRest;

public class ApiRestCgp implements OrigenDeDatos
{
	private String apiURL;

	public ApiRestCgp()
	{
		String query = "centro";
		Configuracion config = new Configuracion();
		String urlComponente = config.obtenerPropiedad("consultasURL");
		apiURL = urlComponente + query;
	}


	public ArrayList<PuntoDeInteres> leerDatos()
	{
        String centrosJSON = UtilidadesRest.leerDeUrl(apiURL);
        return UtilidadesRest.obtenerListaCentros(centrosJSON);
	}

    public ArrayList<PuntoDeInteres> consultar(String query)
    {
        String centrosJSON = UtilidadesRest.leerDeUrl(apiURL + "?zona=" + query);
        return UtilidadesRest.obtenerListaCentros(centrosJSON);
    }


}
