package com.rangers.medicineservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ZoomRestTemplateConfig {

    @Bean(name = "zoomRestTemplate")
    public RestTemplate zoomRestTemplate() {
        return new RestTemplate();
    }
}