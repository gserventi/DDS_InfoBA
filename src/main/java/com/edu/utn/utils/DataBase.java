package com.edu.utn.utils;

import java.util.List;

import com.edu.utn.infoba.modelo.PuntoDeInteres;

public interface DataBase
{
	List<PuntoDeInteres> buscarPuntoDeInteresPorTexto(String texto);
	void borradoFisico(List<PuntoDeInteres> puntosDeInteres);
	PuntoDeInteres buscarPuntoDeInteresPorNombre(String texto);
	void persistirPuntoDeInteres(PuntoDeInteres puntoDeInteres);
	void eliminarPuntoDeInteres(PuntoDeInteres puntoDeInteres);
    void bajaPuntoDeInteres(PuntoDeInteres puntoDeInteres);
	void modificarPuntoDeInteres(PuntoDeInteres puntoDeInteres);
}
