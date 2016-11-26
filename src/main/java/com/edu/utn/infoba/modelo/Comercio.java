package com.edu.utn.infoba.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
public class Comercio extends PuntoDeInteres
{
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "poi_id", referencedColumnName = "id")
    private Set<HorarioComercio> horariosComercio;

    public Comercio() {}
    
    public Comercio(String nombre, Direccion direccion, Coordenadas coordenadas, Rubro rubro) {
        super(nombre, direccion, coordenadas);
        this.rubro = rubro;
    }
    
    public Rubro getRubro() {
	   return this.rubro;
	}

	public void setRubro(Rubro rubro) {
	   this.rubro = rubro;
	}
	
	public Set<HorarioComercio> getHorarioComercio() {
		return this.horariosComercio;
	}
	 	
	public void setHorarioComercio(Set<HorarioComercio> horariosComercio) {
	 	this.horariosComercio = horariosComercio;
	}

    public Boolean cercaDe(PuntoDeInteres otroPunto) {
        return distanciaMenorA(this.rubro.getRadioCercania(), otroPunto);
    }
    
    public boolean buscarRubros(String texto) {
 	   return this.rubro.getNombre().contains(texto);
    }
    
    public void agregarHorarioComercio(HorarioComercio horarioComercio) {
 		if (horariosComercio == null) {
 			horariosComercio = new HashSet<>();
 		}
 		if (!horariosComercio.contains(horarioComercio))
 			horariosComercio.add(horarioComercio);
 	}
 	
 	public void removerHorarioComercio(HorarioComercio horarioComercio) {
 		if(horariosComercio.contains(horarioComercio))
 			horariosComercio.remove(horarioComercio);
 	}
 	
 	public void limpiarHorarioComercio() {
 		horariosComercio.clear();
 	}
  
    public boolean disponibilidad(Date tiempo , Servicio servicio) {
    	String diaNumero = new SimpleDateFormat("u").format(tiempo);
    	if( Integer.parseInt(diaNumero) != 6 && Integer.parseInt(diaNumero) != 7) {		
    		for (HorarioComercio h : horariosComercio) {
    		    if(h.coincideTiempo(tiempo)) 
    		    	return true;
    		}
    		return false;
    	}
    	else
    		return false;
    }
}
