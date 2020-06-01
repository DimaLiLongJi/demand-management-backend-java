package com.demand.management.dto.response;

import lombok.Data;

@Data
public class TokenResponseData {
    public String token;

    public TokenResponseData(String token) {
        this.token = token;
    }
}
