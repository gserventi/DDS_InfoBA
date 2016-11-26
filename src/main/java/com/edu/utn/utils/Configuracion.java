package com.edu.utn.utils;

import java.io.*;
import java.util.Properties;

public class Configuracion
{
   private String configFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "config.properties";

   public String obtenerPropiedad(String nombre)
   {
       String result = "";
       InputStream inputStream = null;

        try
        {
            Properties prop = new Properties();
            inputStream = new FileInputStream(this.configFilePath);
            prop.load(inputStream);

            // get the property value and print it out
            result = prop.getProperty(nombre);
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }
        finally
        {
            try
            {
                assert inputStream != null;
                inputStream.close();
            }
            catch (IOException e) {return null;}
        }

        return result;
   }

   //Intento de pisar los datos del config
   public void cambiarPropiedades(String nombre ,String valor) 
   {
	   Properties prop = new Properties();
	   File f = new File("config.properties");
	   if(f.exists())
	   {
		  try
          {
              prop.load(new FileInputStream("config.properties"));
          }
          catch (IOException e)
          {
			  // TODO Auto-generated catch block
              e.printStackTrace();
		  }

		  prop.setProperty(nombre, valor);

          try
          {
			prop.store(new FileOutputStream("config.properties"), null);
          }
          catch (IOException e)
          {
					// TODO Auto-generated catch block
					e.printStackTrace();
		  }
		  
       }
   }

}
