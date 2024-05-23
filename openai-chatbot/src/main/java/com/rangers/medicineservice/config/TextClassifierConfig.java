package com.rangers.medicineservice.config;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.util.CollectionObjectStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.TrainingParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class for creating and training a text categorization model
 * using Apache OpenNLP. The model is trained with data provided in a specified file.
 */
@Configuration
public class TextClassifierConfig {

    @Value("${training.opennlp.data.path}")
    private String trainingDataPath;

    /**
     * Method for training a text categorization model.
     * @return Trained DoccatModel
     * @throws IOException If there is an issue reading the training data.
     */
    @Bean
    public DoccatModel trainModel() throws IOException {
        // Create a list to store document samples
        List<DocumentSample> documentSamples = new ArrayList<>();

        // Reading the training data file
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(trainingDataPath), StandardCharsets.UTF_8))) {
            String line;
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Split the line into parts by ", "
                String[] parts = line.split(", ");
                // Check if the line contains both text and category label
                if (parts.length >= 2) {
                    // Get the text and category label
                    String text = parts[0];
                    String category = parts[1];
                    // Split the text into an array of words
                    String[] words = text.split(" ");
                    // Create a DocumentSample object and add it to the list
                    documentSamples.add(new DocumentSample(category, words));
                }
            }
        }

        ObjectStream<DocumentSample> sampleStream = new CollectionObjectStream<>(documentSamples);

        // Create factory and parameters for training
        DoccatFactory factory = new DoccatFactory();
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.CUTOFF_PARAM, "5");

        // Train the text categorization model using DocumentCategorizerME
        return DocumentCategorizerME.train("en", sampleStream, params, factory);
    }
}
