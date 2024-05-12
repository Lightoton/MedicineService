--liquibase formatted sql

--changeset liquibase:24

INSERT INTO prescription_details(prescription_details_id,prescription_id,medicine_id,quantity)
VALUES (UNHEX('c5f4e7d8a1c3b6e2f7d9b3a5c6f4a2e7'),UNHEX('ac5c9927676f47142357f52cbb9b4a95'),UNHEX('ac5c8867676f4737931f052cbb9b4a84'),2),
       (UNHEX('e7f3c6a9d5c4b2a1f8e5c6a3f7b2d9c4'),UNHEX('ac5c9927676f47142357f52cbb9b4a95'),UNHEX('01f558a1736b4916b7e802a06c63ac8a'),1),
       (UNHEX('a3c7e6f5b4a9b1f2e3f6c4d5a2b3c9f7'),UNHEX('ac5c9927676f47142357f52cbb9b4a95'),UNHEX('8bda13952ee34aee80c1842bedd9f4c2'),1),
       (UNHEX('a5c3e6f5b4a9b1f2e3f6c4d5a2b3c9f7'),UNHEX('ac5c8867676f47541357f52cbb9b4a95'),UNHEX('8bda13952ee34aee80c1842bedd9f4c2'),1),
       (UNHEX('a1c1e6f2b4a9b1f2e3f6c4d5a2b3c9f7'),UNHEX('ac5c7767676f47541357f52cbb9b4a95'),UNHEX('8bda13952ee34aee80c1842bedd9f4c2'),1),
       (UNHEX('a4c3e6f2b4a9b1f2e3f6c4d5a2b3c9f7'),UNHEX('ac5c5567676f47541357f52cbb9b4a95'),UNHEX('8bda13952ee34aee80c1842bedd9f4c2'),1),
       (UNHEX('a4c5e6f2b6a9b1f2e3f6c4d5a2b3c9f7'),UNHEX('ac5c5567676f47541357f52cbb9b4a95'),UNHEX('b585b9c08b7f493fb3c39018d3f8773d'),1);