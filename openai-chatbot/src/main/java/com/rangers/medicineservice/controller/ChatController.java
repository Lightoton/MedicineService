package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.entity.PromptRequest;
import com.rangers.medicineservice.openaiChat.OpenaiRunner;
import com.rangers.medicineservice.service.interf.TextClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/response")
    public String getAiResponse(@RequestBody PromptRequest promptRequest) {
        String prompt = promptRequest.getPrompt();

        // Классифицируем вопрос
        String category = classificationService.classifyText(prompt);

        // Проверяем категорию и отправляем соответствующий ответ
        if (category.equals("medical")) {
            return openaiRunner.sendMessage(prompt);
        } else {
            return "Sorry, I cannot answer non-medical questions.";
        }
    }
}


