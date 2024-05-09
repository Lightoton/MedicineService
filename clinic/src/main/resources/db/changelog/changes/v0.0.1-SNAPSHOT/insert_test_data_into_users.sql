--liquibase formatted sql

--changeset liquibase:users

INSERT INTO users (user_id, first_name, last_name, email, phone_number, address, city, country, postal_code, chat_id, policy_number)
VALUES
    (UNHEX('ddb7ccab9f3d409da7ab9573061c6e29'), 'Hans', 'Anderson', 'test@email.com', '0151-333-44-55', 'Test address', 'Berlin', 'Germany', '11222', '001', '1234567890'),
    (UNHEX('ac5c8867676f4737931f052cbb9b4a59'), 'Mikle', 'Ivanov', 'wqw@kkk.com','0151','Oak Street 1','Berlin','Germany','10000','002','123321'),
    (UNHEX('ac5c8867676f4737931f052cbb9b4a11'), 'Dmitryi', 'Petrov', 'wqw@kkk.com','0152','Oak Street 2','Berlin','Germany','10000','003','12344321');
