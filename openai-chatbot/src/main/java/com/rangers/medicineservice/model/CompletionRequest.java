package com.rangers.medicineservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompletionRequest {
    private String model;
    private List<ChatMassage> messages;

    public CompletionRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new ChatMassage("user", prompt));
    }
}
