package com.javathon.queuemonitoring.controllers.responses;

import org.bson.Document;

import java.util.List;

public class AllPlacesResponse extends BaseResponse {

    private List<Document> places;

    public AllPlacesResponse(List<Document> places) {
        super(true);
        this.places = places;
    }

    public List<Document> getPlaces() {
        return places;
    }
}
