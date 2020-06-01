package com.demand.management.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataResponse<T> extends BaseResponse {

    public DataResponse(String message, boolean success) {
        super(message, success);
        this.message = message;
        this.success = success;
    }

    public DataResponse(String message, boolean success, T data) {
        super(message, success);
        this.data = data;
    }

    public T data;

}
