package com.hewak.blog._core.config;

import com.hewak.blog._core.interceptor.LoginInterceptor;
import com.hewak.blog._core.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebJaveConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**");  // 모든 URL 요청에서 동작 함

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/board/**","/user/**")

                .excludePathPatterns(
                        "/login-form",
                        "/join-form",
                        "/logout",

                        "/board/list",
                        "/",
                        "index",
                        "/board/{id:\\d+}",

                        "/css/**",
                        "/js/**",
                        "/images**",
                        "/favicon.ico",

                        "/h2-console/**"

                );
    }
}
