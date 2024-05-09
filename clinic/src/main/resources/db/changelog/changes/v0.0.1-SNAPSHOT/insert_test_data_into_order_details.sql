--liquibase formatted sql

--changeset liquibase:orders

INSERT INTO order_details (order_detail_id, medicine_id, quantity, order_id)
VALUES
    (UNHEX('47b680523de9466c8aad07be43efe66c'), UNHEX('ac5c8867676f4737931f052cbb9b4a84'), 2, UNHEX('12d721446d6b4e8a9e8755680fd56c0a'));






