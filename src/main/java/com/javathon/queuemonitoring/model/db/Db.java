package com.javathon.queuemonitoring.model.db;



import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

/**
 * All interactions with db
 */
public class Db {
    private MongoCollection<Document> places;

    public Db(MongoCollection<Document> places){
        this.places = places;
    }

    /**
     * @return all places
     */
    public List<Document> getAllPlaces(){
        List<Document> placesList = places.find().into(new ArrayList<>());
        placesList.forEach(el -> el.remove("_id"));
        return placesList;
    }

    /**
     * @see com.javathon.queuemonitoring.model.App#updateInformation(long, int)
     */
    public void updateInformation(long id, int count){
        List<Integer> userData = places.find(eq("id", id))
                .first()
                .get("userData", ArrayList.class);

        if (userData.size() > 3) { // if we have enough trusted information
            userData.add(count);
            int averageQueue = 0;
            for (Integer el : userData) averageQueue+=el;
            averageQueue = averageQueue / userData.size() + 1;

            places.updateOne(
                    eq("id", id),
                    combine(
                            set("queue", averageQueue),
                            set("userData", Collections.EMPTY_LIST)
                    )

            );
        } else {
            userData.add(count);

            places.updateOne(
                    eq("id", id),
                    set("userData", userData)
            );
        }
    }

    /**
     * Location for some place
     * @param id place id
     * @return json object with "lat" and "lon" fields
     */
    public Document getLocation(long id){
        return places.find(eq("id", id)).first().get("location", Document.class);
    }



}
