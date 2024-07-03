--liquibase formatted sql

--changeset liquibase:4

INSERT INTO pharmacies (pharmacy_id, pharmacy_name, address, city, country, postal_code)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a94'), 'Healthy Pharmacy', '456 Oak Street', 'Berlin', 'Germany', '12345')
