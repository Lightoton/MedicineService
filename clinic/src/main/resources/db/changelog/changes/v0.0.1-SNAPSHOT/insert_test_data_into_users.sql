--liquibase formatted sql

INSERT INTO users (user_id, first_name, last_name, email, phone_number, address, city, country, postal_code, chat_id, policy_number)
VALUES
    (UNHEX('9e8a1b7053d34b1e8d71ace34f56aaf1'), 'John', 'Doe', 'john.doe@example.com', '1234567890', '123 Oak St', 'Springfield', 'USA', '12345', 'chat123', 'PN12345'),
    (UNHEX('7f63bc1e5b86490ca8e1244ed62aa4b2'), 'Alice', 'Smith', 'alice.smith@example.com', '2345678901', '456 Maple St', 'Greenfield', 'USA', '23456', 'chat234', 'PN23456'),
    (UNHEX('ac39d62568884f058b2ebe490a5015e0'), 'Michael', 'Johnson', 'michael.johnson@example.com', '3456789012', '789 Pine St', 'Riverside', 'USA', '34567', 'chat345', 'PN34567'),
    (UNHEX('b9821fcb27ab4a1a9a779e567e07f6a1'), 'Emily', 'Brown', 'emily.brown@example.com', '4567890123', '101 Ash St', 'Shelbyville', 'USA', '45678', 'chat456', 'PN45678'),
    (UNHEX('aadfb935dda94f82a6b19b3183c6ccbb'), 'David', 'Martinez', 'david.martinez@example.com', '5678901234', '323 Elm St', 'Centerville', 'USA', '56789', 'chat567', 'PN56789'),
    (UNHEX('ddb7ccab9f3d409da7ab9573061c6e29'), 'Hans', 'Anderson', 'test@email.com', '0151-333-44-55', 'Test address', 'Berlin', 'Germany', '11222', '001', '1234567890'),
    (UNHEX('ac5c8867676f4737931f052cbb9b4a59'), 'Mikle', 'Ivanov', 'wqw@kkk.com','0151','Oak Street 1','Berlin','Germany','10000','002','123321'),
    (UNHEX('ac5c8867676f4737931f052cbb9b4a11'), 'Dmitryi', 'Petrov', 'wqw@kkk.com','0152','Oak Street 2','Berlin','Germany','10000','003','12344321');
