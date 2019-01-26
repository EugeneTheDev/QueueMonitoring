package com.javathon.queuemonitoring.controllers.responses;

public class TimeResponse extends SuccessResponse {

    private String time;

    public TimeResponse(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
