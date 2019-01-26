package com.javathon.queuemonitoring.model.db;



import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class Db {
    private MongoCollection<Document> places;

    public Db(MongoCollection<Document> places){
        this.places = places;
    }

    public List<Document> getAllPlaces(){
        List<Document> placesList = places.find().into(new ArrayList<>());
        placesList.forEach(el -> el.remove("_id"));
        return placesList;
    }
}
