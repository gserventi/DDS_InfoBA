package com.edu.utn.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.edu.utn.infoba.modelo.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class UtilidadesRest
{

	public static ArrayList<PuntoDeInteres> obtenerListaBancos(String objetoJSON) {

		// Parsear bancos

		ArrayList<PuntoDeInteres> ListaPOI = new ArrayList<>();

		JSONArray arr = new JSONArray(objetoJSON);

		for (int i = 0; i < arr.length(); i++) {
			JSONObject POIJson = arr.getJSONObject(i);

			String nombre = POIJson.getString("banco");
			String sucursal = POIJson.getString("sucursal");
			String gerente = POIJson.getString("gerente");

			Double x = POIJson.getDouble("x");
			Double y = POIJson.getDouble("y");

			Coordenadas coordenadas = new Coordenadas(x, y);
			Banco banco = new Banco(nombre, null, coordenadas);
			banco.setGerente(gerente);
			banco.setSucursal(sucursal);

			if (POIJson.has("servicios")) {
				JSONArray servicios = POIJson.getJSONArray("servicios");
				for (int p = 0; p < servicios.length(); p++) {
					String _servicioNombre = servicios.getString(p);
					Servicio servicio = new Servicio(_servicioNombre);
					banco.agregarServicio(servicio);
				}
			}

			ListaPOI.add(banco);

		}

		return ListaPOI;
	}

	public static ArrayList<PuntoDeInteres> obtenerListaCentros(String objetoJSON) {

		// Parsear CGPs

		ArrayList<PuntoDeInteres> ListaPOI = new ArrayList<PuntoDeInteres>();

		JSONArray arr = new JSONArray(objetoJSON);

		for (int i = 0; i < arr.length(); i++) {
			JSONObject POIJson = arr.getJSONObject(i);

			int comuna = POIJson.getInt("comuna");
			String director = POIJson.getString("director");
			String domicilio = POIJson.getString("domicilio");
			String telefono = POIJson.getString("telefono");

			// String zonasString = POIJson.getString("zonas");
			// ArrayList<String> zonas = new
			// ArrayList<>(Arrays.asList(zonasString.split(",")));

			JSONArray serviciosJSON = POIJson.getJSONArray("servicios");

			for (int p = 0; p < serviciosJSON.length(); p++) {
				JSONObject servicioJSON = serviciosJSON.getJSONObject(p);

				String nombreServicio = servicioJSON.getString("nombre");

				JSONArray horarios = servicioJSON.getJSONArray("horarios");

				Servicio servicio = new Servicio(nombreServicio);

				for (int q = 0; q < horarios.length(); q++) {
					JSONObject horarioJSON = serviciosJSON.getJSONObject(q);

					boolean isValid = horarioJSON.has("diaSemana") && horarioJSON.has("horaDesde")
							&& horarioJSON.has("minutosDesde") && horarioJSON.has("horaHasta")
							&& horarioJSON.has("minutosHasta");

					if (isValid) {
						int dia = horarioJSON.getInt("diaSemana");
						int horaDesde = horarioJSON.getInt("horaDesde");
						int minutosDesde = horarioJSON.getInt("minutosDesde");
						int horaHasta = horarioJSON.getInt("horaHasta");
						int minutosHasta = horarioJSON.getInt("minutosHasta");

						HorarioServicio horario = new HorarioServicio(dia, horaDesde, minutosDesde, horaHasta,
								minutosHasta);

						servicio.agregarHorarioServicio(horario);
					}
				}
			}

			CGP centro = new CGP(comuna, director, domicilio, telefono);
			// TODO Arreglar el seteo de zonas
			// centro.setZona(zonas);

			ListaPOI.add(centro);
		}

		return ListaPOI;
	}

	public static String leerDeUrl(String url)
	{
		String respuesta = "";

		try
		{
			URL restAPIURL = new URL(url);
			URLConnection yc = restAPIURL.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				respuesta += inputLine;
			}

			in.close();
		} catch (Exception e) {
			// Ocurrio una excepción de algun tipo.
			System.out.print(e.toString());
			return null;
		}

		return respuesta;

	}
}