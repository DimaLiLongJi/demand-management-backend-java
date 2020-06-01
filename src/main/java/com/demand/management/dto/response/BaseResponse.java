package com.demand.management.dto.response;

import lombok.Data;

@Data
public class BaseResponse {
    public String message;
    public boolean success;

    public BaseResponse() {
    }

    public BaseResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
