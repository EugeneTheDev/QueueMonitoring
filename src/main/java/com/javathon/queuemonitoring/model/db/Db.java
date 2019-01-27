package com.javathon.queuemonitoring.model.db;



import com.javathon.queuemonitoring.utils.DistanceUtils;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.*;

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
    public List<Document> getAllPlaces(double userLat, double userLon) {
        List<Document> placesList = places.find().into(new ArrayList<>());
        placesList.forEach(el -> el.remove("_id"));

        placesList.sort((o1, o2) -> {
            double lat1 = o1.get("location", Document.class).getDouble("lat");
            double lon1 = o1.get("location", Document.class).getDouble("lon");
            double firstDistance = DistanceUtils.calculateStraightDistance(lat1, lon1, userLat, userLon);

            double lat2 = o2.get("location", Document.class).getDouble("lat");
            double lon2 = o2.get("location", Document.class).getDouble("lat");
            double secondDistance = DistanceUtils.calculateStraightDistance(lat2, lon2, userLat, userLon);

            return new Double(firstDistance - secondDistance).intValue();
        });
        return placesList;
    }

    /**
     * @see com.javathon.queuemonitoring.model.App#updateInformation(long, int)
     */
    public int updateInformation(long id, int userSize){
        Document doc = places.find(eq("id", id)).first();

        int currentSize = doc.getInteger("queueSize");
        int newSize = (int)(currentSize*0.7 + userSize*0.3) + 1;
        double currentSpeed = doc.getDouble("speed");
        long lastUpdated = doc.getLong("lastUpdated");
        long time = Calendar.getInstance().getTimeInMillis();

        double newSpeed = 0.5*currentSpeed + 0.5*60*1000*(userSize - currentSize)/(time - lastUpdated);


        places.updateOne(
                eq("id", id),
                combine(
                        set("queueSize", newSize),
                        set("speed", newSpeed)
                )

        );

        return newSize;
    }

    /**
     * Increment queue size
     * @param id place id
     */
    public void updateUserCome(long id){
        places.updateOne(
                eq("id", id),
                inc("queueSize", 1)
        );
    }


    /**
     * Place json object
     * @param id place id
     * @return json object with "lat" and "lon" fields
     */
    public Document getPlace(long id){
        return places.find(eq("id", id)).first();
    }



}
