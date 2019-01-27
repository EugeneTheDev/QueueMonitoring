package com.javathon.queuemonitoring.controllers.responses;

public class TimeResponse extends SuccessResponse {

    private String time;
    private int peopleInQueue;

    public TimeResponse(String time, int peopleInQueue) {
        this.time = time;
        this.peopleInQueue = peopleInQueue;
    }

    public String getTime() {
        return time;
    }

    public int getPeopleInQueue() {
        return peopleInQueue;
    }
}
