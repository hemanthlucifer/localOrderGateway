package com.localOrder.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.localOrder.gateway.filter.TokenValidationFilter;



@Configuration
public class FilterConfig {

    @Bean
    public TokenValidationFilter requestFilter() {
        return new TokenValidationFilter();
    }
}

