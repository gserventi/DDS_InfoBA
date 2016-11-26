package com.edu.utn.infoba.procesos;

public class ProcesoBajaPoisListener extends ProcesoListener
{
	@Override
	protected void rollback()
	{
		//no es necesario en teoria.
		System.out.println("Rollback!");
	}

}
