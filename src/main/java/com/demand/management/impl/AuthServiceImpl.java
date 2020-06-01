package com.demand.management.impl;

import com.demand.management.dto.user.JWTTokenUser;
import com.demand.management.dto.login.LoginInfo;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.TokenResponseData;
import com.demand.management.dto.user.RelationUser;
import com.demand.management.service.AuthService;
import com.demand.management.service.UserService;
import com.demand.management.utils.JWTUtil;
import com.demand.management.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;

    /**
     * 验证token
     * @param token
     * @return RelationUser
     */
    @Override
    public RelationUser verify(String token) {
        JWTTokenUser user = JWTUtil.getUserByToken(token);
        return this.validateUser(user);
    }

    /**
     * 生成签名
     * @param payload
     * @return String
     * @throws Exception
     */
    @Override
    public String signIn(JWTTokenUser payload) throws Exception {
        return  JWTUtil.createToken(payload);
    }

    /**
     * 验证用户是否存在
     * @param payload
     * @return RelationUser
     */
    @Override
    public RelationUser validateUser(JWTTokenUser payload) {
        Map<String, Object> where = new HashMap<String, Object>();
        where.put("email", payload.getEmail());
        where.put("mobile", payload.getMobile());
        return this.userService.findOneByWhere(where);
    }

    /**
     * 登录并生成一个token
     *
     * @param loginInfo
     * @return DataResponse<TokenResponseData>
     * @throws Exception
     */
    @Override
    public DataResponse<TokenResponseData> login(LoginInfo loginInfo) throws Exception {
        String email = loginInfo.getEmail();
        String mobile = loginInfo.getMobile();
        Map<String, Object> where = new HashMap<String, Object>();
        where.put("email", email);
        where.put("mobile", mobile);
        RelationUser user = this.userService.findOneByWhere(where);
        if (!Objects.isNull(user)) {
            String saltPassword = user.getSalt() + ":" +loginInfo.getPassword();
            String password = MD5Util.MD5Encode(saltPassword, "utf-8");
            if (user.getPassword().equals(password)) {
                if (Objects.isNull(user.getDeleteDate())) {
                    JWTTokenUser newTokenUser = new JWTTokenUser();
                    newTokenUser.setMobile(user.getMobile());
                    newTokenUser.setEmail(user.getEmail());
                    String token = this.signIn(newTokenUser);
                    // 验证之后返回token
                    return  new DataResponse<TokenResponseData>("登录成功", true, new TokenResponseData(token));
                } else {
                    // 用户被删除
                    return new DataResponse<TokenResponseData>("该用户已下架，无法登陆，请联系管理员", false);
                }
            } else {
                // 密码不对
                return new DataResponse<TokenResponseData>("密码不正确，请再次输入", false);
            }

        } else {
            // 用户不存在
            return new DataResponse<TokenResponseData>("该登录用户不存在", false);
        }
    }

}
