package com.rangers.medicineservice.openaiChat;

import com.rangers.medicineservice.entity.QuestionAnswer;
import com.rangers.medicineservice.model.ChatMassage;
import com.rangers.medicineservice.model.CompletionRequest;
import com.rangers.medicineservice.model.CompletionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Component
public class OpenaiRunner {

    private final RestTemplate restTemplate;

    private final List<QuestionAnswer> trainingData;

    public OpenaiRunner(@Qualifier("openai_restTemplate") RestTemplate restTemplate, List<QuestionAnswer> trainingData) {
        this.restTemplate = restTemplate;
        this.trainingData = trainingData;
    }

    public String sendMessage(String prompt) {
        double temperature = 0.5;
        double topP = 0.3;

        List<ChatMassage> messages = new ArrayList<>();
        for (QuestionAnswer qa : trainingData) {
            messages.add(new ChatMassage("user", qa.getQuestion()));
            messages.add(new ChatMassage("assistant", qa.getAnswer()));
        }
        messages.add(new ChatMassage("user", prompt));

        CompletionRequest request = new CompletionRequest("gpt-3.5-turbo-0125", messages, temperature, topP);
        String openaiUrl = "https://api.openai.com/v1/chat/completions";
        CompletionResponse response = restTemplate
                .postForObject(openaiUrl, request, CompletionResponse.class);
        assert response != null;
        return response.getChoices().getFirst().getMessage().getContent();
    }
}
