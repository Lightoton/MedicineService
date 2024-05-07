--liquibase formatted sql

--changeset liquibase:users

INSERT INTO users (user_id, first_name, last_name, email, phone_number, address, city, country, postal_code, chat_id, policy_number)
VALUES
    (UNHEX('ddb7ccab9f3d409da7ab9573061c6e29'), 'Hans', 'Anderson', 'test@email.com', '0151-333-44-55', 'Test address', 'Berlin', 'Germany', '11222', '001', '1234567890')













