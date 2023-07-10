-- Insert data into the sender table
INSERT INTO sender (id, sender_code, sender_name)
VALUES (1, '603799', 'Bank Melli Iran');

INSERT INTO sender (id, sender_code, sender_name)
VALUES (2, '627648', 'Bank Saderat Iran');

INSERT INTO sender (id, sender_code, sender_name)
VALUES (3, '621986', 'Saman Bank');

INSERT INTO sender (id, sender_code, sender_name)
VALUES (4, '502229', 'Bank Pasargad');

INSERT INTO sender (id, sender_code, sender_name)
VALUES (5, '603770', 'Bank Keshavarzi Iran');

INSERT INTO sender (id, sender_code, sender_name)
VALUES (6, '589210', 'Bank Sepah');


-- Insert data into the person table
INSERT INTO person (id, name, family_name, national_code, phone_number, address)
VALUES (1, 'Sarah', 'Moghadam', '9654587454', '09301111111', 'Tehran,Iran');

INSERT INTO person (id, name, family_name, national_code, phone_number, address)
VALUES (2, 'Ahmad', 'babaee', '0029674125', '09121111111', 'Yazad,Iran');

INSERT INTO person (id, name, family_name, national_code, phone_number, address)
VALUES (3, 'Ziba', 'Danesh Pour', '1029674125', '09131111111', 'Isfehan,Iran');

INSERT INTO person (id, name, family_name, national_code, phone_number, address)
VALUES (4, 'Mohsen', 'Danaee', '0529674125', '09141111111', 'Kurdestan,Iran');

INSERT INTO person (id, name, family_name, national_code, phone_number, address)
VALUES (5, 'Asghar', 'Rezaee', '0929874125', '09101111111', 'Abadan,Iran');

INSERT INTO person (id, name, family_name, national_code, phone_number, address)
VALUES (6, 'Ghazaleh', 'Golami', '4028674125', '09381111111', 'Karaj,Iran');

INSERT INTO person (id, name, family_name, national_code, phone_number, address)
VALUES (7, 'Nima', 'Kordi', '6029674129', '09371111111', 'Mazandran,Iran');





-- Insert data into the card table
INSERT INTO card (id, name_card, card_type, expiration_date, status, person_id, sender_id)
VALUES (1, '6037996587436521', 0, '2028-12-31', true, 1, 1);

INSERT INTO card (id, name_card, card_type, expiration_date, status, person_id, sender_id)
VALUES (2, '6276485654654514', 1, '2024-06-30', true, 2, 2);

INSERT INTO card (id, name_card, card_type, expiration_date, status, person_id, sender_id)
VALUES (3, '6276481112589741', 0, '2024-03-29', true, 3, 2);

INSERT INTO card (id, name_card, card_type, expiration_date, status, person_id, sender_id)
VALUES (4, '6037709965412357', 0, '20245-09-03', true, 4, 5);

INSERT INTO card (id, name_card, card_type, expiration_date, status, person_id, sender_id)
VALUES (5, '6037998563241587', 0, '2025-02-01', true, 5, 1);

INSERT INTO card (id, name_card, card_type, expiration_date, status, person_id, sender_id)
VALUES (6, '6219865487965412', 1, '2026-08-04', true, 6, 3);

INSERT INTO card (id, name_card, card_type, expiration_date, status, person_id, sender_id)
VALUES (7, '5892108965414879', 0, '2028-02-13', true, 7, 6);

INSERT INTO card (id, name_card, card_type, expiration_date, status, person_id, sender_id)
VALUES (8, '5022298965414879', 0, '2027-02-13', true, 7, 4);