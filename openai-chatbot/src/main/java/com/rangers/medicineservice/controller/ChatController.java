package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.entity.PromptRequest;
import com.rangers.medicineservice.openaiChat.OpenaiRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class ChatController {

    private final OpenaiRunner openaiRunner;

    @Autowired
    public ChatController(OpenaiRunner openaiRunner) {
        this.openaiRunner = openaiRunner;
    }

    @PostMapping("/response")
    public String getAiResponse(@RequestBody PromptRequest promptRequest) {
        String prompt = promptRequest.getPrompt();
        return openaiRunner.sendMessage(prompt);
    }
}
