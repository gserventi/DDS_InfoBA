package com.edu.utn.infoba;

import java.util.HashSet;

import com.edu.utn.utils.Configuracion;

public class ReportesProcesos
{
	private HashSet<ResulProcesos> resulProcesos;
	private MailSender mailSender;
	private Configuracion config = new Configuracion();

	public ReportesProcesos(MailSender sender)
	{
		this.resulProcesos = new HashSet<>();
		this.mailSender = sender;
	}
	
	public void registrar(ResulProcesos resultado)
	{
		this.resulProcesos.add(resultado);

		if(Boolean.getBoolean(config.obtenerPropiedad("respuestaPorMail")))
		{
			notificarfalla(resultado);
		}
	}

	public void notificarfalla(ResulProcesos resultado )
	{
		if(config.obtenerPropiedad("respuestaPorMail").equals("true"))
		{
		   final String fromEmail = config.obtenerPropiedad("fromEmail"); //Email desde el cual se va a mandar
		   final String password = config.obtenerPropiedad("password"); // Pass del mail desde donde se va a mandar
		   final String toEmail = config.obtenerPropiedad("toEmail"); // Email al cual se va a mandar
		   String asunto = config.obtenerPropiedad("asuntoProceso");
		   String body = "El proceso" + resultado.getNombre()+", que se inicio a ejecutar el :" + resultado.getFechainicio() +" y tardo "+ resultado.getTiempo()+" Fallo y dio como error: " + resultado.getMsjresultado();
           this.mailSender.send(fromEmail , password, toEmail, asunto, body);
		}
	}
	
}
