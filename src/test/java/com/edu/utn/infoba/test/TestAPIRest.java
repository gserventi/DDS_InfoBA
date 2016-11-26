package com.edu.utn.infoba.test;

import com.edu.utn.infoba.*;
import com.edu.utn.infoba.modelo.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

public class TestAPIRest
{

    @Before
    public void setUp() throws Exception
    {

    }

    @Test
    public void testServicioBancos()
    {
        ApiRestBancos apiRest = new ApiRestBancos();
        ArrayList<PuntoDeInteres> Bancos = apiRest.leerDatos();
        ArrayList<PuntoDeInteres> Bancos2 = apiRest.consultar("Galicia");

        assertNotNull(Bancos);
        assertNotNull(Bancos2);
    }

    @Test
    public void testServicioCentros()
    {
        ApiRestCgp apiRest = new ApiRestCgp();
        ArrayList<PuntoDeInteres> Centros = apiRest.leerDatos();
        ArrayList<PuntoDeInteres> Centros2 = apiRest.consultar("Boedo");

        assertNotNull(Centros);
        assertNotNull(Centros2);
    }

}
