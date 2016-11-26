//package com.edu.utn.infoba;
//
//import com.edu.utn.infoba.modelo.Banco;
//import com.edu.utn.infoba.modelo.CGP;
//import com.edu.utn.infoba.modelo.PuntoDeInteres;
//import com.edu.utn.infoba.modelo.RepositorioMongoDB;
//import com.edu.utn.utils.MongoDBConnection;
//import com.edu.utn.utils.PoisMySQL;
//import com.edu.utn.utils.UtilidadesRest;
//import org.mongodb.morphia.Datastore;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//public class BusquedaFuentesMultiples
//{
//    private static final String PERSISTENCE_UNIT_NAME = "infoba";
//    private EntityManagerFactory emFactory;
//    private FuenteDeDatos fuente;
//    private RepositorioMongoDB repoBusqueda; // Repositorio mongodb.
//    private Datastore datastore;
//
//    public BusquedaFuentesMultiples()
//    {
//        datastore = MongoDBConnection.getInstance().getDatastore();
//        emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//        fuente = new FuenteDeDatos(null,new PoisMySQL(emFactory.createEntityManager()));
//        repoBusqueda = new RepositorioMongoDB(datastore, new MockMailSender());
//    }
//
//    public Banco buscarBanco(String nombreABuscar)
//    {
//        Banco banco = null;
//
//        List<Banco> puntos  = repoBusqueda.buscarBancoPorNombre(nombreABuscar);
//
//        if(puntos.size() <= 0)
//        {
//            banco = (Banco) fuente.buscarPuntoDeInteresPorNombre(nombreABuscar);
//
//            if(banco == null)
//            {
//                ArrayList<Banco> bancos = obtenerBancosDeAPIRest();
//
//                // Necesitamos guardar los POI en la cache local (MongoDB) porque no existen.
//                guardarBancosEnCacheLocal(bancos);
//
//                for(PuntoDeInteres poiBanco : bancos)
//                {
//                    if(poiBanco.getNombre() == nombreABuscar)
//                    {
//                        banco = (Banco) poiBanco;
//                        break;
//                    }
//                }
//            }
//        }
//        else
//        {
//            banco = puntos.get(0);
//        }
//
//        return banco;
//    }
//
//
//    public CGP buscarCentro(String nombreABuscar)
//    {
//        CGP centro = null;
//
//        // 1) Fijarse si existen en nuestra cache local. De ser cierto, dar por finalizada la búsqueda.
//        List<CGP> puntos  = repoBusqueda.buscarCentroPorNombre(nombreABuscar);
//
//        // 2) Fijarse si existen en la DB.  De ser cierto, dar por finalizada la búsqueda.
//        if(puntos.size() <= 0)
//        {
//            centro = (CGP) fuente.buscarPuntoDeInteresPorNombre(nombreABuscar);
//
//            // 3) Si no se da ninguno de los casos anteriores, pegarle a la API rest.
//            if(centro == null)
//            {
//                ArrayList<CGP> centros = obtenerCentrosDeAPIRest();
//
//                //4) Necesitamos guardar los POI en la cache local (MongoDB).
//                guardarCentrosEnCacheLocal(centros);
//
//                for(PuntoDeInteres poiCentro : centros)
//                {
//                    if(poiCentro.getNombre() == nombreABuscar)
//                    {
//                        centro = (CGP) poiCentro;
//                        break;
//                    }
//                }
//            }
//        }
//        else
//        {
//            centro = puntos.get(0);
//        }
//
//        return centro;
//    }
//
//    private void guardarBancosEnCacheLocal(ArrayList<Banco> bancos)
//    {
//        for(Banco banco : bancos)
//        {
//            repoBusqueda.guardarBanco(banco);
//        }
//    }
//
//    private void guardarCentrosEnCacheLocal(ArrayList<CGP> centros)
//    {
//        for(CGP centro : centros)
//        {
//           repoBusqueda.guardarCentro(centro);
//        }
//    }
//
//    private ArrayList<Banco> obtenerBancosDeAPIRest()
//    {
//        //String query = "banco";
//
//        //Configuracion config = new Configuracion();
//        //String urlComponente = config.obtenerPropiedad("consultasURL");
//        //String apiURL = urlComponente + query;
//
//        //String respuesta = UtilidadesRest.leerDeURL(apiURL);
//        // Respuesta hardcodeada de la API rest.
//        String respuesta = "[{\"banco\":\"Galicia\",\"x\":9999.0,\"y\":9999.0,\"sucursal\":\"Flores\",\"gerente\":\"Roberto Gomez\",\"servicios\":[\"Plazo Fijo\",\"Acciones\",\"Cobros\",\"Pagos\"]},{\"banco\":\"Santander Rio\",\"x\":1234.0,\"y\":1234.0,\"sucursal\":\"Almagro\",\"gerente\":\"Lorenzo\",\"servicios\":[\"Moneda extranjera\",\"Cobros\",\"Pagos\"]}]";
//
//        // Parsear bancos
//        ArrayList<Banco> Bancos = UtilidadesRest.obtenerListaBancos(respuesta);
//
//        return Bancos;
//    }
//
//
//    private ArrayList<CGP> obtenerCentrosDeAPIRest()
//    {
//        //String query = "centros";
//
//        //Configuracion config = new Configuracion();
//        //String urlComponente = config.obtenerPropiedad("consultasURL");
//        //String apiURL = urlComponente + query;
//
//        //String respuesta = UtilidadesRest.leerDeURL(apiURL);
//        // Respuesta hardcodeada de la API rest.
//        String respuesta = "[{\"comuna\":7,\"zonas\":\"Flores, Parque Chacabuco\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":6,\"zonas\":\"Caballito\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":13,\"zonas\":\"Belgrano, Nuñez, Colegiales\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":3,\"zonas\":\"San Cristobal, Balvanera\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":8,\"zonas\":\"Villa Soldati, Villa Riachuelo, Villa Lugano\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":2,\"zonas\":\"Recoleta\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":4,\"zonas\":\"La Boca, Barracas, Parque Patricios, Nueva Pompeya\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":15,\"zonas\":\"Chacarita, Villa Crespo, Paterna, Villa Ortuzar, Agronomía, Parque Chas\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":1,\"zonas\":\"Retiro, San Telmo, San Nicolás, Puerto Madero, Monserrat, Constitución\",\"director\":\"Roberto Rodriguez\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[{\"nombre\":\"Atención ciudadana\",\"horarios\":[{\"diaSemana\":1,\"horaDesde\":9,\"minutosDesde\":0,\"horaHasta\":15,\"minutosHasta\":0}]}]},{\"comuna\":5,\"zonas\":\"Almagro, Boedo\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":11,\"zonas\":\"Villa General Mitre, Villa Devoto, Villa del Parque, Villa Santa Rita\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":9,\"zonas\":\"Parque Avellaneda, Liniers, Mataderos\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":10,\"zonas\":\"Villa Real, Montecastro, Versalles, Floresta, Velez Sarsfield, Villa Luro\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":14,\"zonas\":\"Palermo\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]},{\"comuna\":12,\"zonas\":\"Coghlan, Saavedra, Villa Urquiza, Villa Pueyrredón\",\"director\":\"\",\"domicilio\":\"\",\"telefono\":\"\",\"servicios\":[]}]";
//
//        // Parsear centros.
//        ArrayList<CGP> Centros = UtilidadesRest.obtenerListaCentros(respuesta);
//
//        return Centros;
//    }
//}
