package com.javathon.queuemonitoring.model;

import com.javathon.queuemonitoring.controllers.responses.SuccessResponse;
import com.javathon.queuemonitoring.controllers.responses.TimeResponse;
import com.javathon.queuemonitoring.controllers.responses.UpdateResponse;
import com.javathon.queuemonitoring.model.db.Db;
import com.javathon.queuemonitoring.controllers.responses.AllPlacesResponse;
import com.javathon.queuemonitoring.utils.DistanceUtils;
import com.javathon.queuemonitoring.utils.Pair;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * Main class
 */
@Component
public class App {

    private Db db;

    public App() {
        initDb();
    }

    /**
     * Initial events
     */
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

    /**
     * @return all places from db
     */
    public AllPlacesResponse getAllPlaces(){
        return new AllPlacesResponse(db.getAllPlaces());
    }

    /**
     * Update queue information for some place
     * @param id place id
     * @param userSize new userSize of peoples in queue
     * @return success response
     */
    public UpdateResponse updateInformation(long id, int userSize){
        return new UpdateResponse(db.updateInformation(id, userSize));
    }

    /**
     * Calculate time to go and predict queue size for some place
     * @param id place id
     * @param lat user latitude
     * @param lon user longitude
     * @return time to go and predicted queue size
     */
    public TimeResponse calculateTime(long id, double lat, double lon){
        Document place = db.getPlace(id);
        Document location = place.get("location", Document.class);
        Pair<String, Integer> time = DistanceUtils.calculateTimeToGo(lat, lon, location.getDouble("lat"),
                location.getDouble("lon"));
        int peopleInQueue = (int)(place.getInteger("queueSize") + time.getSecond()*place.getDouble("speed"));
        peopleInQueue = peopleInQueue > 0 ? peopleInQueue : 0;
        return new TimeResponse(time.getFirst(), peopleInQueue);
    }

    public SuccessResponse updateUserCome(long id){
        db.updateUserCome(id);
        return new SuccessResponse();
    }

}
