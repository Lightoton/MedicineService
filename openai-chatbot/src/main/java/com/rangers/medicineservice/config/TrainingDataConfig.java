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

/**
 * Configuration class for loading training data from a file into a list of QuestionAnswer objects.
 * The training data file is for OpenAI model.
 * @author Maksym Bondarenko
 */
@Configuration
public class TrainingDataConfig {

    @Value("${training.openai.data.path}")
    private String trainingFilePath;

    /**
     * Bean for reading training data from a file and converting it to a list of QuestionAnswer objects.
     * @return List of QuestionAnswer objects or an empty list if an error occurs.
     */
    @Bean
    public List<QuestionAnswer> trainingData() {
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


