-- liquibase formatted sql

-- changeset liquibase:10

INSERT INTO doctors (doctor_id, first_name, last_name, specialization, rating)
VALUES
    (UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), 'John', 'Doe', 'FAMILY_DOCTOR', '5'),
    (UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), 'Alice', 'Smith', 'FAMILY_DOCTOR', '4'),
    (UNHEX('e9b8a3f4a5d84f6a9f2b1e5e6e6b1234'), 'Liam', 'Thomas', 'FAMILY_DOCTOR', '4.3'),
    (UNHEX('01f558a1736b4916b7e802a06c63ac7a'), 'Michael', 'Johnson', 'THERAPIST', '4.5'),
    (UNHEX('c5a1f9d5f62e4d66932b1a8e3439b547'), 'James', 'Anderson', 'THERAPIST', '4.9'),
    (UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), 'Emily', 'Brown', 'TRAUMATOLOGIST', '4.2'),
    (UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), 'David', 'Martinez', 'OCULIST', '4.8'),
    (UNHEX('d2b7b3f3b4144a748c5e2a1e3e7d9062'), 'Olivia', 'Taylor', 'OCULIST', '4.6'),
    (UNHEX('a7f5a6e92d0f4e7a96a456ae6b5b7893'), 'Sophia', 'Wilson', 'PEDIATRIC', '4.7'),
    (UNHEX('f1a9c4d5b6e94d7ba3d3e6f7e7e5c678'), 'Emma', 'Garcia', 'PEDIATRIC', '4.4');

INSERT INTO medicines (medicine_id, medicine_name, description, price, available_quantity, category, pharmacy_id)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a84'), 'SleepEase', 'Gentle sleep aid with natural ingredients for restful sleep.', 19.99, 50, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('b585b9c08b7f493fb3c39018d3f8773d'), 'Amoxicillin', 'Commonly used antibiotic for bacterial infections.', 12.49, 100, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('01f558a1736b4916b7e802a06c63ac8a'), 'Claritin', 'Non-drowsy antihistamine for seasonal allergy relief.', 8.99, 25, 'ANTIHISTAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f15'), 'Vitamin D3', 'Essential nutrient for bone health and immune function.', 14.79, 100, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('8bda13952ee34aee80c1842bedd9f4c2'), 'Ibuprofen', 'NSAID for pain relief, inflammation reduction, and fever lowering.', 9.29, 38, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('d3c5b8f123e74891a4c8d5e3b0f76123'), 'Tylenol', 'Acetaminophen for pain relief and fever reduction.', 7.49, 70, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('c4a1d8e2438a4b028a8d1c7f3f6e4123'), 'Zyrtec', 'Antihistamine for allergy relief, including sneezing and runny nose.', 10.99, 45, 'ANTIHISTAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('f2b6a7d4e8b14f5b9a1e8f7c4a3b5e13'), 'Metformin', 'Medication for type 2 diabetes management.', 15.99, 80, 'ANTIDIABETICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('e3b7c4d8a4e94b5f8a2d4b7c1a2e5f13'), 'Lisinopril', 'ACE inhibitor for hypertension and heart failure.', 9.89, 60, 'CARDIOVASCULAR', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('d4a1e8c2b4a74b3b8a1c8d5e3f4b2e13'), 'Omeprazole', 'Proton pump inhibitor for acid reflux and heartburn.', 11.29, 55, 'GASTROINTESTINAL', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('c3a5b7e4d8a14f6b9a2e8f1d4c3b2a12'), 'Crestor', 'Statin for lowering cholesterol levels.', 17.59, 30, 'CARDIOVASCULAR', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('b2c6d4e7a8b94f5b9a3e8f2d4b1c5e12'), 'Aspirin', 'Pain reliever and anti-inflammatory.', 6.99, 90, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('d1c6b7e4a8d14f5b9a2e8f3d4b2a5e12'), 'Albuterol', 'Bronchodilator for asthma and COPD.', 19.49, 25, 'RESPIRATORY', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('a4c7d8e2b4a84f7b9a2c8d5f3e2a4e12'), 'Synthroid', 'Hormone replacement for hypothyroidism.', 13.29, 65, 'HORMONES', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('b3a6d7e4c8a14b2b9a1e8f5d4c2b3a12'), 'Prednisone', 'Corticosteroid for inflammation and immune system suppression.', 16.49, 40, 'CORTICOSTEROIDS', UNHEX('ac5c8867676f4737931f052cbb9b4a94')),
    (UNHEX('c5b7a4e8d2a14f3b9a2d8f1c4b3e2a12'), 'Lipitor', 'Statin for lowering cholesterol and preventing cardiovascular disease.', 18.99, 50, 'CARDIOVASCULAR', UNHEX('ac5c8867676f4737931f052cbb9b4a94'));


INSERT INTO pharmacies (pharmacy_id, pharmacy_name, address, city, country, postal_code)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a94'), 'Healthy Pharmacy', '456 Oak Street', 'Berlin', 'Germany', '12345');

INSERT INTO users (user_id, first_name, last_name, email, phone_number, address, city, country, postal_code, chat_id, policy_number)
VALUES
    (UNHEX('ddb7ccab9f3d409da7ab9573061c6e29'), 'Hans', 'Anderson', 'sakovolga@live.com', '0151-333-44-55', 'Test address', 'Berlin', 'Germany', '11222', '001', '1234567890'),
    (UNHEX('ac5c8867676f4737931f052cbb9b4a59'), 'Mikle', 'Ivanov', 'wqw@kkk.com','0151','Oak Street 1','Berlin','Germany','10000','002','123321'),
    (UNHEX('ac5c8867676f4737931f052cbb9b4a11'), 'Dmitryi', 'Petrov', 'wqw@kkk.com','0152','Oak Street 2','Berlin','Germany','10000','003','12344321');

INSERT INTO schedules (schedule_id, doctor_id, user_id, date_time, status)
VALUES
    -- John Doe
    (UNHEX('ac5c8867676f4737931f052cbb9b4a95'), UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), null, '2024-07-01 09:00:00', 'FREE'),
    (UNHEX('b5a4a97b909d4b8aa6e32f4db47ea3c9'), UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), null, '2024-07-01 13:00:00', 'FREE'),
    (UNHEX('9f6b8a3c4d5f4c0e9e62a78b9d5a1a3e'), UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), null, '2024-07-01 17:00:00', 'FREE'),
    (UNHEX('f4a7bf08de174195ac57fe251d9e15c2'), UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), null, '2024-07-02 09:00:00', 'FREE'),
    (UNHEX('d5b6a97b123d4c5a8ae12f3eb47e23f1'), UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), null, '2024-07-02 13:00:00', 'FREE'),
    (UNHEX('e4b7a8c90bcd4d0f9e7a6b8d9c1f4a2b'), UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), null, '2024-07-02 17:00:00', 'FREE'),

    -- Alice Smith
    (UNHEX('a7d9a6571c6e4c5d9b7f9ea1c7f9e4d3'), UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), null, '2024-07-01 09:00:00', 'FREE'),
    (UNHEX('c7d9b5a6712c4d5b8ae13f2db48f34e0'), UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), null, '2024-07-01 13:00:00', 'FREE'),
    (UNHEX('d5b6e98b412d4c8e9f6b7a9d0c1a3e4f'), UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), null, '2024-07-01 17:00:00', 'FREE'),
    (UNHEX('e6c7b5a8791d4e0f8a6c5b8d9c3f1a2e'), UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), null, '2024-07-02 09:00:00', 'FREE'),
    (UNHEX('f8d9a6c7011b4d0e9c7b8a9d0c1f3e2a'), UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), null, '2024-07-02 13:00:00', 'FREE'),
    (UNHEX('e5c6a7b8913e4d0e9b7d8a9d0c1f2a3d'), UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), null, '2024-07-02 17:00:00', 'FREE'),

    -- Liam Thomas
    (UNHEX('e8f1b4c4e8f14b9d8c6f4e8b7d6e3d4f'), UNHEX('e9b8a3f4a5d84f6a9f2b1e5e6e6b1234'), null, '2024-07-01 09:00:00', 'FREE'),
    (UNHEX('f9e1c3d4e9f14c9d8c6f4e8b7d6e3d5a'), UNHEX('e9b8a3f4a5d84f6a9f2b1e5e6e6b1234'), null, '2024-07-01 13:00:00', 'FREE'),
    (UNHEX('e8c7a5b9013e4d8e9c6b7a8d9f1a2e3d'), UNHEX('e9b8a3f4a5d84f6a9f2b1e5e6e6b1234'), null, '2024-07-01 17:00:00', 'FREE'),
    (UNHEX('f9c8b7d8911a4d8e9b7c8a9d0f1a3e2d'), UNHEX('e9b8a3f4a5d84f6a9f2b1e5e6e6b1234'), null, '2024-07-02 09:00:00', 'FREE'),
    (UNHEX('e7b8a5c7013d4d8e9b7c8a9d0f1a2e3c'), UNHEX('e9b8a3f4a5d84f6a9f2b1e5e6e6b1234'), null, '2024-07-02 13:00:00', 'FREE'),
    (UNHEX('f6a7b5c8011c4e8e9c7b8a9d0f1a3e2b'), UNHEX('e9b8a3f4a5d84f6a9f2b1e5e6e6b1234'), null, '2024-07-02 17:00:00', 'FREE'),

    -- Michael Johnson
    (UNHEX('18d62c9dd8634bb2b7f4c1dcf692116e'), UNHEX('01f558a1736b4916b7e802a06c63ac7a'), null, '2024-07-01 09:00:00', 'FREE'),
    (UNHEX('1391e7dfbdf94faaa95fc6ea3cef7594'), UNHEX('01f558a1736b4916b7e802a06c63ac7a'), null, '2024-07-01 13:00:00', 'FREE'),
    (UNHEX('e7b8a9d6e5d94b8e9c7a8d6b9f1a2e3d'), UNHEX('01f558a1736b4916b7e802a06c63ac7a'), null, '2024-07-01 17:00:00', 'FREE'),
    (UNHEX('d8e9b6a7e5f94b8e9c7a8d6b9f1a2e4b'), UNHEX('01f558a1736b4916b7e802a06c63ac7a'), null, '2024-07-02 09:00:00', 'FREE'),
    (UNHEX('c7d8b6a7913d4e8e9b7a8d6c9f1a2e3a'), UNHEX('01f558a1736b4916b7e802a06c63ac7a'), null, '2024-07-02 13:00:00', 'FREE'),
    (UNHEX('e8b7a6d8011c4e8e9c7a8d6b9f1a2e3c'), UNHEX('01f558a1736b4916b7e802a06c63ac7a'), null, '2024-07-02 17:00:00', 'FREE'),

    -- James Anderson
    (UNHEX('a18fc37a32ee44c0b1c1245859861055'), UNHEX('c5a1f9d5f62e4d66932b1a8e3439b547'), null, '2024-07-01 09:00:00', 'FREE'),
    (UNHEX('b7c8a9d6712e4d9c8e7b6a9f8e1d3c4f'), UNHEX('c5a1f9d5f62e4d66932b1a8e3439b547'), null, '2024-07-01 13:00:00', 'FREE'),
    (UNHEX('e8c7a9d6912b4e8c9d7a6b8d0e1f3c2e'), UNHEX('c5a1f9d5f62e4d66932b1a8e3439b547'), null, '2024-07-01 17:00:00', 'FREE'),
    (UNHEX('d8e7a9d6912c4b9d8c7b6a8f9e1d3c2e'), UNHEX('c5a1f9d5f62e4d66932b1a8e3439b547'), null, '2024-07-02 09:00:00', 'FREE'),
    (UNHEX('c7e8a9d7013d4b8e9c7b6a8f9e1d3c2b'), UNHEX('c5a1f9d5f62e4d66932b1a8e3439b547'), null, '2024-07-02 13:00:00', 'FREE'),
    (UNHEX('e6b7a9d7012c4e8b9c7a6b8d0e1f3c2d'), UNHEX('c5a1f9d5f62e4d66932b1a8e3439b547'), null, '2024-07-02 17:00:00', 'FREE'),

    -- Emily Brown
    (UNHEX('b7d8e9f6913d4e8a9c7a6b8d0e1f2c3e'), UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), null, '2024-07-01 09:00:00', 'FREE'),
    (UNHEX('d8e7a9d7013d4b9d8c7a6b8d0e1f2c3a'), UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), null, '2024-07-01 13:00:00', 'FREE'),
    (UNHEX('e9b8a9d7012c4b8e9c7a6b8d0e1f2c3b'), UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), null, '2024-07-01 17:00:00', 'FREE'),
    (UNHEX('f8d9a8c7013d4b8e9c7a6b8d0e1f2c3c'), UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), null, '2024-07-02 09:00:00', 'FREE'),
    (UNHEX('e7d8a9d7012c4b8e9c7a6b8d0e1f2c3d'), UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), null, '2024-07-02 13:00:00', 'FREE'),
    (UNHEX('c8b7a9d7013d4e8e9c7a6b8d0e1f2c3e'), UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), null, '2024-07-02 17:00:00', 'FREE'),

    -- David Martinez
    (UNHEX('e5b6a9d7013d4b8e9c7a6b8d0e1f2c3f'), UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), null, '2024-07-01 09:00:00', 'FREE'),
    (UNHEX('d8e7a9d7013d4b8e9c7a6b8d0e1f2c3a'), UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), null, '2024-07-01 13:00:00', 'FREE'),
    (UNHEX('f9b8a9d7013d4b8e9c7a6b8d0e1f2c3b'), UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), null, '2024-07-01 17:00:00', 'FREE'),
    (UNHEX('e8d7a9d7012c4b8e9c7a6b8d0e1f2c3c'), UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), null, '2024-07-02 09:00:00', 'FREE'),
    (UNHEX('f9e8a9d7012c4b8e9c7a6b8d0e1f2c3d'), UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), null, '2024-07-02 13:00:00', 'FREE'),
    (UNHEX('c8d7a9d7013d4b8e9c7a6b8d0e1f2c3e'), UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), null, '2024-07-02 17:00:00', 'FREE'),

    -- Olivia Taylor
    (UNHEX('d8c7a9d7013d4b8e9c7a6b8d0e1f2c3f'), UNHEX('d2b7b3f3b4144a748c5e2a1e3e7d9062'), null, '2024-07-01 09:00:00', 'FREE'),
    (UNHEX('f8c7a9d7013d4b8e9c7a6b8d0e1f2c3e'), UNHEX('d2b7b3f3b4144a748c5e2a1e3e7d9062'), null, '2024-07-01 13:00:00', 'FREE'),
    (UNHEX('e7c7a9d7013d4b8e9c7a6b8d0e1f2c3d'), UNHEX('d2b7b3f3b4144a748c5e2a1e3e7d9062'), null, '2024-07-01 17:00:00', 'FREE'),
    (UNHEX('f8d8a9d7013d4b8e9c7a6b8d0e1f2c3c'), UNHEX('d2b7b3f3b4144a748c5e2a1e3e7d9062'), null, '2024-07-02 09:00:00', 'FREE'),
    (UNHEX('e8c7a9d7012c4b8e9c7a6b8d0e1f2c3b'), UNHEX('d2b7b3f3b4144a748c5e2a1e3e7d9062'), null, '2024-07-02 13:00:00', 'FREE'),
    (UNHEX('c8b7a9d7013d4b8e9c7a6b8d0e1f2c3a'), UNHEX('d2b7b3f3b4144a748c5e2a1e3e7d9062'), null, '2024-07-02 17:00:00', 'FREE'),

    -- Sophia Wilson
    (UNHEX('a9d7e6e5f94b8c7e6f5d4c3b2a1f4c3'), UNHEX('a7f5a6e92d0f4e7a96a456ae6b5b7893'), null, '2024-07-01 09:00:00', 'FREE'),
    (UNHEX('b9d8e7e6e5f94b6a8d7e6f5d4c3b2a1e'), UNHEX('a7f5a6e92d0f4e7a96a456ae6b5b7893'), null, '2024-07-01 13:00:00', 'FREE'),
    (UNHEX('c9e7d6e5f94b6c7e8d7e6f5d4c3b2a1f'), UNHEX('a7f5a6e92d0f4e7a96a456ae6b5b7893'), null, '2024-07-01 17:00:00', 'FREE'),
    (UNHEX('d8e7c6e5f94b8e9c7e6f5d4c3b2a1e4'), UNHEX('a7f5a6e92d0f4e7a96a456ae6b5b7893'), null, '2024-07-02 09:00:00', 'FREE'),
    (UNHEX('c8e7d6e5f94b8e9c7e6f5d4c3b2a1f3'), UNHEX('a7f5a6e92d0f4e7a96a456ae6b5b7893'), null, '2024-07-02 13:00:00', 'FREE'),
    (UNHEX('b8e7d6e5f94b8e9c7e6f5d4c3b2a1e5'), UNHEX('a7f5a6e92d0f4e7a96a456ae6b5b7893'), null, '2024-07-02 17:00:00', 'FREE'),

    -- Emma Garcia
    (UNHEX('d9e7c6e5f94b8e9c7e6f5d4c3b2a1e5'), UNHEX('f1a9c4d5b6e94d7ba3d3e6f7e7e5c678'), null, '2024-07-01 09:00:00', 'FREE'),
    (UNHEX('c9d7e6e5f94b8e9c7e6f5d4c3b2a1e4'), UNHEX('f1a9c4d5b6e94d7ba3d3e6f7e7e5c678'), null, '2024-07-01 13:00:00', 'FREE'),
    (UNHEX('b9c7e6e5f94b8e9c7e6f5d4c3b2a1e3'), UNHEX('f1a9c4d5b6e94d7ba3d3e6f7e7e5c678'), null, '2024-07-01 17:00:00', 'FREE'),
    (UNHEX('a8c7e6e5f94b8e9c7e6f5d4c3b2a1e2'), UNHEX('f1a9c4d5b6e94d7ba3d3e6f7e7e5c678'), null, '2024-07-02 09:00:00', 'FREE'),
    (UNHEX('e7c6e5f94b8e9c7e6f5d4c3b2a1e1d5'), UNHEX('f1a9c4d5b6e94d7ba3d3e6f7e7e5c678'), null, '2024-07-02 13:00:00', 'FREE'),
    (UNHEX('d7c6e5f94b8e9c7e6f5d4c3b2a1e0c4'), UNHEX('f1a9c4d5b6e94d7ba3d3e6f7e7e5c678'), null, '2024-07-02 17:00:00', 'FREE');

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

INSERT INTO orders (order_id, prescription_id, user_id, pharmacy_id, order_date, status, order_cost, delivery_address)
VALUES
    (UNHEX('12d721446d6b4e8a9e8755680fd56c0a'), UNHEX('ac5c9927676f47142357f52cbb9b4a95'), UNHEX('ddb7ccab9f3d409da7ab9573061c6e29'), UNHEX('ac5c8867676f4737931f052cbb9b4a94'), '2024-11-20 11:00:00', 'CREATED', 200.00, 'Street'),
    (UNHEX('0e98b6da9cce4ca3a79d58c2c63f8406'), UNHEX('ac5c9927676f47142357f52cbb9b4a95'), UNHEX('ddb7ccab9f3d409da7ab9573061c6e29'), UNHEX('ac5c8867676f4737931f052cbb9b4a94'), '2024-11-18 11:00:00', 'CREATED', 300.00, 'Street');

INSERT INTO order_details (order_detail_id, medicine_id, quantity, order_id)
VALUES
    (UNHEX('47b680523de9466c8aad07be43efe66c'), UNHEX('ac5c8867676f4737931f052cbb9b4a84'), 2, UNHEX('12d721446d6b4e8a9e8755680fd56c0a')),
    (UNHEX('2d1221cd398540b6b0404341b7ec56bd'), UNHEX('b585b9c08b7f493fb3c39018d3f8773d'), 3, UNHEX('0e98b6da9cce4ca3a79d58c2c63f8406')),
    (UNHEX('231a379d06e64848afa1f6d4f8add74e'), UNHEX('01f558a1736b4916b7e802a06c63ac8a'), 1, UNHEX('0e98b6da9cce4ca3a79d58c2c63f8406'));