package com.edu.utn.infoba;

import java.util.ArrayList;

import com.edu.utn.infoba.modelo.PuntoDeInteres;
import com.edu.utn.utils.Configuracion;
import com.edu.utn.utils.UtilidadesRest;

public class ApiRestBancos implements OrigenDeDatos
{
	private String apiURL;

	public ApiRestBancos()
    {
		String query = "banco";
		Configuracion config = new Configuracion();
		String urlComponente = config.obtenerPropiedad("consultasURL");
		apiURL = urlComponente + query;
	}


	public ArrayList<PuntoDeInteres> leerDatos()
    {
		String bancosJSON = UtilidadesRest.leerDeUrl(apiURL);
        return UtilidadesRest.obtenerListaBancos(bancosJSON);
	}


    public ArrayList<PuntoDeInteres> consultar(String query)
    {
        String bancosJSON = UtilidadesRest.leerDeUrl(apiURL + "?banco=" +query);
        return UtilidadesRest.obtenerListaBancos(bancosJSON);
 
    }

}
