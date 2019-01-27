package com.javathon.queuemonitoring.utils;

import com.javathon.queuemonitoring.model.App;
import com.javathon.queuemonitoring.model.google_response.DistanceResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Properties;

public class DistanceUtils {

    private static volatile String TOKEN = null;

    /**
     * Calculates a straight line distance
     * @return distance between first and second points in meters
     */
    public static double calculateStraightDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    /**
     * Gets from google directions API
     * @return Pair(Time to go as string, time in minutes)
     */
    public static Pair<String, Integer> calculateTimeToGo(double lat1, double lon1, double lat2, double lon2) {
        RestTemplate template = new RestTemplate();
        String query = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
                lat1 +
                "," +
                lon1 +
                "&destinations=" +
                lat2 +
                "," +
                lon2 +
                "&language=ru&mode=walking&key=" +
                getToken();
        DistanceResponse response = template.getForObject(query, DistanceResponse.class);
        if (response != null && response.rows.get(0).elements.get(0).status.equals("OK")) {
            return new Pair(response.rows.get(0).elements.get(0).duration.text, response.rows.get(0).elements.get(0).duration.value / 60);
        } else {
            throw new IllegalStateException("Route not found");
        }
    }

    private static String getToken() {
        String LOCAL_TOKEN = TOKEN;
        if (LOCAL_TOKEN == null) {
            synchronized (DistanceUtils.class) {
                LOCAL_TOKEN = TOKEN;
                if (LOCAL_TOKEN == null) {
                    Properties dbCredentials = new Properties();
                    try {
                        dbCredentials.load(App.class.getClassLoader().getResourceAsStream("config.properties"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    TOKEN = LOCAL_TOKEN = dbCredentials.getProperty("distance-token");
                }
            }
        }

        return LOCAL_TOKEN;
    }
}