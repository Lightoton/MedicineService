package com.rangers.medicineservice.openaiChat;

import com.rangers.medicineservice.model.CompletionRequest;
import com.rangers.medicineservice.model.CompletionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class OpenaiRunner {

    @Value("${openai.key}")
    private String openaiApiKey;

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    public OpenaiRunner(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendMessage(String prompt) {

        restTemplate.getInterceptors().add(
                ((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization", "Bearer " + openaiApiKey);
                    return execution.execute(request, body);
                }));

        CompletionRequest request = new CompletionRequest("gpt-3.5-turbo", prompt);
        String openaiUrl = "https://api.openai.com/v1/chat/completions";
        CompletionResponse response = restTemplate
                .postForObject(openaiUrl, request, CompletionResponse.class);
        assert response != null;
        return response.getChoices().getFirst().getMessage().getContent();
    }
}
