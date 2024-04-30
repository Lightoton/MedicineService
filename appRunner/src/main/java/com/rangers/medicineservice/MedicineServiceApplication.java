package com.rangers.medicineservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicineServiceApplication {

    @Value("${openai.key}")
    private String openaiApiKey;

    public static void main(String[] args) {
        SpringApplication.run(MedicineServiceApplication.class, args);
    }
}
