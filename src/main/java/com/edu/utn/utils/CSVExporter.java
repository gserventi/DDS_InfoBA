package com.edu.utn.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class CSVExporter implements ReportExporter
{
    private static final String DEFAULT_SEPARATOR = ",";
    private String outputFile;

    public CSVExporter(String outputFile)
    {
        this.outputFile = outputFile;
    }

    public void export(HashSet<String> Columnas, HashSet<FilaReporte> Filas)
    {
        File f = new File(this.outputFile);

        if(!f.exists())
        {
            try
            {
                f.createNewFile();
                f.setWritable(true);

                FileWriter writer = new FileWriter(f);
                String header = prepararEncabezado(Columnas, DEFAULT_SEPARATOR);
                writer.write(header);
                writer.write("\n");


                for (FilaReporte fila: Filas)
                {
                    writer.write(fila.convertToText(DEFAULT_SEPARATOR));
                    writer.write("\n");
                }

                writer.flush();
            }
            catch(IOException e)
            {
                if(f.exists())
                    f.delete();

                // throw new Exception("ExporterException");
            }
        }

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
