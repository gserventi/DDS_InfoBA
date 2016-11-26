package com.edu.utn.infoba.modelo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import com.edu.utn.utils.Configuracion;

@SuppressWarnings("serial")
@Entity
@Table(name = "poi")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
		@NamedQuery(name = "buscarPoiPorNombre", query = "SELECT p FROM PuntoDeInteres p WHERE p.nombre LIKE :texto"),
		@NamedQuery(name = "buscarPoiPorTag", query = "SELECT p FROM PuntoDeInteres p LEFT OUTER JOIN p.tags t WHERE t.tag LIKE :texto"),
		@NamedQuery(name = "buscarPoiPorServicio", query = "SELECT p FROM PuntoDeInteres p LEFT OUTER JOIN p.servicios s WHERE s.nombre LIKE :texto"),
		@NamedQuery(name = "buscarPoiPorRubro", query = "SELECT p FROM PuntoDeInteres p LEFT OUTER JOIN p.rubro r WHERE r.nombre LIKE :texto") })

public class PuntoDeInteres extends Persistible {
	
	private String nombre;
	private String descripcion;
	private Boolean baja;
	protected Double distanciaCercania;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "poi_id", referencedColumnName = "id")
	protected Set<Servicio> servicios;
	
	@OneToOne(cascade = CascadeType.ALL)
	protected Rubro rubro;

	@OneToOne(cascade = CascadeType.ALL)
	private Direccion direccion;

	@OneToOne(cascade = CascadeType.ALL)
	private Coordenadas coordenadas;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "poi_id", referencedColumnName = "id")
	private Set<Tag> tags;

	public PuntoDeInteres() {
		this.tags = new HashSet<>();
		this.baja = false;
		this.servicios = new HashSet<Servicio>();
	}

	public PuntoDeInteres(String nombre, Direccion direccion, Coordenadas coordenadas) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.coordenadas = coordenadas;
		this.tags = new HashSet<>();
		this.baja = false;

		Configuracion config = new Configuracion();
		this.distanciaCercania = Double.parseDouble(config.obtenerPropiedad("DistanciaDefaultPOI"));
	}

	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Direccion getDireccion() {
		return this.direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Coordenadas getCoordenadas() {
		return this.coordenadas;
	}

	public void setCoordenadas(Coordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}

	@Column(name = "descripcion")
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "baja")
	public Boolean getBaja() {
		return this.baja;
	}

	public void setBaja(Boolean baja) {
		this.baja = baja;
	}

	public List<String> getTags() {
		List<String> lista = this.tags.stream().map(Tag::getTag).collect(Collectors.toList());

		return lista;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@Column(name = "distancia_cercania")
	public Double getDistanciaCercania() {
		return this.distanciaCercania;
	}

	public void setDistanciaCercania(Double distanciaCercania) {
		this.distanciaCercania = distanciaCercania;
	}

	public Boolean distanciaMenorA(Double metros, PuntoDeInteres otroPunto) {
		Double distancia = this.coordenadas.distanciaCon(otroPunto.coordenadas);
		return (distancia < metros);
	}

	// Tiene nombre y esta geolocalizado (tiene coordenadas)
	public Boolean esValido() {
		return ((this.nombre != null) && (!this.nombre.isEmpty()) && (this.coordenadas != null));
	}

	public Boolean cercaDe(PuntoDeInteres otroPunto) {
		return distanciaMenorA(this.distanciaCercania, otroPunto);
	}

	public String getInfo() {
		return "Info.";
	}

	public void agregarTag(Tag tag) {
		if (tags == null) {
			tags = new HashSet<>();
		}
		if (!tags.contains(tag)) {
			tag.setPuntoDeInteres(this);
			tags.add(tag);
		}
	}

	public void sacarTag(Tag tag) {
		if (tags.contains(tag))
			tags.remove(tag);
	}

	public void limpiarTags() {
		tags.clear();
	}

	public void darDeBaja() {
		this.baja = true;
	}

	public void habilitar() {
		this.baja = false;
	}

	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}
	
    public Set<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(Set<Servicio> servicios) {
		this.servicios = servicios;
	}

	public void agregarServicio(Servicio servicio) {
		if (servicios == null) {
			servicios = new HashSet<>();
		}
		if (!servicios.contains(servicio)) {
			servicio.setPuntoDeInteres(this);
			servicios.add(servicio);
		}
	}

	public void sacarServicio(Servicio servicio) {
		if (servicios.contains(servicio))
			servicios.remove(servicio);
	}

	public void limpiarServicios() {
		servicios.clear();
	}

	public boolean buscarServicios(String texto) {
  	   return servicios.stream().anyMatch(serv -> serv.getNombre().contains(texto));
    }

}
