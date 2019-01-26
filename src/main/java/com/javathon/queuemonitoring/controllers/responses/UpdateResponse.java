package com.javathon.queuemonitoring.controllers.responses;

public class UpdateResponse extends SuccessResponse {
    private int newSize;

    public UpdateResponse(int newSize) {
        this.newSize = newSize;
    }

    public int getNewSize() {
        return newSize;
    }
}
