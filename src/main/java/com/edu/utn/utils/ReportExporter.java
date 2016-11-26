package com.edu.utn.utils;

import java.util.HashSet;

public interface ReportExporter
{
    void export(HashSet<String> Columnas, HashSet<FilaReporte> Filas);
}