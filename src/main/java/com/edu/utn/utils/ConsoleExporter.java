package com.edu.utn.utils;

import java.util.HashSet;

public class ConsoleExporter implements ReportExporter
{
    private static final String DEFAULT_SEPARATOR = " - ";

    public void export(HashSet<String> Columnas, HashSet<FilaReporte> Filas)
    {
        System.out.println(prepararEncabezado(Columnas, DEFAULT_SEPARATOR));
        System.out.println("------------------------------------------");

        for (FilaReporte fila: Filas)
        {
            System.out.println(fila.convertToText(DEFAULT_SEPARATOR));
        }

        System.out.println("\n\n");
    }

    private String prepararEncabezado(HashSet<String> Columnas, String separador)
    {
        String encabezado = "";
        Integer count = 0;
        Integer size = Columnas.size();

        for(String str : Columnas)
        {
            count++;
            encabezado+= str;

            if(size != count)
                encabezado+= separador;
        }

        return encabezado;
    }
}
