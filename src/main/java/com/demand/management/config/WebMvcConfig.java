package com.demand.management.config;

import com.demand.management.interceptor.JWTInterceptor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .excludePathPatterns(
                        "/static**",
                        "/api/auth/login",
                        "/api/auth/logout"
                )
                .addPathPatterns("/**");
    }

    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String basePath = ResourceUtils.getURL("").getPath() + "static/";
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:" + basePath);
    }
}
