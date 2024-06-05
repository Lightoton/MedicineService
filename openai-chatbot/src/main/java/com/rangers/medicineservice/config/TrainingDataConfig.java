package com.rangers.medicineservice.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.entity.QuestionAnswer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

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

private static final String TRAINING_FILE_PATH ="training_file.json";

    /**
     * Bean for reading training data from a file and converting it to a list of QuestionAnswer objects.
     * @return List of QuestionAnswer objects or an empty list if an error occurs.
     */
    @Bean
    public List<QuestionAnswer> trainingData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassPathResource resource = new ClassPathResource(TRAINING_FILE_PATH);
            return objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
        } catch (IOException e) {

            return Collections.emptyList();
        }
    }
    }


