package com.javathon.queuemonitoring.utils;

import com.javathon.queuemonitoring.model.App;
import com.javathon.queuemonitoring.model.google_response.PlacesResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class PlacesUtils {

    private static volatile String TOKEN = null;

    public static List<PlacesResponse.Candidate> searchPlaces (String name, double lat, double lon) {
        RestTemplate template = new RestTemplate();
        String query = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" +
                name +
                "&locationbias=point:" +
                lat +
                "," +
                lon +
                "&language=ru&fields=formatted_address,geometry,name&inputtype=textquery&key=" +
                getToken();
        PlacesResponse response = template.getForObject(query, PlacesResponse.class);
        if (response != null) {
            return response.candidates;
        } else {
            return null;
        }
    }

    private static String getToken() {
        String LOCAL_TOKEN = TOKEN;
        if (LOCAL_TOKEN == null) {
            synchronized (PlacesUtils.class) {
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
