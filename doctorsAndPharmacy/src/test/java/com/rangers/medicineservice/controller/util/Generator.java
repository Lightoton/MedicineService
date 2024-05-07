package com.rangers.medicineservice.controller.util;

public class Generator {
    public static String getNewPrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac5c8867-676f-4754-1357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ac5c9927-676f-4737-9357-f52cbb9b4a95\",\n" +
                "  \"expiryDate\": \"2024-11-25\",\n" +
                "  \"medicines\": [{\n" +
                "    \"medicineId\": \"b585b9c0-8b7f-493f-b3c3-9018d3f8773d\"\n" +
                "  },{\n" +
                "    \"medicineId\": \"b585b9c0-8b7f-493f-b3c3-9018d3f8773d\"\n" +
                "  },{\n" +
                "    \"medicineId\": \"ac5c8867-676f-4737-931f-052cbb9b4a84\"\n" +
                "  }]\n" +
                "}";
    }
}
