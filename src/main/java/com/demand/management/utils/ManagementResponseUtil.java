package com.demand.management.utils;

import com.demand.management.dto.response.BaseResponse;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.PageDataResponse;

import java.util.List;

public class ManagementResponseUtil {
    public static BaseResponse buildBaseResponse(String message, boolean success) {
        return new BaseResponse(message, success);
    }

    public static<T> DataResponse<T> buildDataResponse(String message, boolean success) {
        return new DataResponse<T>(message, success);
    }

    public static<T> DataResponse<T> buildDataResponse(String message, boolean success, T data) {
        return new DataResponse<T>(message, success, data);
    }

    public static<T> PageDataResponse<T> buildPageDataResponse(String message, boolean success) {
        return new PageDataResponse<T>(message, success);
    }

    public static<T> PageDataResponse<T> buildPageDataResponse(String message, boolean success, List<T> record, long total) {
        return new PageDataResponse<T>(message, success, record, total);
    }
}
