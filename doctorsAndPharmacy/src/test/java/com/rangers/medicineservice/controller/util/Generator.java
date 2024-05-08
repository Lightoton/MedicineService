package com.rangers.medicineservice.controller.util;

public class Generator {

    public static String getNewPrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac5c9927-676f-4714-2357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ac5c9927-676f-4737-9357-f52cbb9b4a95\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }

    public static String getWrongPrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac5c9927-676f-4714-2357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ac5c9927-676f-4737-9357-f52cbb9b4a96\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }
    public static String getExpiredPrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac5c8867-676f-4754-1357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ac5c9927-676f-4737-9357-f52cbb9b4a96\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }
    public static String getInactivePrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac5c7767-676f-4754-1357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ac5c9927-676f-4737-9357-f52cbb9b4a96\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }
    public static String getEmptyPrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac4c4467-676f-4754-1357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ac5c9927-676f-4737-9357-f52cbb9b4a96\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }
    public static String getNegativeBalancePrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac5c5567-676f-4754-1357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ac5c9927-676f-4737-9357-f52cbb9b4a96\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }
}
