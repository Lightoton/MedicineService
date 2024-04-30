package com.rangers.medicineservice.openaiChat;

import com.rangers.medicineservice.model.CompletionRequest;
import com.rangers.medicineservice.model.CompletionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class OpenaiRunner {

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    public OpenaiRunner(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendMessage(String prompt) {
        CompletionRequest request = new CompletionRequest("gpt-3.5-turbo", prompt);
        String openaiUrl = "https://api.openai.com/v1/chat/completions";
        CompletionResponse response = restTemplate
                .postForObject(openaiUrl, request, CompletionResponse.class);
        assert response != null;
        return response.getChoices().getFirst().getMessage().getContent();
    }
}
