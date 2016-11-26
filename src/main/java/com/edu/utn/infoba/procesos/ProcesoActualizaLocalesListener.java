package com.edu.utn.infoba.procesos;

import org.quartz.JobListener;

public class ProcesoActualizaLocalesListener extends ProcesoListener implements JobListener
{
	@Override
	protected void rollback()
	{
		System.out.println("Rollback de Actualiza Locales.");
	}
}
