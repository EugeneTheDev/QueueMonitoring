package com.javathon.queuemonitoring.model;

import com.javathon.queuemonitoring.model.db.Db;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
public class App {

    private Db db;

    public App() {
        initDb();
    }

    private void initDb(){
        Properties dbCredentials = new Properties();

        try{
            dbCredentials.load(App.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e){
            e.printStackTrace();
        }

        MongoClientURI uri  = new MongoClientURI(String.format("mongodb://%s:%s@%s:%s/%s",
                dbCredentials.getProperty("db-user"), dbCredentials.getProperty("db-password"),
                dbCredentials.getProperty("db-host"), dbCredentials.getProperty("db-port"),
                dbCredentials.getProperty("db-name")));

        MongoClient mongoClient = new MongoClient(uri);

        db = new Db(mongoClient.getDatabase(uri.getDatabase()).getCollection(dbCredentials.getProperty("db-places")));

    }

    public List<Document> getAllPlaces(){
        return db.getAllPlaces();
    }
}
