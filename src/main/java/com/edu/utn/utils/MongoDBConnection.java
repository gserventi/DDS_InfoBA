package com.edu.utn.utils;

import com.edu.utn.infoba.modelo.Busqueda;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoDBConnection {
	private static MongoDBConnection instance = null;
    private final Datastore datastore;

    public static MongoDBConnection getInstance() {
        if (instance == null) {
            instance = new MongoDBConnection();
        }
        return instance;
    }

    private MongoDBConnection(){
        final Morphia morphia = new Morphia();
        morphia.map(Busqueda.class);
        this.datastore = morphia.createDatastore(new MongoClient(), "infoba");
        datastore.ensureIndexes();
    }

    public Datastore getDatastore() {
        return this.datastore;
    }
}
