--liquibase formatted sql

--changeset liquibase:3

INSERT INTO medicines (medicine_id, medicine_name, description, price, category, pharmacy_id,quantity)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a84'), 'SleepEase', 'Gentle sleep aid with natural ingredients for restful sleep.', 19.99, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),10),
    (UNHEX('b585b9c08b7f493fb3c39018d3f8773d'), 'Amoxicillin', 'Commonly used antibiotic for bacterial infections.', 12.49, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),15),
    (UNHEX('01f558a1736b4916b7e802a06c63ac8a'), 'Claritin', 'Non-drowsy antihistamine for seasonal allergy relief.', 8.99, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),23),
    (UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f15'), 'Vitamin D3', 'Essential nutrient for bone health and immune function.', 14.79, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),18),
    (UNHEX('8bda13952ee34aee80c1842bedd9f4c2'), 'Ibuprofen', 'NSAID for pain relief, inflammation reduction, and fever lowering.', 9.29, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),45)
