package com.edu.utn.infoba.modelo.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.edu.utn.infoba.modelo.*;

@Entity("poi")
public class PuntoDeInteresMongoDB {
	@Id
	private ObjectId id;
	private Rubro rubro;
	private Direccion direccion;
	private Coordenadas coordenadas;
	@Embedded
	private List<String> tags;
	private Boolean baja;
    private String nombre;
    private String descripcion;
    private Double distanciaCercania;
    @Embedded
	private List<ServicioMongoDB> servicios;
    
    public PuntoDeInteresMongoDB() {}
    
    public PuntoDeInteresMongoDB(String nombre, Direccion direccion, Coordenadas coordenadas) {
    	this.nombre = nombre;
    	this.direccion = direccion;
    	this.coordenadas = coordenadas;
    }
    
    public PuntoDeInteresMongoDB(String nombre, Direccion direccion, Coordenadas coordenadas, List<Servicio> servicios) {
    	this.nombre = nombre;
    	this.direccion = direccion;
    	this.coordenadas = coordenadas;
    	List <RangosServicioMongoDB> horario;
    	this.servicios = new ArrayList<>();
    	horario = new ArrayList<>();
		if (servicios != null) {
			for (Servicio s : servicios) {
				if (s.getHorarioServicio() != null) {
					for (HorarioServicio h : s.getHorarioServicio()) {
						horario.add(new RangosServicioMongoDB(h.getDia(),h.getHoraDesde(),h.getMinutoDesde(),h.getHoraHasta(),h.getMinutoHasta()));
					}
				}
				this.servicios.add(new ServicioMongoDB(s.getNombre(), horario));
				horario.clear();
			}
		}
    }
    
    public PuntoDeInteresMongoDB(Rubro rubro, Direccion direccion, Coordenadas coordenadas, 
    		List<String> tags, Boolean baja, String nombre, String descripcion, Double distanciaCercania) {
    	this.rubro = rubro;
    	this.direccion = direccion;
    	this.coordenadas = coordenadas;
    	this.tags = tags;
    	this.baja = baja;
    	this.nombre = nombre;
    	this.descripcion = descripcion;
    	this.distanciaCercania = distanciaCercania;
    }
    
	public PuntoDeInteresMongoDB(PuntoDeInteres poi) {
    	this.rubro = poi.getRubro();
    	this.direccion = poi.getDireccion();
    	this.coordenadas = poi.getCoordenadas();
    	this.tags = poi.getTags();
    	this.baja = poi.getBaja();
    	this.nombre = poi.getNombre();
    	this.descripcion = poi.getDescripcion();
    	this.distanciaCercania = poi.getDistanciaCercania();
    	List <RangosServicioMongoDB> horario;
    	this.servicios = new ArrayList<>();
    	horario = new ArrayList<>();
		if (poi.getServicios() != null) {
			for (Servicio s : poi.getServicios()) {
				if (s.getHorarioServicio() != null) {
					for (HorarioServicio h : s.getHorarioServicio()) {
						horario.add(new RangosServicioMongoDB(h.getDia(),h.getHoraDesde(),h.getMinutoDesde(),h.getHoraHasta(),h.getMinutoHasta()));
					}
				}
				this.servicios.add(new ServicioMongoDB(s.getNombre(), horario));
				horario.clear();
			}
		}
    }

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Coordenadas getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Coordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Boolean getBaja() {
		return baja;
	}

	public void setBaja(Boolean baja) {
		this.baja = baja;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getDistanciaCercania() {
		return distanciaCercania;
	}
	
	public void setDistanciaCercania(Double distanciaCercania) {
		this.distanciaCercania = distanciaCercania;
	}
	
	public List<ServicioMongoDB> getServicios() {
		return servicios;
	}

	public void setServicios(List<ServicioMongoDB> servicios) {
		this.servicios = servicios;
	}

	public PuntoDeInteres convertirToPoi() {
		PuntoDeInteres poi = new PuntoDeInteres();
		poi.setNombre(nombre);
		poi.setDireccion(direccion);
		poi.setCoordenadas(coordenadas);
		poi.setBaja(baja);
		poi.setDescripcion(descripcion);
		poi.setDistanciaCercania(distanciaCercania);
		poi.setRubro(rubro);
		if (this.getTags() != null) {
			for (String t : tags) {
				poi.agregarTag(new Tag(t));
			}
		}
		if (servicios != null) {
			for (ServicioMongoDB s : servicios) {
				Servicio servicio = new Servicio();
				servicio.setNombre(s.getNombre());
				if (s.getRangosServicio() != null) {
					for (RangosServicioMongoDB r : s.getRangosServicio()) {
						servicio.agregarHorarioServicio(new HorarioServicio(r.getDiaSemana(),
								r.getHoraDesde(),r.getMinutoDesde(),r.getHoraHasta(),r.getMinutoHasta()));
					}
				}
				poi.agregarServicio(servicio);
				servicio = null;
			}
		}
		return poi;
	}
}
