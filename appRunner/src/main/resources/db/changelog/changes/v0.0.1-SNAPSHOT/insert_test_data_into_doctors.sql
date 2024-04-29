--liquibase formatted sql

--changeset liquibase:2

INSERT INTO doctors (doctor_id, first_name, last_name, specialization, rating)
VALUES
    (UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), 'John', 'Doe', 'FAMILY_DOCTOR', '5'),
    (UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), 'Alice', 'Smith', 'FAMILY_DOCTOR', '4'),
    (UNHEX('01f558a1736b4916b7e802a06c63ac7a'), 'Michael', 'Johnson', 'THERAPIST', '4.5'),
    (UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), 'Emily', 'Brown', 'TRAUMATOLOGIST', '4.2'),
    (UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), 'David', 'Martinez', 'OCULIST', '4.8')












