package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.entity.PromptRequest;
import com.rangers.medicineservice.openaiChat.OpenaiRunner;
import com.rangers.medicineservice.service.interf.TextClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling AI-related HTTP requests.
 * It receives user prompts, classifies them using a text classification service,
 * and sends medical prompts to OpenAI for responses.
 * @author Maksym Bondarenko
 */
@RestController
@RequestMapping("/ai")
public class ChatController {

    private final TextClassificationService classificationService;
    private final OpenaiRunner openaiRunner;

    @Autowired
    public ChatController(TextClassificationService classificationService, OpenaiRunner openaiRunner) {
        this.classificationService = classificationService;
        this.openaiRunner = openaiRunner;
    }

    /**
     * Receives a user prompt, classifies it, and returns an AI-generated response.
     * @param promptRequest The request containing the user prompt.
     * @return The AI-generated response.
     */
    @PostMapping("/response")
    public String getAiResponse(@RequestBody PromptRequest promptRequest) {
        String prompt = promptRequest.getPrompt();

        String category = classificationService.classifyText(prompt);

        if (category.equals("medical")) {
            return openaiRunner.sendMessage(prompt);
        } else {
            return "Sorry, I cannot answer non-medical questions.";
        }
    }
}


