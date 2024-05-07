--liquibase formatted sql

--changeset liquibase:10

INSERT INTO users (user_id,first_name, last_name,email,phone_number,address,city,country,postal_code,chat_id,policy_number )
VALUES
    (UNHEX('ac5c9927676f47379357f52cbb9b4a95'), 'John','Bee', 'mail@gmail.com', '123456789', 'Street', 'Hamburg','DE','45967','14568','321654')
