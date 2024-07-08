# Medicine Service Project [Backend and Telegram Bot]

[ ⚡Here you can see the video presentation ](https://www.youtube.com/watch?v=WV-xZFAvjhg)

The Telegram Bot and RESTful Application are designed for your convenience and offer a wide range of healthcare services. 
Below you will find a description of all available features:

*Schedule an appointment with a doctor*
• You can schedule an appointment with any doctor at our clinic.
• Appointments are available both online and offline.

*Pharmacy*
• After visiting the doctor, you will receive a prescription that can be filled at our pharmacy.
• You can also order the medications you need without a prescription.
• Function to search for the nearest pharmacies by your geolocation.

*Ask a question to the artificial intelligence*
• The user can ask questions and get answers from our AI, which will help them make decisions about their health.

*Personal account*
• View all your appointments with doctors and prescriptions that have been issued to you, and view/change personal data.

*Development team:*
[Volha Zadziarkouskaya](https://www.linkedin.com/in/volha-zadziarkouskaya-84a1292b7/)
[Maksym Bondarenko](https://www.linkedin.com/in/maksym-bondarenko-8a6ba0280/?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app)
[Oleksandr Harbuz](https://www.linkedin.com/in/oleksandr-harbuz-1b9b41300)
[Oleksii Chilibiiskyi](https://www.linkedin.com/in/oleksii-chilibiiskyi/)
[Viktor Bulatov](https://www.linkedin.com/in/viktor-bulatov-46a54b30b/)

*Project curator:*
[Mikhail](https://www.linkedin.com/in/mikhail-egorov-54715917b/)

*We are constantly working to improve our bot and will be happy to receive your feedback and suggestions.*
___ 
* [ApiDoc Link](http://localhost:8080/swagger-ui/index.html)
___
### Database scheme of clinic

https://dbdiagram.io/d/Copy-of-MedicineService-663103e85b24a634d02f668f

## Database structure of clinic

### Table users (Users information)

| Column name        | Type          | Description                                   |
|--------------------|---------------|-----------------------------------------------|
| user_id            | binary(16)    | id key of row - unique, not null, primary key | 
| first_name         | varchar(255)  | first name of user                            |
| last_name          | varchar(255)  | last name of user                             |
| email              | varchar(255)  | email of user                                 |
| phone_number       | varchar(20)   | phone number of user                          |
| address            | varchar(255)  | address of user                               |
| city               | varchar(255)  | city of user                                  |                               
| country            | varchar(255)  | country of user                               |
| postal_code        | varchar(20)   | postal_code of user                           |                                
| chat_id            | varchar(255)  | chat_id of users telegram bot                 |
| policy_number      | varchar(255)  | policy number of user                         |

### Table doctors (Doctors performing appointments at the clinic)

| Column name            | Type         | Description                                   |
|------------------------|--------------|-----------------------------------------------|
| doctor_id              | binary(16)   | id key of row - unique, not null, primary key |
| first_name             | varchar(255) | first name of doctor in clinic                |
| last_name              | varchar(255) | last name of doctor in clinic                 |
| specialization         | varchar(255) | specialization of doctor                      |
| rating                 | decimal(3,1) | doctor's rating                               |

### Table pharmacies (Pharmacies selling medicines)

| Column name          | Type         | Description                                   |
|----------------------|--------------|-----------------------------------------------|
| pharmacy_id          | binary(16)   | id key of row - unique, not null, primary key |
| pharmacy_name        | varchar(255) | name of pharmacy                              | 
| address              | varchar(255) | address of pharmacy                           | 
| city                 | varchar(255) | city of pharmacy                              | 
| country              | varchar(255) | country of pharmacy                           | 
| postal_code          | varchar(20)  | postal code office address of pharmacy        | 

### Table medicines (Medicines for sale)

| Column name         | Type         | Description                                   |
|---------------------|--------------|-----------------------------------------------|
| 	medicine_id        | binary(16)   | id key of row - unique, not null, primary key | 
| 	medicine_name      | varchar(255) | name of medicine                              | 
| 	description        | text         | description of medicine                       | 
| 	price              | decimal(8,2) | price of medicine                             | 
| 	category           | varchar(255) | category of medicine                          | 
| 	pharmacy_id        | binary(16)   | id of pharmacy where is the medicine          | 
| 	available_quantity | int          | available quantity                            | 


### Table schedules (Doctors appointment schedules)

| Column name         | Type         | Description                                   |
|---------------------|--------------|-----------------------------------------------|
| 	schedule_id        | binary(16)   | id key of row - unique, not null, primary key | 
| 	user_id            | binary(16)   | id of user                                    | 
| 	doctor_id          | binary(16)   | id of doctor                                  | 
| 	date_time          | datetime     | time of receipt                               | 
| 	status             | varchar(50)  | status of receipt                             | 
| 	type               | varchar(50)  | type of receipt (online,offline)              |
| 	link               | varchar(255) | link of zoom to contact the doctor            |

### Table cart_items (ordered medicines)

| Column name      | Type       | Description                                   |
|------------------|------------|-----------------------------------------------|
| cart_item_id     | binary(16) | id key of row - unique, not null, primary key |
| medicine_id      | binary(16) | ID of the ordering medicine                   |         
| user_id          | binary(16) | ID of user                                    |                               
| quantity         | int        | quantity of medicine                          | 

### Table order_details (Orders details)

| Column name     | Type         | Description                                   |
|-----------------|--------------|-----------------------------------------------|
| order_detail_id | binary(16)   | id key of row - unique, not null, primary key |
| medicine_id     | binary(16)   | ID of the ordered medicine                    |                                   
| quantity        | int          | quantity of medicine                          |                          
| order_id        | binary(16)   | ID of order                                   | 

### Table orders (Orders)

| Column name             | Type          | Description                                   |
|-------------------------|---------------|-----------------------------------------------|
| order_id                | binary(16)    | id key of row - unique, not null, primary key |
| prescription_id         | binary(16)    | ID of prescription                            |
| user_id                 | binary(16)    | ID of user                                    |
| pharmacy_id             | binary(16)    | ID of pharmacy                                |
| order_date              | datetime      | date of order                                 |
| status                  | varchar(50)   | status of order                               |
| order_cost              | decimal(10,2) | cost of order                                 |
| delivery_address        | varchar(255)  | delivery address                              |

### Table prescriptions (Prescriptions)

| Column name           | Type       | Description                                   |
|-----------------------|------------|-----------------------------------------------|
| prescription_id       | binary(16) | id key of row - unique, not null, primary key |
| doctor_id             | binary(16) | ID of doctor                                  |         
| user_id               | binary(16) | ID of user                                    |
| exp_date              | datetime   | prescription expiration date                  |
| created_at            | datetime   | recipe creation date                          |
| is_active             | boolean    | whether the prescription is valid or not      |

### Table prescription_details (Prescription details)

| Column name             | Type       | Description                                   |
|-------------------------|------------|-----------------------------------------------|
| prescription_details_id | binary(16) | id key of row - unique, not null, primary key |
| prescription_id         | binary(16) | ID of prescription                            |         
| medicine_id             | binary(16) | ID of medicine                                |
| quantity                | int        | quantity of medicine                          |
