package com.edu.utn.utils;

import java.util.LinkedHashSet;

public class FilaReporte
{
    private LinkedHashSet<String> datos;

    public FilaReporte(LinkedHashSet<String> datos)
    {
        this.datos = datos;
    }

    public String convertToText(String separador)
    {
       String valor = "";

       Integer count = 0;
       Integer size = this.datos.size();

       for(String str : this.datos)
        {
            count++;
            valor+= str;

            if(size != count)
                valor+= separador;
        }

        return valor;
    }
}