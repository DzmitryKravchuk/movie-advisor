package com.godeltech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/movie/movies").setViewName("/movie/movies");
        registry.addViewController("/movie/review").setViewName("/movie/review");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/admin/users").setViewName("/admin/users");
    }
}
