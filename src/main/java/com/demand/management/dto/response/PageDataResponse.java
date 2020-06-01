package com.demand.management.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageDataResponse extends BaseResponse {
    public List<Object> data = new ArrayList<Object>();

    public PageDataResponse(String message, boolean success) {
        super(message, success);
        this.message = message;
        this.success = success;
    }

    public <T>PageDataResponse(String message, boolean success, List<T> record, long total) {
        super(message, success);
        this.getData().add(record);
        this.getData().add(total);
    }
}
