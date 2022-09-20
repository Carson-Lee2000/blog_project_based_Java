package com.lks.blog.blog_project.config;

import com.lks.blog.blog_project.controller.interceptor.AlphaInterceptor;
import com.lks.blog.blog_project.controller.interceptor.LoginTicketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AlphaInterceptor alphaInterceptor;
    private final LoginTicketInterceptor loginTicketInterceptor;

    @Autowired
    public WebMvcConfig(AlphaInterceptor alphaInterceptor, LoginTicketInterceptor loginTicketInterceptor) {
        this.alphaInterceptor = alphaInterceptor;
        this.loginTicketInterceptor = loginTicketInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(alphaInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpeg")
                .addPathPatterns("/register", "/login");

        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpeg");
    }
}
