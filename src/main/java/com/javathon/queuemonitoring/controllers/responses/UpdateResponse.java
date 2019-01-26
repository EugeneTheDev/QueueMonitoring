package com.javathon.queuemonitoring.controllers.responses;

public class UpdateResponse extends BaseResponse {

    private String message;

    public UpdateResponse() {
        super(true);
        message = "Updated successful";
    }

    public String getMessage() {
        return message;
    }
}
