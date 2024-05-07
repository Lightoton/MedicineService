--liquibase formatted sql

--changeset liquibase:6

INSERT INTO schedules (schedule_id, doctor_id, date_time, status)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a95'), UNHEX('d1fd8b7990aa4f4aae0c8ae2069443e3'), '2024-11-25 17:00:00', 'FREE'),
    (UNHEX('ac5c8867676f4737931f052cbb9b4a94'), null, '2024-11-25 17:00:00', 'FREE'),
    (UNHEX('f4a7bf08de174195ac57fe251d9e15c2'), UNHEX('b585b9c08b7f493fb3c39018d3f8772d'), '2024-11-20 11:00:00', 'FREE'),
    (UNHEX('18d62c9dd8634bb2b7f4c1dcf692116e'), UNHEX('01f558a1736b4916b7e802a06c63ac7a'), '2024-11-23 10:00:00', 'FREE'),
    (UNHEX('a18fc37a32ee44c0b1c1245859861055'), UNHEX('5c51a0874b5f4d038ab12d2bc4fc2f14'), '2024-10-24 20:00:00', 'FREE'),
    (UNHEX('33c8d87947e64f719743fd83c2983fe2'), UNHEX('8bda13952ee34aee80c1842bedd9f4c1'), '2024-11-12 15:15:00', 'FREE');

INSERT INTO users (user_id, first_name, last_name, email)
VALUES
    (UNHEX('ac5c8867676f4737931f052cbb9b4a59'), 'Mikle', 'Ivanov', 'wqw@kkk.com')

