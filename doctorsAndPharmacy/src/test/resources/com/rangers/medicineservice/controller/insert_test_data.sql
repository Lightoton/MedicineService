--liquibase formatted sql
--changeset liquibase:10

INSERT INTO users (user_id, first_name, last_name, email, phone_number, address, city, country, postal_code, chat_id, policy_number)
VALUES
    (UNHEX('ac5c9927676f47379357f52cbb9b4a95'), 'John', 'Bee', 'mail@gmail.com', '123456789', 'Street', 'Hamburg', 'DE', '45967', '14568', '321654');

INSERT INTO doctors (doctor_id, first_name, last_name, specialization, rating)
VALUES
    (UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), 'John', 'Doe', 'FAMILY_DOCTOR', 5),
    (UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), 'Alice', 'Smith', 'FAMILY_DOCTOR', 4),
    (UNHEX('01f558a1736b4916b7e802a06c63ac7a'), 'Michael', 'Johnson', 'THERAPIST', 4.5),
    (UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), 'Emily', 'Brown', 'TRAUMATOLOGIST', 4.2),
    (UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), 'David', 'Martinez', 'OCULIST', 4.8);


INSERT INTO medicines (medicine_id, medicine_name, description, price, category, pharmacy_id,quantity)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a84'), 'SleepEase', 'Gentle sleep aid with natural ingredients for restful sleep.', 19.99, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),10),
    (UNHEX('b585b9c08b7f493fb3c39018d3f8773d'), 'Amoxicillin', 'Commonly used antibiotic for bacterial infections.', 12.49, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),15),
    (UNHEX('01f558a1736b4916b7e802a06c63ac8a'), 'Claritin', 'Non-drowsy antihistamine for seasonal allergy relief.', 8.99, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),23),
    (UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f15'), 'Vitamin D3', 'Essential nutrient for bone health and immune function.', 14.79, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),18),
    (UNHEX('8bda13952ee34aee80c1842bedd9f4c2'), 'Ibuprofen', 'NSAID for pain relief, inflammation reduction, and fever lowering.', 9.29, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),45),
    (UNHEX('b3c7f6a2e3f4c5e6a7c8b2f1d3e6f4a9'), 'AllergyRelief', 'Quick antihistamine for allergy relief.', 21.99, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),25),
    (UNHEX('d5c4e6f7a9c2a3e5c3f7e8b1d3a6c7f4'), 'Vitamin D Supplement', 'High-dose vitamin D for bone health.', 14.95, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c5f6d7a3c4e3f7f8c1e6a9b3c2e5d6f4'), 'PainAway Joint', 'Pain reliever for muscle and joint pain.', 14.25, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c3f7e6f5d4a2b3c5e7f8a1c2b3e6f4d7'), 'AntibioticUltra Plus', 'Broad-spectrum antibiotic for severe infections.', 33.50, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('f6c3e7a9c4b3e5f4a7c6d3b2f6a9c1d7'), 'SleepWell Gentle', 'Natural sleep aid for gentle nights.', 17.85, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c5f7e6c3a2d3e4c7f6d7a9b1c3f6e7f8'), 'AllergyBlock Quick', 'Quick-acting antihistamine for allergy relief.', 23.40, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c3e7d5f6c4b2e3f4c5e7f6a2b3d6c7f8'), 'Vitamin B Complex Energy', 'Essential vitamin B for energy and metabolism.', 14.99, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('b2e7f6d3c5e6f4c7a3c2b1f7e5d4c6a9'), 'PainAway Headache', 'Quick pain relief for headaches.', 14.50, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('e7b3f4c5d3c6e5f7a1c2e7b6c4f3d8f6'), 'AntibioticMax Advanced', 'Advanced antibiotic for bacterial infections.', 32.50, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c5f4e3a2c3f6b1c7e8f6d3b3e7f8a9c4'), 'DreamEase Rest', 'Natural sleep aid for restful nights.', 18.55, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('e3f6c5a7c2e5f7a4c6d3b2e7f8a9c3f5'), 'AllergyStop Fast', 'Fast-acting antihistamine for quick relief.', 23.30, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c6f4d5e7b3c1f8a9b2c5e7a1d4f6c7a3'), 'Vitamin C Booster Immunity', 'High-dose vitamin C for immunity.', 14.99, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('f3e7c5a4c3f6e4d7c5b3f6d7a2c1e8b6'), 'PainAway Muscle', 'Quick pain relief for muscle and joint pain.', 14.25, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c6d7e4b5a3c3e7f8a2b1c7f6d5c3e6f4'), 'AntibioticUltra Advanced', 'Broad-spectrum antibiotic for various infections.', 33.50, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('e7b5c4a3f6c7d3c5e6f7a1b2c3f6d5c4'), 'SleepWell Natural', 'Sleep aid for natural and gentle nights.', 18.85, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c6f5e3a7c2c4f7e8f3c1b2c5d6f8c7d4'), 'AllergyBlock Gentle', 'Quick antihistamine for allergy relief.', 22.50, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c7d6e4b3c5e7a1c2f3b3a9c4d5e6c7f4'), 'Vitamin D Supplement Bone', 'Essential vitamin D for bone health.', 14.75, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c4e5d3f6c7b2a3c1e7f8f6d3b4f7e6f5'), 'PainAway Relief', 'Pain reliever for muscle and joint pain.', 14.50, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('e6f7c5a3b2c3e7f6a9c1c2f7b3e4c5f7'), 'AntibioticMax Severe', 'Advanced antibiotic for bacterial infections.', 32.99, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('a3c6f5e4b7d2f3e6c7f8b1c4d5e6f7a9'), 'DreamSleep Comfort', 'Natural sleep aid for restful nights.', 18.85, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('e4c6f7a2b1c5e7f6c3a9b3f4c7d8a2c1'), 'AllergyBlock QuickRelief', 'Fast-acting antihistamine for quick relief.', 23.40, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('b5c7e6f3a1c2e4f7c5b6d7c3a9e5f6b4'), 'Vitamin D Supplement HighDose', 'High-dose vitamin D for bone health.', 14.75, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c4e3f7d5a9c2b1c3e5f7a1c6d7f6b3e4'), 'PainAway Extra', 'Pain reliever for muscle and joint pain.', 14.25, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('e6f7d5c3a4c3f7e8f6a1b3d6c4f5e7b3'), 'AntibioticUltra Severe', 'Strong antibiotic for serious infections.', 33.50, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('f4c7e5d3a2c6e7f8b3a1c2e4f7c5d6b3'), 'SleepEase Gentle', 'Natural sleep aid for restful nights.', 17.95, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('b4c6f7d5a3c2e8f6d7c3b1a9c4f5e7c2'), 'AllergyRelief Fast', 'Quick antihistamine for fast relief.', 22.30, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c6e5f7d3c2e4f7f5a2c3e6f7b3c1f8d4'), 'Vitamin B Complex Focus', 'Vitamin B for energy and focus.', 14.99, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c5f4d3e6a7b3c1f8d5c4b6e7c3e6f7a2'), 'PainAway Rapid', 'Quick pain relief for headaches.', 14.25, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('b6f7e5c3a2c7e8f3c4d5a9b1c6e7f8d2'), 'AntibioticMax Bacterial', 'Advanced antibiotic for bacterial infections.', 32.99, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('e7f4c3d5a6c1e5f7f8c3b2a1c3e6b4c7'), 'SleepWell Restful', 'Gentle sleep aid for restful nights.', 18.55, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c6e7f3b4a9b3c5e7f8a2c4d5e6c3a1f2'), 'AllergyEase Effective', 'Effective antihistamine for allergy relief.', 22.40, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c5f7e4b2c3a6f7e8f3b1c2e5f4c7e6d5'), 'Vitamin C Booster Immune', 'High-dose vitamin C for immune system.', 14.75, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c4f7d6e3c5e6f5b3a1c2f7e4f8c6a3b2'), 'PainAway MuscleRelief', 'Pain reliever for muscle and joint pain.', 14.50, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c5f4d3e6b2a1c7f8f6c3e5d7b1a2f3c6'), 'AntibioticX Bacterial', 'Broad-spectrum antibiotic for bacterial infections.', 33.50, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('b7c6f5a3c2e6f7f8b3c1c4e7d2a5c6e9'), 'DreamEase Calm', 'Sleep aid for gentle and restful nights.', 17.85, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('e5f4d3c6b1c7e8f6a3c2d7e4f5a9c3b7'), 'AllergyBlock Rapid', 'Fast-acting antihistamine for quick relief.', 22.40, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c3e5d7c6b3a2e7f4a1c2d3e4c5f7f6a9'), 'Vitamin D Supplement Essential', 'Essential vitamin D for bone health.', 14.99, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('e7f6d5c4b2a3f7e6f3c1a9b3c5e7d8c2'), 'PainAway JointRelief', 'Pain reliever for muscle and joint pain.', 14.50, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c6f5d7e3b2a1c4f6e7f3b3a9c1c3e7f8'), 'AntibioticX Ultra', 'Broad-spectrum antibiotic for various infections.', 32.30, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c5e7f6a1b3c2e8f7f5c4a9c3d6e2b1c7'), 'SleepEase Peaceful', 'Gentle sleep aid for a restful night.', 18.85, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('a5f4c3e6a1c2e7f3d6b4c7e8f5b3c4a9'), 'AllergyBlock Swift', 'Quick antihistamine for allergy relief.', 22.40, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('e4c5f7d3a2c1e7f8b2c6a9f3b3c4d7e6'), 'Vitamin C Booster HighDose', 'High-dose vitamin C for immune system.', 14.45, 'VITAMINS_AND_SUPPLEMENTS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('f6c3a9b4d7e8c1a5f7c4b3e6d2f5a3c7'), 'PainAway HeadacheRelief', 'Quick pain relief for headaches.', 14.50, 'PAIN_RELIEVERS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('e6d5f4b3c7e7f8a1c2b3f5d7a4c6e3f8'), 'AntibioticUltra Plus', 'Advanced antibiotic for bacterial infections.', 33.50, 'ANTIBIOTICS', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('c7e6a3b4f7c2d6f8a9c5e3f7a1c2b3d5'), 'DreamEase Relax', 'Natural sleep aid for gentle nights.', 17.85, 'SEDATIVES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30),
    (UNHEX('f5c3e7d4a2c6e8f7b3c4d5e6f7c2b1a3'), 'AllergyStop FastActing', 'Fast-acting antihistamine for fast relief.', 23.40, 'ANTIHIAMINES', UNHEX('ac5c8867676f4737931f052cbb9b4a94'),30);

INSERT INTO pharmacies (pharmacy_id, pharmacy_name, address, city, country, postal_code)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a94'), 'Healthy Pharmacy', '456 Oak Street', 'Berlin', 'Germany', '12345');

INSERT INTO schedules (schedule_id, doctor_id, date_and_time, status, type, link)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a95'), UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), '2023-11-25 17:00:00', 'FREE', 'ONLINE', 'zoom_link_1'),
    (UNHEX('f4a7bf08de174195ac57fe251d9e15c2'), UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), '2023-11-24 18:00:00', 'IN_PROGRESS', 'OFFLINE', NULL),
    (UNHEX('18d62c9dd8634bb2b7f4c1dcf692116e'), UNHEX('01f558a1736b4916b7e802a06c63ac7a'), '2023-11-23 19:00:00', 'COMPLETED', 'ONLINE', 'google_meet_link_1'),
    (UNHEX('a18fc37a32ee44c0b1c1245859861055'), UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), '2023-11-24 20:00:00', 'FREE', 'OFFLINE', NULL),
    (UNHEX('33c8d87947e64f719743fd83c2983fe2'), UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), '2023-11-24 21:15:00', 'IN_PROGRESS', 'ONLINE', 'teams_link_1');

INSERT INTO prescriptions (prescription_id,medicine_id, doctor_id,user_id,exp_date,created_at,is_active )
VALUES
    (UNHEX('ac5c9927676f47142357f52cbb9b4a95'), UNHEX('ac5c8867676f4737931f052cbb9b4a84'),UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), UNHEX('ac5c9927676f47379357f52cbb9b4a95'),'2024-11-25 17:00:00','2023-11-25 17:00:00' ,true),
    (UNHEX('ac5c8867676f47541357f52cbb9b4a95'), UNHEX('b585b9c08b7f493fb3c39018d3f8773d'),UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), UNHEX('ac5c9927676f47379357f52cbb9b4a95'),'2024-11-25 17:00:00','2023-11-25 17:00:00', true),
    (UNHEX('ac5c8867676f47541357f74cbb9b4a96'), UNHEX('b585b9c08b7f493fb3c39018d3f8773d'),UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), UNHEX('ac5c9927676f47379357f52cbb9b4a95'),'2024-11-25 17:00:00','2023-11-25 17:00:00', false);
--     (UNHEX('ac5c8789676f47541357f85cbb9b4a97'), UNHEX('b585b9c08b7f493fb3c39018d3f8773d'),UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), UNHEX('ac5c9927676f47379357f52cbb9b4a95'),'2024-11-25 17:00:00','2023-11-25 17:00:00', true),
--     (UNHEX('ac5c8789676f47541357f85cbb9b4a97'), UNHEX('b585b9c08b7f493fb3c39018d3f8773d'),UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), UNHEX('ac5c9927676f47379357f52cbb9b4a95'),'2024-11-25 17:00:00','2023-11-25 17:00:00', true),
--     (UNHEX('ac5c8789676f47541357f85cbb9b4a97'), UNHEX('b585b9c08b7f493fb3c39018d3f8773d'),UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), UNHEX('ac5c9927676f47379357f52cbb9b4a95'),'2024-11-25 17:00:00','2023-11-25 17:00:00', true),
--     (UNHEX('ac5c8789676f47541357f85cbb9b4a97'), UNHEX('b585b9c08b7f493fb3c39018d3f8773d'),UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), UNHEX('ac5c9927676f47379357f52cbb9b4a95'),'2024-11-25 17:00:00','2023-11-25 17:00:00', true);