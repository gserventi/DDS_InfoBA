package com.edu.utn.infoba.bean;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.faces.bean.ManagedBean;
import java.util.HashMap;
import java.util.Map;
import javax.faces.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

@ManagedBean
public class AccionesConsultaBean 
{
     private String accion;
     private Map<String,String> accionesItems;	
     private List<String> acciones;
     
     // Overriding method.
     public AccionesConsultaBean()
     {
    	 acciones = new ArrayList<>();
		 acciones.add("Primera acción");
		 
    	 accionesItems = new HashMap<>();
    	 accionesItems.put("Generar Log", "Generar Log");
    	 accionesItems.put("Totalizar por fecha", "Totalizar por fecha");
    	 accionesItems.put("Totalizar por usuario", "Totalizar por usuario");
    	 
    	 accion = null;
     }
     
     
     public Map<String,String> getAccionesItems(){
    	 return accionesItems;	
     }	

     public void setAccionesItems(Map<String,String> _accionesItems){
    	 accionesItems = _accionesItems;	
     }	
     
     public String getAccion() {
         return this.accion;
     }
  
     public void setAccion(String accion) {
    	 System.out.println("Accion set!");
    	 System.out.println(accion);    	 
         this.accion = accion;
     }
    
 	public List<String> getAcciones() {
        return acciones;
    } 
    
		
	public void aceptar() throws NoSuchAlgorithmException, NoSuchProviderException {
		//
		System.out.println("Aceptar called.");
	}
		
	public void cancelar() {
		// TODO
		System.out.println("Cancelar called.");
	}
	
	public void buttonAction(ActionEvent actionEvent) {
		//Agregar accion.
		 acciones.add(this.accion);		 
	}	
			
}
