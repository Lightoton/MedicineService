--liquibase formatted sql

--changeset liquibase:orders

INSERT INTO orders (order_id, prescription_id, user_id, pharmacy_id, order_date, status, order_cost, delivery_address)
VALUES
    (UNHEX('12d721446d6b4e8a9e8755680fd56c0a'), UNHEX('ac5c9927676f47142357f52cbb9b4a95'), UNHEX('ddb7ccab9f3d409da7ab9573061c6e29'), UNHEX('ac5c8867676f4737931f052cbb9b4a94'), '2024-11-20 11:00:00', 'CREATED', 200.00, 'Street');










