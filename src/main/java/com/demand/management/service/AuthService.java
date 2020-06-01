package com.demand.management.service;

import com.demand.management.dto.user.JWTTokenUser;
import com.demand.management.dto.login.LoginInfo;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.TokenResponseData;
import com.demand.management.dto.user.RelationUser;

public interface AuthService {
    RelationUser verify(String token);
    String signIn(JWTTokenUser payload) throws Exception;
    RelationUser validateUser(JWTTokenUser payload);
    DataResponse<TokenResponseData> login(LoginInfo loginInfo) throws Exception;
}
