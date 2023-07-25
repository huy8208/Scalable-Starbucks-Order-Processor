package com.example.springcashier;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        // registry.addViewController("/starbucks").setViewName("starbucks");
        registry.addViewController("/register").setViewName("login");
        registry.addViewController("/access-denied").setViewName("access-denied");
        registry.addViewController("/about-us").setViewName("about-us");
    }

}
