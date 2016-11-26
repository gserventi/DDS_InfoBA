package com.edu.utn.infoba;

import java.util.ArrayList;
import java.util.List;

import com.edu.utn.infoba.modelo.PuntoDeInteres;
import com.edu.utn.infoba.modelo.Comercio;
import com.edu.utn.infoba.modelo.Tag;
import com.edu.utn.utils.Configuracion;
import com.edu.utn.utils.DataBase;

import java.io.*;

public class FuenteDeDatos
{
	private OrigenDeDatos origenDeDatos;
	private DataBase dataBase;
	private static final String separador = ";";
	
	public FuenteDeDatos(OrigenDeDatos odd,DataBase dataBasen)
    {
		origenDeDatos = odd;
		dataBase = dataBasen;
	}
	

	public void agregarPuntoDeInteres(PuntoDeInteres puntoDeInteres)
	{
		dataBase.persistirPuntoDeInteres(puntoDeInteres);
	}
	
	public void eliminarPuntoDeInteres(PuntoDeInteres puntoDeInteres)
	{
		dataBase.eliminarPuntoDeInteres(puntoDeInteres);
	}

    public void bajaPuntoDeInteres(PuntoDeInteres puntoDeInteres)
    {
        dataBase.bajaPuntoDeInteres(puntoDeInteres);
    }

	public void modificarPuntoDeInteres(PuntoDeInteres puntoDeInteres)
	{
		dataBase.modificarPuntoDeInteres(puntoDeInteres);
	}
	
	public List<PuntoDeInteres> buscarPuntoDeInteresPorTexto(String texto)
	{
		List<PuntoDeInteres> resultado = dataBase.buscarPuntoDeInteresPorTexto(texto);
		if(resultado.size() == 0){
			resultado = origenDeDatos.consultar(texto);
			for(PuntoDeInteres poi : resultado)
			dataBase.persistirPuntoDeInteres(poi);
		}
		return resultado;
	}
	
	public void bajaDePOIs(List<String> textos) throws Exception
	{
		List<PuntoDeInteres> resultado;
		List<PuntoDeInteres> poisABorrar = new ArrayList<>();

		for(String texto : textos)
		{
			resultado = this.buscarPuntoDeInteresPorTexto(texto);

            if(resultado.size() == 0)
			{
				throw new Exception("Not Found:" + texto);
			}

			if(resultado.size() > 1)
			{
				throw new Exception("Not Unique:" + texto);
			}

			poisABorrar.addAll(resultado);
		}

        this.dataBase.borradoFisico(poisABorrar);
		//this.borradoFisico(poisABorrar);
	}

	public PuntoDeInteres buscarPuntoDeInteresPorNombre(String texto)
    {
		return dataBase.buscarPuntoDeInteresPorNombre(texto);
	}
	
	public void actualizarLocales() throws IOException
    {
		Configuracion config = new Configuracion();
        String separator = File.separator;
        String userDir = System.getProperty("user.dir");
        String archivoActualizacion = config.obtenerPropiedad("archivoActualizacion");
		String archivo =  userDir + separator + "src" + separator + archivoActualizacion;
		BufferedReader br = null;
		PuntoDeInteres poi;
			
		try
        {
			br = new BufferedReader(new FileReader(archivo));
			String line = br.readLine();
			while (null != line)
            {
				String [] campos = line.split(this.separador);

				poi = this.buscarPuntoDeInteresPorNombre(campos[0]);

				if (poi == null)
				{
					Comercio comercio = new Comercio(campos[0],null,null,null);
					this.agregarPuntoDeInteres(comercio);

					poi = this.buscarPuntoDeInteresPorNombre(campos[0]);

                    if(poi != null)
                    {
                       String[] tagCollection = campos[1].split(" ");

                       for (String tagS : tagCollection)
                       {
                            Tag ntag = new Tag(tagS);
                            poi.agregarTag(ntag);
                       }

                       this.dataBase.modificarPuntoDeInteres(poi);
                    }
				}
				else
                {
					poi.limpiarTags();

                    for (String tagS : campos[1].split(" "))
					{
                        Tag newTag = new Tag(tagS);
						poi.agregarTag(newTag);
					}

					this.dataBase.persistirPuntoDeInteres(poi);
				}

				line = br.readLine();
			}
		}
		catch (IOException e)
        {
			e.printStackTrace();
		}
		finally
        {
		  if (null!=br)
		  {
              try
              {
                  br.close();
			  }
			  catch(IOException e)
              {
                  e.printStackTrace();
			  }
          }
		}
	}


	/*
	public void cargaInicial(){
		ArrayList<PuntoDeInteres> pois = proxy.leerDatos();
		for(PuntoDeInteres poi : pois){
			this.agregarPuntoDeInteres(poi);
		}
	}*/

}