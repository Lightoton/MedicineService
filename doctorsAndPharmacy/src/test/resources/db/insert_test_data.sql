INSERT INTO doctors (doctor_id, first_name, last_name, specialization, rating)
VALUES
    (UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), 'John', 'Doe', 'FAMILY_DOCTOR', '5'),
    (UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), 'Alice', 'Smith', 'FAMILY_DOCTOR', '4'),
    (UNHEX('01f558a1736b4916b7e802a06c63ac7a'), 'Michael', 'Johnson', 'THERAPIST', '4.5'),
    (UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), 'Emily', 'Brown', 'TRAUMATOLOGIST', '4.2'),
    (UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), 'David', 'Martinez', 'OCULIST', '4.8');

INSERT INTO medicines (medicine_id, medicine_name, description, price, available_quantity, category, pharmacy_id)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a84'), 'SleepEase', 'Gentle sleep aid with natural ingredients for restful sleep.', 19.99, 50, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('b585b9c08b7f493fb3c39018d3f8773d'), 'Amoxicillin', 'Commonly used antibiotic for bacterial infections.', 12.49, 0, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('01f558a1736b4916b7e802a06c63ac8a'), 'Claritin', 'Non-drowsy antihistamine for seasonal allergy relief.', 8.99, 25, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f15'), 'Vitamin D3', 'Essential nutrient for bone health and immune function.', 14.79, 0, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('8bda13952ee34aee80c1842bedd9f4c2'), 'Ibuprofen', 'NSAID for pain relief, inflammation reduction, and fever lowering.', 9.29, 38, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'));


INSERT INTO pharmacies (pharmacy_id, pharmacy_name, address, city, country, postal_code)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a94'), 'Healthy Pharmacy', '456 Oak Street', 'Berlin', 'Germany', '12345');

INSERT INTO users (user_id, first_name, last_name, email)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a59'), 'Mikle', 'Ivanov', 'wqw@kkk.com'),
    (UNHEX('ac5c8867676f4737931f052cbb9b4a11'), 'Dmitryi', 'Petrov', 'wqw@kkk.com');


INSERT INTO schedules (schedule_id, user_id, doctor_id, date_time, status)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a95'), UNHEX('ac5c8867676f4737931f052cbb9b4a59'), UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), '2024-11-25 17:00:00', 'FREE'),
    (UNHEX('ac5c8867676f4737931f052cbb9b4a94'), null, null, '2024-11-25 17:00:00', 'FREE'),
    (UNHEX('f4a7bf08de174195ac57fe251d9e15c2'), null, UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), '2024-11-20 11:00:00', 'FREE'),
    (UNHEX('18d62c9dd8634bb2b7f4c1dcf692116e'), null, UNHEX('01f558a1736b4916b7e802a06c63ac7a'), '2024-11-23 10:00:00', 'FREE'),
    (UNHEX('a18fc37a32ee44c0b1c1245859861055'), null, UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), '2024-10-24 20:00:00', 'FREE'),
    (UNHEX('33c8d87947e64f719743fd83c2983fe2'), null, UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), '2024-11-12 15:15:00', 'FREE');



