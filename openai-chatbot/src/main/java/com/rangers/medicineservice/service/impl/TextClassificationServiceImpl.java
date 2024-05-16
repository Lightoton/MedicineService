package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.service.interf.TextClassificationService;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextClassificationServiceImpl implements TextClassificationService {

    private final DoccatModel model;

    @Autowired
    public TextClassificationServiceImpl(DoccatModel model) {
        this.model = model;
    }

    @Override
    public String classifyText(String text) {
        // экземпляр класса DocumentCategorizerME - для классификации текста с использованием модели
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        String[] words = text.split(" ");
        // получение вероятности категории(medical/non-medical)
        double[] probabilities = categorizer.categorize(words);
        // возвращаем наиболее вероятную категорию
        return categorizer.getBestCategory(probabilities);
    }
}


