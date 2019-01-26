package com.javathon.queuemonitoring.controllers.responses;

public class BaseResponse {
    private boolean isSuccess;

    public BaseResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
