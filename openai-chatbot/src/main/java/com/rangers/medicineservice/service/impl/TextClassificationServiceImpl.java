package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.service.interf.TextClassificationService;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for text classification using a pre-trained DoccatModel.
 * This service classifies input text into predefined categories.
 */
@Service
public class TextClassificationServiceImpl implements TextClassificationService {

    private final DoccatModel model;

    @Autowired
    public TextClassificationServiceImpl(DoccatModel model) {
        this.model = model;
    }

    /**
     * Classifies the given text into a category using the trained DoccatModel.
     * @param text The text to classify.
     * @return The most probable category for the given text.
     */
    @Override
    public String classifyText(String text) {
        // Create an instance of DocumentCategorizerME for classifying text using the model
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        String[] words = text.split(" ");
        // Get the probabilities of each category (e.g., medical/non-medical)
        double[] probabilities = categorizer.categorize(words);
        // Return the most probable category
        return categorizer.getBestCategory(probabilities);
    }
}
