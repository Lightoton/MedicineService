package com.rangers.medicineservice.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for creating and setting up a RestTemplate bean
 * that adds the OpenAI API key to the Authorization header for all outgoing requests.
 * @author Maksym Bondarenko
 */
@Configuration
public class RestTemplateConfig {

    Dotenv dotenv = Dotenv.load();
    private final String openaiApiKey = dotenv.get("OPEN_AI_KEY");

    @Bean(name = "openai_restTemplate")
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
}