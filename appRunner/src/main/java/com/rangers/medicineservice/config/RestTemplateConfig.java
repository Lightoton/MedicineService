package com.rangers.medicineservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("sk-proj-oIXb5ZgVmYh09dtnoGcET3BlbkFJAPgtNFcUhHK5akBiS3H1")
    private String openaiApiKey;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(
                ((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization", "Bearer " + openaiApiKey);
                    return execution.execute(request, body);
                }));
        return restTemplate;
    }

    @Bean(name = "zoomRestTemplate")
    public RestTemplate zoomRestTemplate() {
        return new RestTemplate();
    }

}
