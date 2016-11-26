package com.edu.utn.infoba.test;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mongodb.morphia.Datastore;

import com.edu.utn.utils.MongoDBConnection;
import com.edu.utn.infoba.MailSender;
import com.edu.utn.infoba.modelo.*;
import com.edu.utn.infoba.repositorios.RepositorioBusqueda;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMongoDBHistorialBusqueda
{
	private Busqueda busqueda;
	private Banco banco1;
	private Banco banco2;
    private Datastore datastore;
    private RepositorioBusqueda repoBusqueda;
    private MailSender mailSender;
    
    @Before
    public void setUp() throws Exception {
    	datastore = MongoDBConnection.getInstance().getDatastore();
    	repoBusqueda = new RepositorioBusqueda(datastore, mailSender);
    }

    @Test
    public void insertarDocumento() throws ParseException  {
     	banco1 = new Banco("ITAU Centro", null, new Coordenadas(22.0, 15.0));
     	banco2 = new Banco("ITAU Sur", null, new Coordenadas(22.0, 15.0));
        
    	busqueda = new Busqueda();
    	busqueda.setTexto("IT");
    	busqueda.setCantResultados(2);
    	busqueda.setDuracion((double) 1);
    	//busqueda.setPuntoDeInteresEncontrados(Arrays.asList(banco1,banco2));
    	busqueda.setUsuario("usuario1");
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Date d = sdf.parse("23/12/2012");
    	busqueda.setFechaBusqueda(d);
    	repoBusqueda.registrarBusqueda(busqueda);
    	List<Busqueda> items = repoBusqueda.buscarBusquedaPorTexto("IT");
    	assertTrue("No se encontro la busqueda de ITAU",items.size() > 0 );
    }
}
