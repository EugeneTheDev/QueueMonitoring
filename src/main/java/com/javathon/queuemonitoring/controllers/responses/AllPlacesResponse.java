package com.javathon.queuemonitoring.controllers.responses;

import org.bson.Document;

import java.util.List;

public class AllPlacesResponse extends SuccessResponse {

    private List<Document> places;

    public AllPlacesResponse(List<Document> places) {
        this.places = places;
    }

    public List<Document> getPlaces() {
        return places;
    }
}
