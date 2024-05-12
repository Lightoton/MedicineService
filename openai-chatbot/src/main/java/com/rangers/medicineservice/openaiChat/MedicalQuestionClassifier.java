package com.rangers.medicineservice.openaiChat;


import opennlp.tools.cmdline.doccat.DoccatModelLoader;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;

import java.io.File;

public class MedicalQuestionClassifier {

    private static final DoccatModel model;

    static {
        // Загрузка предварительно обученной модели для классификации текста
        model = new DoccatModelLoader().load(new File("path/to/medical-question-model.bin"));
    }

    public static boolean isMedicalQuestion(String question) {
        // Инициализация классификатора на основе загруженной модели
        DocumentCategorizer categorizer = new DocumentCategorizerME(model);

        // Преобразование входного вопроса в признаки для классификатора
        String[] tokens = question.split(" "); // Токенизация вопроса
        double[] probabilities = categorizer.categorize(tokens);

        // Определение категории вопроса
        String category = categorizer.getBestCategory(probabilities);

        // Возвращаем true, если категория вопроса связана с медициной
        return category.equals("medical");
    }
}

