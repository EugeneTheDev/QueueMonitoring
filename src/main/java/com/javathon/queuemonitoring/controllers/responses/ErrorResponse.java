package com.javathon.queuemonitoring.controllers.responses;

public class ErrorResponse extends BaseResponse {

    private String message;

    public ErrorResponse(String message) {
        super(false);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
