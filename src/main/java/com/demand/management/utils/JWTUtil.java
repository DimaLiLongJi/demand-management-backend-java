package com.demand.management.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demand.management.dto.user.JWTTokenUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    public static final String SECRET = "1AGy4bCUoECDZ4yI6h8DxHDwgj84EqStMNyab8nPChQ=";
    public static final Long EXPIRE = 7 * 24 * 60 * 60 * 1000L;

    public static String createToken(JWTTokenUser payload) throws Exception {
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        return JWT.create()
                .withHeader(map) // header
                .withClaim("email", payload.getEmail()) // payload
                .withClaim("mobile", payload.getMobile()) // payload
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE)) // expire time
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static Map<String, String> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //token 校验失败, 抛出Token验证非法异常
            e.printStackTrace();
        }
        assert jwt != null;
        Map<String, Claim> jwtMap = jwt.getClaims();
        Map<String, String> resultMap = new HashMap<>(jwtMap.size());
        jwtMap.forEach((k, v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }

    public static JWTTokenUser getUserByToken(String token) {
        Map<String, String> claims = verifyToken(token);
        String email = claims.get("email");
        String mobile = claims.get("mobile");
        JWTTokenUser userToken = new JWTTokenUser();
        userToken.setEmail(email);
        userToken.setMobile(mobile);
        return userToken;
    }
}
