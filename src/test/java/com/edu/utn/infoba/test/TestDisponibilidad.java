package com.edu.utn.infoba.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.edu.utn.infoba.modelo.*;
import org.junit.Before;
import org.junit.Test;

public class TestDisponibilidad
{
	private Banco banco;
	private CGP cgp;
	private Comercio comercio;
	private ParadaColectivos colectivo;
	private HorarioServicio disponi ;
	private HorarioServicio disponi1 ;
	private HorarioServicio disponi2 ;
	private Servicio serv1 ;
	private Servicio serv2 ;
	private Servicio serv3 ;

	private HorarioServicio disponi3;
	private HorarioServicio disponi4;
	private HorarioServicio disponi5;
	private HorarioComercio disponi6;
	private Servicio serv4;
	private Servicio serv5;
	private Servicio serv6;
	private Date tiempo1;
	private Date tiempo2;
	private Date tiempo3;
	private Date tiempo4;
	private Date tiempo5;
	private Date tiempoM;

	@Before
	public void setUp() throws Exception
	{
		
		colectivo = new ParadaColectivos("7y101",null,new Coordenadas(2.0,13.0));

		Coordenadas coordenadas = new Coordenadas(2.0,13.0);
		
		cgp = new CGP("Comuna10",null, coordenadas);
		
		disponi =  new HorarioServicio(3, 11, 0 , 19, 30);
		disponi1 =  new HorarioServicio(6, 11, 0 , 19, 30);
		disponi2 =  new HorarioServicio(1, 10, 0, 15, 0);
		
		serv1 = new Servicio("Rentas");
		cgp.agregarServicio(serv1);
		serv1.agregarHorarioServicio(disponi);
		
		serv2 = new Servicio("Pagos");
		cgp.agregarServicio(serv2);
		serv2.agregarHorarioServicio(disponi1);
		
		serv3 = new Servicio("Tramites");
		cgp.agregarServicio(serv3);
		serv3.agregarHorarioServicio(disponi2);
		
		
		banco = new Banco("ITAU",null,new Coordenadas(1.0, 10.0));
		disponi3 =  new HorarioServicio(4, 10, 0 , 15, 0);
		disponi4 =  new HorarioServicio(3, 10, 0 , 15, 0);
		disponi5 =  new HorarioServicio(4, 10, 0 , 11, 30);
		
		serv4 = new Servicio("Atencion ciudadana");
		serv4.agregarHorarioServicio(disponi3);
		banco.agregarServicio(serv4);
		
		serv5 = new Servicio("Pagos");
		serv5.agregarHorarioServicio(disponi4);
		banco.agregarServicio(serv5);
		
		serv6 = new Servicio("Tramites");
		serv6.agregarHorarioServicio(disponi5);
		banco.agregarServicio(serv6);
		
		comercio = new Comercio("LosMuebles",null,new Coordenadas(15.0, 55.0),new Rubro("Muebleria", 200.0));
		disponi6 =  new HorarioComercio(5, 8, 0 , 19, 30);
		comercio.agregarHorarioComercio(disponi6);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "15-06-2016 10:20:56";
		tiempo1 = sdf.parse(dateInString);
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString1 = "23-06-2016 11:00:56";
		tiempo2 = sdf1.parse(dateInString1);
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString3 = "22-06-2016 17:20:56";
		tiempo3 = sdf3.parse(dateInString3);
		
		SimpleDateFormat sdf4 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString4 = "06-06-2016 10:20:56";
		tiempo4 = sdf4.parse(dateInString4);
		
		SimpleDateFormat sdf5 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString5 = "24-06-2016 11:20:56";
		tiempo5 = sdf5.parse(dateInString5);
		
		SimpleDateFormat sdf6 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString6 = "24-06-2016 23:20:56";
		tiempoM = sdf6.parse(dateInString6);
	}

	@Test
	public void testBanco()
	{
		assertTrue("Falló disponibilidad de Banco con servicio",banco.disponibilidad(tiempo2, serv4));
		assertTrue("Falló disponibilidad de Banco con servicio null ",banco.disponibilidad(tiempo2, null));
		assertFalse("Falló disponibilidad falsa por servicio no diponible",banco.disponibilidad(tiempoM, null));
	}

	@Test
	public void testCGP()
	{
		
		assertTrue("Falló disponibilidad de CGP con servicio",cgp.disponibilidad(tiempo3, serv1));
		assertTrue("Falló disponibilidad de CGP con servicio null",cgp.disponibilidad(tiempo4, null));
		assertFalse("Falló disponibilidad falsa por servicio no diponible",cgp.disponibilidad(tiempoM, null));
	}
	
	@Test
	public void testComercio()
	{
		assertTrue("Falló disponibilidad de Comercio",comercio.disponibilidad(tiempo5, null));
	}
	
	@Test
	public void testColectivo()
	{
		assertTrue("Falló el calculo de la diponibilidad en colectivo",colectivo.disponibilidad(tiempo1, null));
	}
	
}
