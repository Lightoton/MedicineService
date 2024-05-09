package com.rangers.medicineservice.controller.util;

public class Generator {

    public static String getNewPrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac5c1111-252f-4714-2357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ddb7ccab-9f3d-409d-a7ab-9573061c6e29\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }

    public static String getWrongPrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac5c1111-252f-4714-2357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ac5c8867-676f-4737-931f-052cbb9b4a59\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }
    public static String getExpiredPrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac5c8888-751f-4754-1357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ddb7ccab-9f3d-409d-a7ab-9573061c6e29\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }
    public static String getInactivePrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac5c7777-952f-4754-1357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ddb7ccab-9f3d-409d-a7ab-9573061c6e29\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }
    public static String getEmptyPrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac4c4444-176f-4754-1357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ddb7ccab-9f3d-409d-a7ab-9573061c6e29\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }
    public static String getNegativeBalancePrescription(){
        return "{\n" +
                "  \"prescriptionId\": \"ac5c5555-176f-4754-1357-f52cbb9b4a95\",\n" +
                "  \"userId\": \"ddb7ccab-9f3d-409d-a7ab-9573061c6e29\",\n" +
                "  \"deliveryAddress\": \"\"\n" +
                "}";
    }
}
