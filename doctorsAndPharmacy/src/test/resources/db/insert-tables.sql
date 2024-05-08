# --liquibase formatted sql
#
# --changeset liquibase:10

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

INSERT INTO users (user_id, first_name, last_name, email, phone_number, address, city, country, postal_code, chat_id, policy_number)
VALUES
    (UNHEX('ddb7ccab9f3d409da7ab9573061c6e29'), 'Hans', 'Anderson', 'test@email.com', '0151-333-44-55', 'Test address', 'Berlin', 'Germany', '11222', '001', '1234567890'),
    (UNHEX('ac5c8867676f4737931f052cbb9b4a59'), 'Mikle', 'Ivanov', 'wqw@kkk.com','0151','Oak Street 1','Berlin','Germany','10000','002','123321'),
    (UNHEX('ac5c8867676f4737931f052cbb9b4a11'), 'Dmitryi', 'Petrov', 'wqw@kkk.com','0152','Oak Street 2','Berlin','Germany','10000','003','12344321');

INSERT INTO schedules (schedule_id, doctor_id, user_id, date_time, status)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a95'), UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), UNHEX('ac5c8867676f4737931f052cbb9b4a59'), '2024-11-25 17:00:00', 'FREE'),
    (UNHEX('f4a7bf08de174195ac57fe251d9e15c2'), UNHEX('b585b9c08b7f493fb3c39018d3f8772d'),null, '2024-11-20 11:00:00', 'FREE'),
    (UNHEX('18d62c9dd8634bb2b7f4c1dcf692116e'), UNHEX('01f558a1736b4916b7e802a06c63ac7a'),null, '2024-11-23 10:00:00', 'FREE'),
    (UNHEX('1391e7dfbdf94faaa95fc6ea3cef7594'), UNHEX('01f558a1736b4916b7e802a06c63ac7a'),null, '2024-11-23 15:00:00', 'FREE'),
    (UNHEX('a18fc37a32ee44c0b1c1245859861055'), UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'),null, '2024-10-24 20:00:00', 'FREE'),
    (UNHEX('33c8d87947e64f719743fd83c2983fe2'), UNHEX('8bda13952ee34aee80c1842bedd9f4c1'),null, '2024-11-12 15:15:00', 'FREE');

INSERT INTO prescriptions (prescription_id, doctor_id,user_id,exp_date,created_at,is_active )
VALUES
    (UNHEX('ac5c9927676f47142357f52cbb9b4a95'),UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), UNHEX('ac5c8867676f4737931f052cbb9b4a59'),'2024-11-25 17:00:00','2023-11-25 17:00:00' ,true),
    (UNHEX('ac5c8867676f47541357f74cbb9b4a96'),UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), UNHEX('ac5c8867676f4737931f052cbb9b4a59'),'2024-11-25 17:00:00','2023-11-25 17:00:00', true);


INSERT INTO prescription_details(prescription_details_id,prescription_id,medicine_id,quantity)
VALUES (UNHEX('c5f4e7d8a1c3b6e2f7d9b3a5c6f4a2e7'),UNHEX('ac5c9927676f47142357f52cbb9b4a95'),UNHEX('ac5c8867676f4737931f052cbb9b4a84'),2),
       (UNHEX('b7d8a29f5e6f4874b3c2d1f4a9b6c7e5'),UNHEX('ac5c9927676f47142357f52cbb9b4a95'),UNHEX('b585b9c08b7f493fb3c39018d3f8773d'),1),
       (UNHEX('e7f3c6a9d5c4b2a1f8e5c6a3f7b2d9c4'),UNHEX('ac5c9927676f47142357f52cbb9b4a95'),UNHEX('01f558a1736b4916b7e802a06c63ac8a'),1),
       (UNHEX('d4e5f7b3c6a8f2c1a3e7f6b5c4d9e8a7'),UNHEX('ac5c9927676f47142357f52cbb9b4a95'),UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f15'),1),
       (UNHEX('a3c7e6f5b4a9b1f2e3f6c4d5a2b3c9f7'),UNHEX('ac5c9927676f47142357f52cbb9b4a95'),UNHEX('8bda13952ee34aee80c1842bedd9f4c2'),1);
