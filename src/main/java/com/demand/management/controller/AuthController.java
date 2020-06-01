package com.demand.management.controller;

import com.demand.management.dto.login.LoginResBody;
import com.demand.management.dto.response.BaseResponse;
import com.demand.management.dto.login.LoginInfo;
import com.demand.management.dto.response.DataResponse;
import com.demand.management.dto.response.TokenResponseData;
import com.demand.management.dto.user.RelationUser;
import com.demand.management.service.AuthService;
import com.demand.management.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @GetMapping("")
    public DataResponse<RelationUser> auth(HttpServletRequest request) {
        Cookie token = CookieUtil.get(request, "10086ManageLoginToken");
        RelationUser user = this.service.verify(token.getValue());
        return new DataResponse<RelationUser>("验证成功", true, user);
    }

    @GetMapping("/logout")
    public BaseResponse logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie token = CookieUtil.get(request, "10086ManageLoginToken");
        if (token != null && !token.getValue().isEmpty()) {
            CookieUtil.set(response, "10086ManageLoginToken", token.getValue(), 0, true);
            return new BaseResponse("退出登录成功", true);
        } else {
            return new BaseResponse("用户并未登录", true);
        }
    }

    @Transactional
    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginResBody info, HttpServletResponse response) throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        // 设置密码
        if (info.getPassword() == null) return new BaseResponse("密码不正确，请再次输入", false);
        else loginInfo.setPassword(info.getPassword());
        // 判断登录的是手机号还是邮箱
        if (Pattern.matches("^\\d+$", info.getAccount())) {
            loginInfo.setMobile(info.getAccount());
        } else loginInfo.setEmail(info.getAccount());

        DataResponse<TokenResponseData> loginRes = this.service.login(loginInfo);
        BaseResponse res = new BaseResponse(loginRes.getMessage(), loginRes.isSuccess());
        // 登录成功
        if (loginRes.isSuccess() && loginRes.getMessage().equals("登录成功")) {
            CookieUtil.set(response, "10086ManageLoginToken", loginRes.getData().getToken(), 2 * 24 * 60 * 60, true);
        }
        return res;
    }
}
