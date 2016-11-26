package com.edu.utn.infoba.modelo.mongodb;

import java.util.List;

import com.edu.utn.infoba.modelo.Banco;
import com.edu.utn.infoba.modelo.Coordenadas;
import com.edu.utn.infoba.modelo.Direccion;
import com.edu.utn.infoba.modelo.HorarioServicio;
import com.edu.utn.infoba.modelo.PuntoDeInteres;
import com.edu.utn.infoba.modelo.Servicio;
import com.edu.utn.infoba.modelo.Tag;

public class BancoMongoDB extends PuntoDeInteresMongoDB { 
	private String sucursal;
	private String gerente;
	
	public BancoMongoDB() {}
	
	public BancoMongoDB(String nombre,  Direccion direccion, Coordenadas coordenadas) {
		super(nombre, direccion, coordenadas);
	}
	
	public BancoMongoDB(String nombre,  Direccion direccion, Coordenadas coordenadas, List<Servicio> servicios, String sucursal, String gerente) {
		super(nombre, direccion, coordenadas, servicios);
		this.sucursal = sucursal;
		this.gerente = gerente;
	}
	
	public BancoMongoDB(Banco banco) {
		super(banco);		
		this.sucursal = banco.getSucursal();
		this.gerente = banco.getGerente();
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getGerente() {
		return gerente;
	}

	public void setGerente(String gerente) {
		this.gerente = gerente;
	}
	
	public PuntoDeInteres convertirToPoi() {
		Banco banco = new Banco(this.getNombre(), this.getDireccion(), this.getCoordenadas());
		banco.setBaja(this.getBaja());
		banco.setDescripcion(this.getDescripcion());
		banco.setDistanciaCercania(this.getDistanciaCercania());
		banco.setRubro(this.getRubro());
		if (this.getTags() != null) {
			for (String t : this.getTags()) {
				banco.agregarTag(new Tag(t));
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
				banco.agregarServicio(servicio);
				servicio = null;
			}
		}
		banco.setSucursal(sucursal);
		banco.setGerente(gerente);
		return banco;
	}
}
