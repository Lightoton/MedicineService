--liquibase formatted sql

--changeset liquibase:1



DROP TABLE IF EXISTS medicines_prescriptions;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS prescriptions;
DROP TABLE IF EXISTS order_details;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS medicines;
DROP TABLE IF EXISTS pharmacies;
DROP TABLE IF EXISTS doctors;

