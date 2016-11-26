package com.edu.utn.infoba.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mongodb.morphia.Datastore;

import com.edu.utn.infoba.ApiRestBancos;
import com.edu.utn.infoba.ApiRestCgp;
import com.edu.utn.infoba.FuenteDeDatos;
import com.edu.utn.infoba.MailSender;
import com.edu.utn.infoba.MockMailSender;
import com.edu.utn.infoba.OrigenDeDatosDummy;
import com.edu.utn.infoba.modelo.*;
//import com.edu.utn.infoba.repositorios.Repositorio;
import com.edu.utn.infoba.repositorios.RepositorioBusqueda;
import com.edu.utn.infoba.repositorios.RepositorioPois;
import com.edu.utn.utils.MongoDBConnection;
import com.edu.utn.utils.PoisMongoDB;
import com.edu.utn.utils.PoisMySQL;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCargaInicial {
	private static final String PERSISTENCE_UNIT_NAME = "infoba";
	private EntityManagerFactory emFactory;
//	private Repositorio repositorio;
	private RepositorioPois repositorioPois;
	private MailSender mailSender;
	private FuenteDeDatos fuenteMySql;
	private FuenteDeDatos fuenteBancosMongo;
	private FuenteDeDatos fuenteCentrosMongo;
	
	private Localidad localidadCABA;
	private Provincia provinciaCABA;
	private Pais paisArgentina;
	
	@Before
	public void setUp() throws Exception
    {
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emFactory.createEntityManager();
//		repositorio = new Repositorio(em,mailSender);
		mailSender = new MockMailSender();
		Datastore ds = MongoDBConnection.getInstance().getDatastore();
		repositorioPois = new RepositorioPois(new RepositorioBusqueda(ds, mailSender),em);
		fuenteMySql = new FuenteDeDatos(new OrigenDeDatosDummy(),new PoisMySQL(emFactory.createEntityManager()));
		repositorioPois.agregarFuenteDeDatos(fuenteMySql);
		fuenteBancosMongo = new FuenteDeDatos(new ApiRestBancos(),new PoisMongoDB(MongoDBConnection.getInstance().getDatastore()));
		repositorioPois.agregarFuenteDeDatos(fuenteBancosMongo);
		fuenteCentrosMongo = new FuenteDeDatos(new ApiRestCgp(),new PoisMongoDB(MongoDBConnection.getInstance().getDatastore()));
		repositorioPois.agregarFuenteDeDatos(fuenteCentrosMongo);

		localidadCABA = new Localidad("CABA");
		provinciaCABA = new Provincia("CABA");
		paisArgentina = new Pais("Argentina");
    }
	
	@Test
	public void altaDeUsuarios() {
//		String accion1 = "BuscarPOI";
//		String accion2 = "VisualizarBusquedas";
		
//		Administrador usuario1 = new Administrador("admin1","pass1");
//		usuario1.agregarAccion(new AccionesUsuario(accion1));
//		usuario1.agregarAccion(new AccionesUsuario(accion2));
//		repositorio.usuarios().persistir(usuario1);
		
//		Administrador usuario2 = new Administrador("admin2","pass2");
//		usuario2.agregarAccion(new AccionesUsuario(accion1));
//		usuario2.agregarAccion(new AccionesUsuario(accion2));
//		repositorio.usuarios().persistir(usuario2);
		
//		Terminal usuario3 = new Terminal("user1","pass1");
//		usuario3.agregarAccion(new AccionesUsuario(accion1));
//		repositorio.usuarios().persistir(usuario3);
		
//		Terminal usuario4 = new Terminal("user1","pass1");
//		usuario4.agregarAccion(new AccionesUsuario(accion1));
//		usuario4.agregarAccion(new AccionesUsuario(accion2));
//		repositorio.usuarios().persistir(usuario4);
	}
	
	@Test
	public void altaPOI() {
		//
		// ALTA DE BANCOS
		//
//		Banco banco1 = new Banco("Nacion", new Direccion("Florida", "238", null, null, "C1005AAF", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.605116, -58.375273));
//		banco1.setDescripcion("Banco de La Nacion Argentina");
//		banco1.setBaja(false);
//		banco1.setDistanciaCercania((double) 500);
//		banco1.setSucursal("Florida");
//		banco1.setGerente("Luis Rodriguez");
//		Servicio servicio1 = new Servicio("Pagos");
//		servicio1.agregarHorarioServicio(new HorarioServicio(1,10,0,15,0));
//		servicio1.agregarHorarioServicio(new HorarioServicio(2,10,0,15,0));
//		servicio1.agregarHorarioServicio(new HorarioServicio(3,10,0,15,0));
//		servicio1.agregarHorarioServicio(new HorarioServicio(4,10,0,15,0));
//		servicio1.agregarHorarioServicio(new HorarioServicio(5,10,0,15,0));
//		banco1.agregarServicio(servicio1);
//		banco1.agregarServicio(new Servicio("Cuentas"));
//		banco1.agregarServicio(new Servicio("Inversiones"));
//		banco1.agregarTag(new Tag("banco"));
//		banco1.agregarTag(new Tag("nacion"));
//		fuenteMySql.agregarPuntoDeInteres(banco1);
//		
//		Banco banco2 = new Banco("Ciudad", new Direccion("Av. Callao", "270", null, null, "C1022AAP", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.605961, -58.392412));
//		banco2.setDescripcion("Banco Ciudad");
//		banco2.setBaja(false);
//		banco2.setDistanciaCercania((double) 500);
//		banco2.setSucursal("Callao");
//		banco2.setGerente("Laura Suarez");
//		Servicio servicio2 = new Servicio("Cuentas");
//		servicio2.agregarHorarioServicio(new HorarioServicio(1,10,0,15,0));
//		servicio2.agregarHorarioServicio(new HorarioServicio(2,10,0,15,0));
//		servicio2.agregarHorarioServicio(new HorarioServicio(3,10,0,15,0));
//		servicio2.agregarHorarioServicio(new HorarioServicio(4,10,0,15,0));
//		servicio2.agregarHorarioServicio(new HorarioServicio(5,10,0,15,0));
//		banco2.agregarServicio(servicio2);
//		banco2.agregarServicio(new Servicio("Seguros"));
//		banco2.agregarServicio(new Servicio("Creditos"));
//		banco2.agregarTag(new Tag("banco"));
//		banco2.agregarTag(new Tag("ciudad"));
//		fuenteMySql.agregarPuntoDeInteres(banco2);
//
//		//
//		// ALTA DE CGPS
//		//
//		CGP cgp1 = new CGP("CGP 1", new Direccion("Uruguay", "740", null, null, "C1015ABN", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.599962, -58.386955));
//		cgp1.setDescripcion("Sede Comunal 1");
//		cgp1.setBaja(false);
//		cgp1.setComuna(1);
//		cgp1.setTelefono("0800-999-2727");
//		cgp1.setDirector("Carlos Rueda");
//		cgp1.agregarZona(new Zona("Retiro"));
//		cgp1.agregarZona(new Zona("San Nicolas"));
//		cgp1.agregarZona(new Zona("Puerto Madero"));
//		cgp1.agregarZona(new Zona("San Telmo"));
//		cgp1.agregarZona(new Zona("Montserrat"));
//		cgp1.agregarZona(new Zona("Constitucion"));
//		cgp1.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp1.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp1.agregarServicio(new Servicio("Rentas"));
//		cgp1.agregarServicio(new Servicio("Mediacion Comunitaria"));
//		cgp1.agregarTag(new Tag("comuna 1"));
//		cgp1.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp1);
//		
//		CGP cgp2 = new CGP("CGP 2", new Direccion("Pres. Jose Evaristo Uriburu", "1022", null, null, "C1114AAF", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.596664, -58.399131));
//		cgp2.setDescripcion("Sede Comunal 2");
//		cgp2.setBaja(false);
//		cgp2.setComuna(2);
//		cgp2.setTelefono("0800-999-2727");
//		cgp2.setDirector("Rosa Quevedo");
//		cgp2.agregarZona(new Zona("Recoleta"));
//		cgp2.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp2.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp2.agregarServicio(new Servicio("EcoBici"));
//		cgp2.agregarServicio(new Servicio("Control Comunal"));
//		cgp2.agregarTag(new Tag("comuna 2"));
//		cgp2.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp2);
//		
//		CGP cgp3 = new CGP("CGP 3", new Direccion("Junin", "521", null, null, "C1026ABK", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.603020, -58.396859));
//		cgp3.setDescripcion("Sede Comunal 3");
//		cgp3.setBaja(false);
//		cgp3.setComuna(3);
//		cgp3.setTelefono("0800-999-2727");
//		cgp3.setDirector("Walter Rosas");
//		cgp3.agregarZona(new Zona("Balvanera"));
//		cgp3.agregarZona(new Zona("San Cristobal"));
//		cgp3.agregarServicio(new Servicio("Tesoreria"));
//		cgp3.agregarServicio(new Servicio("Programa Proteger"));
//		cgp3.agregarServicio(new Servicio("Oficina de empelo"));
//		cgp3.agregarServicio(new Servicio("Asesoramiento juridico"));
//		cgp3.agregarTag(new Tag("comuna 3"));
//		cgp3.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp3);
//		
//		CGP cgp4 = new CGP("CGP 4", new Direccion("Av. del Barco Centenera", "2906", null, null, "C1437ACN", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.650206, -58.424649));
//		cgp4.setDescripcion("Sede Comunal 4");
//		cgp4.setBaja(false);
//		cgp4.setComuna(4);
//		cgp4.setTelefono("0800-999-2727");
//		cgp4.setDirector("Analia Gutierrez");
//		cgp4.agregarZona(new Zona("La Boca"));
//		cgp4.agregarZona(new Zona("Barracas"));
//		cgp4.agregarZona(new Zona("Parque Patricios"));
//		cgp4.agregarZona(new Zona("Nueva Pompeya"));
//		cgp4.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp4.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp4.agregarServicio(new Servicio("Servicio Social Zonal"));
//		cgp4.agregarServicio(new Servicio("Estrategia Joven"));
//		cgp4.agregarTag(new Tag("comuna 4"));
//		cgp4.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp4);
//		
//		CGP cgp5 = new CGP("CGP 5", new Direccion("Carlos Calvo", "3307", null, null, "C1230ABE", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.622982, -58.412718));
//		cgp5.setDescripcion("Sede Comunal 5");
//		cgp5.setBaja(false);
//		cgp5.setComuna(5);
//		cgp5.setTelefono("011 4931-6699");
//		cgp5.setDirector("Andres Rosales");
//		cgp5.agregarZona(new Zona("Almagro"));
//		cgp5.agregarZona(new Zona("Boedo"));
//		cgp5.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp5.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp5.agregarServicio(new Servicio("Tesoreria"));
//		cgp5.agregarServicio(new Servicio("Control Comunal"));
//		cgp5.agregarTag(new Tag("comuna 5"));
//		cgp5.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp5);
//		
//		CGP cgp6 = new CGP("CGP 6", new Direccion("Av. Patricias Argentinas", "277", null, null, "C1405BWC", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.605839, -58.432727));
//		cgp6.setDescripcion("Sede Comunal 6");
//		cgp6.setBaja(false);
//		cgp6.setComuna(6);
//		cgp6.setTelefono("0800-999-2727");
//		cgp6.setDirector("Maria Ramos");
//		cgp6.agregarZona(new Zona("Caballito"));
//		cgp6.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp6.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp6.agregarServicio(new Servicio("Tesoreria"));
//		cgp6.agregarServicio(new Servicio("Control Comunal"));
//		cgp6.agregarTag(new Tag("comuna 6"));
//		cgp6.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp6);
//		
//		CGP cgp7 = new CGP("CGP 7", new Direccion("Av. Rivadavia", "7202", null, null, "C1406GMP", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.630213, -58.467048));
//		cgp7.setDescripcion("Sede Comunal 7");
//		cgp7.setBaja(false);
//		cgp7.setComuna(7);
//		cgp7.setTelefono("0800-999-2727");
//		cgp7.setDirector("Maria Ramos");
//		cgp7.agregarZona(new Zona("Flores"));
//		cgp7.agregarZona(new Zona("Parque Chacabuco"));
//		cgp7.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp7.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp7.agregarServicio(new Servicio("Tesoreria"));
//		cgp7.agregarServicio(new Servicio("Control Comunal"));
//		cgp7.agregarTag(new Tag("comuna 7"));
//		cgp7.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp7);
//		
//		CGP cgp8 = new CGP("CGP 8", new Direccion("Av. Cnel. Roca", "5252", null, null, "C1439DVO", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.685828, -58.456231));
//		cgp8.setDescripcion("Sede Comunal 8");
//		cgp8.setBaja(false);
//		cgp8.setComuna(8);
//		cgp8.setTelefono("0800-999-2727");
//		cgp8.setDirector("Angel Ramirez");
//		cgp8.agregarZona(new Zona("Villa Soldati"));
//		cgp8.agregarZona(new Zona("Villa Riachuelo"));
//		cgp8.agregarZona(new Zona("Villa Lugano"));
//		cgp8.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp8.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp8.agregarServicio(new Servicio("Tesoreria"));
//		cgp8.agregarServicio(new Servicio("Control Comunal"));
//		cgp8.agregarTag(new Tag("comuna 8"));
//		cgp8.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp8);
//		
//		CGP cgp9 = new CGP("CGP 9", new Direccion("Av Directorio", "4360", null, null, "C1407HGQ", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.645839, -58.484973));
//		cgp9.setDescripcion("Sede Comunal 9");
//		cgp9.setBaja(false);
//		cgp9.setComuna(9);
//		cgp9.setTelefono("0800-999-2727");
//		cgp9.setDirector("Carla Lopez");
//		cgp9.agregarZona(new Zona("Liniers"));
//		cgp9.agregarZona(new Zona("Mataderos"));
//		cgp9.agregarZona(new Zona("Parque Avellaneda"));
//		cgp9.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp9.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp9.agregarServicio(new Servicio("Tesoreria"));
//		cgp9.agregarServicio(new Servicio("Control Comunal"));
//		cgp9.agregarTag(new Tag("comuna 9"));
//		cgp9.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp9);
//		
//		CGP cgp10 = new CGP("CGP 10", new Direccion("Av. Segurola", "141", null, null, "C1407ANA", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.632376, -58.484018));
//		cgp10.setDescripcion("Sede Comunal 10");
//		cgp10.setBaja(false);
//		cgp10.setComuna(10);
//		cgp10.setTelefono("0800-999-2727");
//		cgp10.setDirector("Esteban Trujillo");
//		cgp10.agregarZona(new Zona("Villa Real"));
//		cgp10.agregarZona(new Zona("Monte Castro"));
//		cgp10.agregarZona(new Zona("Versalles"));
//		cgp10.agregarZona(new Zona("Floresta"));
//		cgp10.agregarZona(new Zona("Velez Sarsfield"));
//		cgp10.agregarZona(new Zona("Villa Luro"));
//		cgp10.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp10.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp10.agregarServicio(new Servicio("Tesoreria"));
//		cgp10.agregarServicio(new Servicio("Control Comunal"));
//		cgp10.agregarTag(new Tag("comuna 10"));
//		cgp10.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp10);
//		
//		CGP cgp11 = new CGP("CGP 11", new Direccion("Av. Francisco Beiro", "4680", null, null, "C1419HZV", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.608747, -58.516349));
//		cgp11.setDescripcion("Sede Comunal 11");
//		cgp11.setBaja(false);
//		cgp11.setComuna(11);
//		cgp11.setTelefono("0800-999-2727");
//		cgp11.setDirector("Barbara Lugones");
//		cgp11.agregarZona(new Zona("Villa General Mitre"));
//		cgp11.agregarZona(new Zona("Villa Devoto"));
//		cgp11.agregarZona(new Zona("Villa del Parque"));
//		cgp11.agregarZona(new Zona("Villa Santa Rita"));
//		cgp11.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp11.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp11.agregarServicio(new Servicio("Tesoreria"));
//		cgp11.agregarServicio(new Servicio("Control Comunal"));
//		cgp11.agregarTag(new Tag("comuna 11"));
//		cgp11.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp11);
//		
//		CGP cgp12 = new CGP("CGP 12", new Direccion("Miller", "2751", null, null, "C1431GEB", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.568715, -58.482810));
//		cgp12.setDescripcion("Sede Comunal 12");
//		cgp12.setBaja(false);
//		cgp12.setComuna(12);
//		cgp12.setTelefono("0800-999-2727");
//		cgp12.setDirector("Ernesto Rosso");
//		cgp12.agregarZona(new Zona("Coghlan"));
//		cgp12.agregarZona(new Zona("Saavedra"));
//		cgp12.agregarZona(new Zona("Villa Urquiza"));
//		cgp12.agregarZona(new Zona("Villa Pueyrredon"));
//		cgp12.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp12.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp12.agregarServicio(new Servicio("Tesoreria"));
//		cgp12.agregarServicio(new Servicio("Control Comunal"));
//		cgp12.agregarTag(new Tag("comuna 12"));
//		cgp12.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp12);
//		
//		CGP cgp13 = new CGP("CGP 13", new Direccion("Av. Cabildo", "3067", null, null, "C1429AAN", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.553775, -58.464122));
//		cgp13.setDescripcion("Sede Comunal 13");
//		cgp13.setBaja(false);
//		cgp13.setComuna(13);
//		cgp13.setTelefono("0800-999-2727");
//		cgp13.setDirector("Susana Torres");
//		cgp13.agregarZona(new Zona("Nuñez"));
//		cgp13.agregarZona(new Zona("Belgrano"));
//		cgp13.agregarZona(new Zona("Colegiales"));
//		cgp13.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp13.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp13.agregarServicio(new Servicio("Tesoreria"));
//		cgp13.agregarServicio(new Servicio("Control Comunal"));
//		cgp13.agregarTag(new Tag("comuna 13"));
//		cgp13.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp13);
//		
//		CGP cgp14 = new CGP("CGP 14", new Direccion("Av. Cnel. Diaz", "2120", null, null, "C1425DQU", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.587307, -58.409118));
//		cgp14.setDescripcion("Sede Comunal 14");
//		cgp14.setBaja(false);
//		cgp14.setComuna(14);
//		cgp14.setTelefono("0800-999-2727");
//		cgp14.setDirector("Alberto Benavidez");
//		cgp14.agregarZona(new Zona("Palermo"));
//		cgp14.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp14.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp14.agregarServicio(new Servicio("Tesoreria"));
//		cgp14.agregarServicio(new Servicio("Control Comunal"));
//		cgp14.agregarTag(new Tag("comuna 14"));
//		cgp14.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp14);
//		
//		CGP cgp15 = new CGP("CGP 15", new Direccion("Av. Cordoba", "5690", null, null, "C1414BBO", 
//				localidadCABA, provinciaCABA, paisArgentina), new Coordenadas(-34.586947, -58.441367));
//		cgp15.setDescripcion("Sede Comunal 15");
//		cgp15.setBaja(false);
//		cgp15.setComuna(15);
//		cgp15.setTelefono("0800-999-2727");
//		cgp15.setDirector("Romina Galvez");
//		cgp15.agregarZona(new Zona("Chacarita"));
//		cgp15.agregarZona(new Zona("Villa Crespo"));
//		cgp15.agregarZona(new Zona("La Paternal"));
//		cgp15.agregarZona(new Zona("Villa Ortuzar"));
//		cgp15.agregarZona(new Zona("Agronomia"));
//		cgp15.agregarZona(new Zona("Parque Chas"));
//		cgp15.agregarServicio(new Servicio("Mesa de Informes"));
//		cgp15.agregarServicio(new Servicio("Atencion Ciudadana"));
//		cgp15.agregarServicio(new Servicio("Tesoreria"));
//		cgp15.agregarServicio(new Servicio("Control Comunal"));
//		cgp15.agregarTag(new Tag("comuna 15"));
//		cgp15.agregarTag(new Tag("cgp"));
//		fuenteMySql.agregarPuntoDeInteres(cgp15);
//	
//		//
//		// ALTA DE COMERCIOS
//		//
		Comercio comercio1 = new Comercio("Restaurante Santander", new Direccion("San Martin", "150", null, null, 
				"C1004AAD", localidadCABA, provinciaCABA, paisArgentina), 
				new Coordenadas(-34.606258, -58.373807), 
				new Rubro("Cafeteria", (double) 300));
		comercio1.setDescripcion("Cadena de Cafeterias americana");
		comercio1.setBaja(false);
		comercio1.agregarTag(new Tag("Cafe"));
		comercio1.agregarHorarioComercio(new HorarioComercio(1,7,0,20,0));
		comercio1.agregarHorarioComercio(new HorarioComercio(2,7,0,20,0));
		comercio1.agregarHorarioComercio(new HorarioComercio(3,7,0,20,0));
		comercio1.agregarHorarioComercio(new HorarioComercio(4,7,0,20,0));
		comercio1.agregarHorarioComercio(new HorarioComercio(5,7,0,20,0));
		fuenteMySql.agregarPuntoDeInteres(comercio1);
		
//		Comercio comercio2 = new Comercio("Peluqueria Tijeras", new Direccion("Carlos Pelegrini", "150", null, null, 
//				"C1004AAD", localidadCABA, provinciaCABA, paisArgentina), 
//				new Coordenadas(-34.606258, -58.373807), 
//				new Rubro("Heladeria", (double) 500));
//		comercio2.setDescripcion("Helado y mas helado");
//		comercio2.setBaja(false);
//		comercio2.agregarTag(new Tag("Heladeria"));
//		comercio2.agregarTag(new Tag("Helado"));
//		comercio2.agregarHorarioComercio(new HorarioComercio(1,10,0,24,0));
//		comercio2.agregarHorarioComercio(new HorarioComercio(2,10,0,24,0));
//		comercio2.agregarHorarioComercio(new HorarioComercio(3,10,0,24,0));
//		comercio2.agregarHorarioComercio(new HorarioComercio(4,10,0,24,0));
//		comercio2.agregarHorarioComercio(new HorarioComercio(5,10,0,24,0));
//		fuenteMySql.agregarPuntoDeInteres(comercio2);
//		
//		Comercio comercio3 = new Comercio("Libreria", new Direccion("Lavalle", "850", null, null, 
//				"C1004AAD", localidadCABA, provinciaCABA, paisArgentina), 
//				new Coordenadas(-34.606258, -58.373807), 
//				new Rubro("Libreria", (double) 1000));
//		comercio3.setDescripcion("Libreria del centro");
//		comercio3.setBaja(false);
//		comercio3.agregarTag(new Tag("Libreria"));
//		comercio3.agregarTag(new Tag("Libros"));
//		comercio3.agregarHorarioComercio(new HorarioComercio(1,9,0,18,0));
//		comercio3.agregarHorarioComercio(new HorarioComercio(2,9,0,18,0));
//		comercio3.agregarHorarioComercio(new HorarioComercio(3,9,0,18,0));
//		comercio3.agregarHorarioComercio(new HorarioComercio(4,9,0,18,0));
//		comercio3.agregarHorarioComercio(new HorarioComercio(5,9,0,18,0));
//		fuenteMySql.agregarPuntoDeInteres(comercio3);
//		
//		Comercio comercio4 = new Comercio("Starbucks Coffee2", new Direccion("San Martin", "150", null, 
//				null, "C1004AAD", localidadCABA, provinciaCABA, paisArgentina), 
//				new Coordenadas(-34.606258, -58.373807), 
//				new Rubro("Cafeteria", (double) 300));
//		comercio4.setDescripcion("Cadena de Cafeterias americana");
//		comercio4.setBaja(false);
//		comercio4.agregarTag(new Tag("Cafe"));
//		comercio4.agregarHorarioComercio(new HorarioComercio(1,7,0,20,0));
//		comercio4.agregarHorarioComercio(new HorarioComercio(2,7,0,20,0));
//		comercio4.agregarHorarioComercio(new HorarioComercio(3,7,0,20,0));
//		comercio4.agregarHorarioComercio(new HorarioComercio(4,7,0,20,0));
//		comercio4.agregarHorarioComercio(new HorarioComercio(5,7,0,20,0));
//		fuenteMySql.agregarPuntoDeInteres(comercio4);
//		
//		Comercio comercio5 = new Comercio("Adidas", new Direccion("Independencia", "4550", null, null, 
//				"C1004AAD", localidadCABA, provinciaCABA, paisArgentina), 
//				new Coordenadas(-34.606258, -58.373807), 
//				new Rubro("Indumentaria", (double) 300));
//		comercio5.setDescripcion("Ropa deportiva");
//		comercio5.setBaja(false);
//		comercio5.agregarTag(new Tag("Indumentaria"));
//		comercio5.agregarTag(new Tag("Deporte"));
//		comercio5.agregarTag(new Tag("Calzado"));
//		comercio5.agregarHorarioComercio(new HorarioComercio(1,7,0,20,0));
//		comercio5.agregarHorarioComercio(new HorarioComercio(2,7,0,20,0));
//		comercio5.agregarHorarioComercio(new HorarioComercio(3,7,0,20,0));
//		comercio5.agregarHorarioComercio(new HorarioComercio(4,7,0,20,0));
//		comercio5.agregarHorarioComercio(new HorarioComercio(5,7,0,20,0));
//		fuenteMySql.agregarPuntoDeInteres(comercio5);
//
//		//
//		// ALTA DE PARADAS DE COLECTIVOS
//		//
//		ParadaColectivos parada1 = new ParadaColectivos("Venezuel 7911", new Direccion("Venezuel", "7911", 
//				null, null, "C1095AAO", localidadCABA, provinciaCABA, paisArgentina), 
//				new Coordenadas(-34.613909, -58.377360));
//		parada1.setDescripcion("Sede Comunal 2");
//		parada1.setBaja(false);
//		parada1.agregarTag(new Tag("8"));
//		parada1.agregarTag(new Tag("86"));
//		fuenteMySql.agregarPuntoDeInteres(parada1);
//		
//		ParadaColectivos parada2 = new ParadaColectivos("Parada2", new Direccion("Venezue", "79132", 
//				null, null, "C1095AAO", localidadCABA, provinciaCABA, paisArgentina), 
//				new Coordenadas(-34.613909, -58.377360));
//		parada2.setDescripcion("Sede Comunal 3");
//		parada2.setBaja(false);
//		parada2.agregarTag(new Tag("200"));
//		parada2.agregarTag(new Tag("103"));
//		parada2.agregarTag(new Tag("1"));
//		fuenteMySql.agregarPuntoDeInteres(parada2);
//		
//		ParadaColectivos parada3 = new ParadaColectivos("Venezuela 791", new Direccion("Venezuela", "791", 
//				null, null, "C1095AAO", localidadCABA, provinciaCABA, paisArgentina), 
//				new Coordenadas(-34.613909, -58.377360));
//		parada3.setDescripcion("Sede Comunal 1");
//		parada3.setBaja(false);
//		parada3.agregarTag(new Tag("2"));
//		parada3.agregarTag(new Tag("103"));
//		fuenteMySql.agregarPuntoDeInteres(parada3);
//		
//		ParadaColectivos parada4 = new ParadaColectivos("Venezuela 991", new Direccion("Venezuela", "991", 
//				null, null, "C1095AAO", localidadCABA, provinciaCABA, paisArgentina), 
//				new Coordenadas(-34.613909, -58.377360));
//		parada4.setDescripcion("Sede Comunal 8");
//		parada4.setBaja(false);
//		parada4.agregarTag(new Tag("2"));
//		parada4.agregarTag(new Tag("103"));
//		fuenteMySql.agregarPuntoDeInteres(parada4);
//		
//		ParadaColectivos parada5 = new ParadaColectivos("Corrientes 1791", new Direccion("Corrientes", 
//				"1791", null, null, "C1095AAO", localidadCABA, provinciaCABA, paisArgentina), 
//				new Coordenadas(-34.613909, -58.377360));
//		parada5.setDescripcion("Colectivos Corrientes");
//		parada5.setBaja(false);
//		parada5.agregarTag(new Tag("24"));
//		parada5.agregarTag(new Tag("124"));
//		parada5.agregarTag(new Tag("26"));
//		fuenteMySql.agregarPuntoDeInteres(parada5);
//		
//		ParadaColectivos parada6 = new ParadaColectivos("Cordoba 2861", new Direccion("Cordoba", "2861", 
//				null, null, "C1095AAO", localidadCABA, provinciaCABA, paisArgentina), 
//				new Coordenadas(-34.613909, -58.377360));
//		parada6.setDescripcion("colectivos enfrente del kiosco");
//		parada6.setBaja(false);
//		parada6.agregarTag(new Tag("168"));
//		fuenteMySql.agregarPuntoDeInteres(parada6);
	}


	@After
	public void tearDown() throws Exception {
		emFactory.close();
	}
}
