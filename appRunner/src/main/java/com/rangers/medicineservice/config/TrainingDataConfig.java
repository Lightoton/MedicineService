package com.rangers.medicineservice.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.entity.QuestionAnswer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Configuration
public class TrainingDataConfig {

    @Value("${C:/Users/masya/Documents/MedicineService/openai-chatbot/src/main/resources/training_file.json}") // Путь к training_file
    private String trainingFilePath;

    @Bean
    public List<QuestionAnswer> trainingData() {
        // Чтение данных из training_file и преобразование в список QuestionAnswer
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(trainingFilePath);
            return objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

