-- liquibase formatted sql

-- changeset liquibase:1

CREATE TABLE IF NOT EXISTS doctors
(
    doctor_id      BINARY(16) PRIMARY KEY,
    first_name     VARCHAR(255),
    last_name      VARCHAR(255),
    specialization VARCHAR(255),
    rating         DECIMAL(3, 1)
);

CREATE TABLE IF NOT EXISTS pharmacies
(
    pharmacy_id   BINARY(16) PRIMARY KEY,
    pharmacy_name VARCHAR(255),
    address       VARCHAR(255),
    city          VARCHAR(255),
    country       VARCHAR(255),
    postal_code   VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS medicines
(
    medicine_id        BINARY(16) PRIMARY KEY,
    medicine_name      VARCHAR(255),
    description        TEXT,
    price              DECIMAL(8, 2),
    category           VARCHAR(255),
    pharmacy_id        BINARY(16),
    available_quantity INT
);

CREATE TABLE IF NOT EXISTS users
(
    user_id       BINARY(16) PRIMARY KEY,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    email         VARCHAR(255),
    phone_number  VARCHAR(20),
    address       VARCHAR(255),
    city          VARCHAR(255),
    country       VARCHAR(255),
    postal_code   VARCHAR(20),
    chat_id       VARCHAR(255),
    policy_number VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS schedules
(
    schedule_id BINARY(16) PRIMARY KEY,
    user_id     BINARY(16),
    doctor_id   BINARY(16),
    date_time   DATETIME,
    status      VARCHAR(50),
    type        VARCHAR(50),
    link        VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id)
);

CREATE TABLE IF NOT EXISTS cart_items
(
    cart_item_id BINARY(16) PRIMARY KEY,
    medicine_id  BINARY(16),
    user_id      BINARY(16),
    quantity     INT,
    FOREIGN KEY (medicine_id) REFERENCES medicines (medicine_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS order_details
(
    order_detail_id BINARY(16) PRIMARY KEY,
    medicine_id     BINARY(16),
    quantity        INT,
    order_id        BINARY(16),
    FOREIGN KEY (medicine_id) REFERENCES medicines (medicine_id)
);

CREATE TABLE IF NOT EXISTS prescriptions
(
    prescription_id BINARY(16) PRIMARY KEY,
    doctor_id       BINARY(16),
    user_id         BINARY(16),
    exp_date        DATETIME,
    created_at      DATETIME,
    is_active       BOOLEAN,
    FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id         BINARY(16) PRIMARY KEY,
    prescription_id  BINARY(16),
    user_id          BINARY(16),
    pharmacy_id      BINARY(16),
    order_date       DATETIME,
    status           VARCHAR(50),
    order_cost       DECIMAL(10, 2),
    delivery_address VARCHAR(255),
    FOREIGN KEY (prescription_id) REFERENCES prescriptions (prescription_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (pharmacy_id) REFERENCES pharmacies (pharmacy_id)
);

CREATE TABLE IF NOT EXISTS prescription_details
(
    prescription_details_id BINARY(16) PRIMARY KEY,
    prescription_id         BINARY(16),
    medicine_id             BINARY(16),
    quantity                int,
    FOREIGN KEY (prescription_id) REFERENCES prescriptions (prescription_id),
    FOREIGN KEY (medicine_id) REFERENCES medicines (medicine_id)
);