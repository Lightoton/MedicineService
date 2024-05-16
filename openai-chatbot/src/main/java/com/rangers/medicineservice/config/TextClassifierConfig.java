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

@Configuration
public class TextClassifierConfig {

    @Value("${training.opennlp.data.path}")
    private String trainingDataPath;

    // Метод для обучения модели категоризации текста
    @Bean
    public DoccatModel trainModel() throws IOException {
        // Создаем список для хранения образцов документов
        List<DocumentSample> documentSamples = new ArrayList<>();

        // Чтение файла обучающих данных
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(trainingDataPath), StandardCharsets.UTF_8))) {
            String line;
            // Чтение каждой строки из файла
            while ((line = reader.readLine()) != null) {
                // Разделение строки на части по ", "
                String[] parts = line.split(", ");
                // Проверка наличия текста и метки категории в строке
                if (parts.length >= 2) {
                    // Получение текста и метки категории
                    String text = parts[0];
                    String category = parts[1];
                    // Преобразование текста в массив слов
                    String[] words = text.split(" ");
                    // Создание объекта DocumentSample и добавление его в список
                    documentSamples.add(new DocumentSample(category, words));
                }
            }
        }

        // Создание потока объектов DocumentSample из списка образцов документов
        ObjectStream<DocumentSample> sampleStream = new CollectionObjectStream<>(documentSamples);

        // Создание фабрики и параметров для обучения
        DoccatFactory factory = new DoccatFactory();
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.CUTOFF_PARAM, "5");

        // Обучение модели категоризации текста с помощью DocumentCategorizerME
        return DocumentCategorizerME.train("en", sampleStream, params, factory);
    }
}


