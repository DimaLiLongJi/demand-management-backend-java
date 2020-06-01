package com.demand.management.interceptor;

import com.demand.management.dto.response.BaseResponse;
import com.demand.management.dto.user.JWTTokenUser;
import com.demand.management.dto.user.RelationUser;
import com.demand.management.service.UserService;
import com.demand.management.utils.CookieUtil;
import com.demand.management.utils.JWTUtil;
import com.demand.management.utils.JsonUtil;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
public class JWTInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie token = CookieUtil.get(request, "10086ManageLoginToken");
        // 没有cookie
        if (Objects.isNull(token)) {
            buildErrorRes(response, "不存在token，请先登录");
            return false;
        }
        // cookie没有值
        String tokenValue = token.getValue();
        if (tokenValue.isEmpty()) {
            buildErrorRes(response, "token为空，请先登录");
            return false;
        };
        // 没有手机号和邮箱
        JWTTokenUser tokenUser = JWTUtil.getUserByToken(tokenValue);
        String mobile = tokenUser.getMobile();
        String email = tokenUser.getEmail();
        if (mobile.isEmpty() || email.isEmpty()) {
            buildErrorRes(response, "拦截器验证失败，该用户手机号或邮箱不存在");
            return false;
        }
        Map<String, Object> where = new HashMap<String, Object>();
        where.put("email", email);
        where.put("mobile", mobile);

        // @Service会在拦截器初始化之前，此刻service为null
        if (this.userService == null) {
            System.out.println("userService is null!!!");
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            // @Service开头字母小写
            this.userService = (UserService) factory.getBean("userServiceImpl");
        }

        RelationUser user = this.userService.findOneByWhere(where);
        // 如果是POST PUT GET方法创建的话，通过反射往请求头里塞入一个authId
        if (user != null) {
            if (request.getMethod().equals("POST") || request.getMethod().equals("PUT") || request.getMethod().equals("GET") ||request.getMethod().equals("DELETE")) {
                request.setAttribute("authId", user.getId());
//                有bug multipart/form-data 反射失败
//                this.reflectSetParam(request, "authId", user.getId());
            }
            return true;
        } else return false;
    }


    /**
     * 构建拦截器报错信息
     * @param response
     * @param message
     */
    private void buildErrorRes(HttpServletResponse response, String message) {
        OutputStream out;
        if (message == null) message = "拦截器已拦截，请求失败";
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            int status = response.getStatus();
            System.out.println("拦截器拦截到的相应状态 =>" + status);
            BaseResponse res = new BaseResponse(message, false);
            out.write(JsonUtil.BeanToJson(res).getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射，修改header信息，key-value键值对儿加入到header中
     * @param request
     * @param key
     * @param value
     */
    private void reflectSetParam(HttpServletRequest request, String key, String value){
        try {
            Class<? extends HttpServletRequest> requestClass = request.getClass();
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            System.out.println("coyoteRequest实现类="+o1.getClass().getName());
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders)headers.get(o1);
            o2.removeHeader(key);
            o2.addValue(key).setString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
