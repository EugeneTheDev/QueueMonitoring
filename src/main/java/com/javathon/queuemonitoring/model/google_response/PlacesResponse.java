package com.javathon.queuemonitoring.model.google_response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlacesResponse {

    public List<Candidate> candidates;

    public static class Candidate {

        @JsonProperty("formatted_address")
        public String address;
        public Geometry geometry;
        public String name;


        public static class Geometry {

            public Location location;

            public static class Location {

                public double lat;
                public double lng;
            }
        }
    }
}
