package com.edu.utn.utils;

import java.io.*;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServicioRestBaja {
	
	private static String parametro = "ServicioBajas";

	public ServicioRestBaja()
	{
		
	}
	
	public List<String> obtenerListaBajas() throws IOException, ParseException{
		String json = this.obtenerJson();
		return this.procesarJson(json);
	}

	public String obtenerJson() throws IOException
	{
		Configuracion config = new Configuracion();
		String archivo = System.getProperty("user.dir") + File.separator + "src" + File.separator + config.obtenerPropiedad(parametro);
		return new String(Files.readAllBytes(FileSystems.getDefault().getPath(archivo)));
	}
	
	public List<String> procesarJson(String jsonString) throws ParseException{
		
		 JSONArray arr = new JSONArray(jsonString);
		 List<String> bajas = new ArrayList<>();

	        for (int i = 0; i < arr.length(); i++)
	        {
	            JSONObject jsonBaja = arr.getJSONObject(i);

	            String texto = jsonBaja.getString("Texto");
	            String fechaTexto = jsonBaja.getString("Fecha");
	            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	            Date fechaBaja = formatter.parse(fechaTexto);
	            Date fechaActual = new Date(System.currentTimeMillis());
	            if(fechaBaja.before(fechaActual))
	            {
	            	bajas.add(texto);
	            }
	        }
	        return bajas;
	}

	static public String getParametro() {
		return parametro;
	}

	static public void setParametro(String par) {
		parametro = par;
	}
}

