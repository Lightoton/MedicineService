package com.rangers.medicineservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompletionRequest {
    private String model;
    private List<ChatMassage> messages;
    @JsonProperty("temperature")
    private double temperature;
    @JsonProperty("top_p")
    private double topP;

    public CompletionRequest(String model, List<ChatMassage> messages, double temperature, double topP) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.topP = topP;
    }

}
