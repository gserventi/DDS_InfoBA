package com.edu.utn.infoba.modelo.mongodb;

import java.util.List;

import com.edu.utn.infoba.modelo.CGP;
import com.edu.utn.infoba.modelo.Coordenadas;
import com.edu.utn.infoba.modelo.Direccion;
import com.edu.utn.infoba.modelo.HorarioServicio;
import com.edu.utn.infoba.modelo.PuntoDeInteres;
import com.edu.utn.infoba.modelo.Servicio;
import com.edu.utn.infoba.modelo.Tag;

public class CGPMongoDB extends PuntoDeInteresMongoDB {
    private int comuna;
    private String telefono;
    private String director;
 
    public CGPMongoDB() {}
    
    public CGPMongoDB(String nombre,  Direccion direccion, Coordenadas coordenadas) {
    	super(nombre, direccion, coordenadas);
    }
    
    public CGPMongoDB(String nombre,  Direccion direccion, Coordenadas coordenadas, 
    		List<Servicio> servicios, int comuna, String telefono, String director) {
    	super(nombre, direccion, coordenadas, servicios);
		this.comuna = comuna;
		this.telefono = telefono;
		this.director = director;
	}
    
    public CGPMongoDB(CGP cgp) {
    	super(cgp);
    	this.comuna = cgp.getComuna();
    	this.telefono = cgp.getTelefono();
    	this.director = cgp.getDirector();
    }
        
	public int getComuna() {
		return comuna;
	}

	public void setComuna(int comuna) {
		this.comuna = comuna;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
	
	public PuntoDeInteres convertirToPoi() {
		CGP cgp = new CGP(this.getNombre(), this.getDireccion(), this.getCoordenadas());
		cgp.setBaja(this.getBaja());
		cgp.setDescripcion(this.getDescripcion());
		cgp.setDistanciaCercania(this.getDistanciaCercania());
		cgp.setRubro(this.getRubro());
		if (this.getTags() != null) {
			for (String t : this.getTags()) {
				cgp.agregarTag(new Tag(t));
			}
		}
		if (this.getServicios() != null) {
			for (ServicioMongoDB s : this.getServicios()) {
				Servicio servicio = new Servicio();
				servicio.setNombre(s.getNombre());
				if (s.getRangosServicio() != null) {
					for (RangosServicioMongoDB r : s.getRangosServicio()) {
						servicio.agregarHorarioServicio(new HorarioServicio(r.getDiaSemana(),
								r.getHoraDesde(),r.getMinutoDesde(),r.getHoraHasta(),r.getMinutoHasta()));
					}
				}
				cgp.agregarServicio(servicio);
				servicio = null;
			}
		}
		cgp.setComuna(comuna);
		cgp.setTelefono(telefono);
		cgp.setDirector(director);
		return cgp;
	}

}
