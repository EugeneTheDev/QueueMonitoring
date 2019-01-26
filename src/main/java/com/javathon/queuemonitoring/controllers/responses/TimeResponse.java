package com.javathon.queuemonitoring.controllers.responses;

public class TimeResponse extends BaseResponse {

    private String time;

    public TimeResponse(String time) {
        super(true);
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
