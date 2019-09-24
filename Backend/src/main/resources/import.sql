INSERT INTO email_hash (email_hash) VALUES ('hash1'),('hash2'),('hash3'),('hash4');

INSERT INTO color_type (color, available) VALUES ('blue', true),('green', false),('purple', true), ('red', true);

INSERT INTO customer_info (fk_email_hash_id, created_date,  first_name, last_name, email) VALUES (1, '2019-09-12', 'hannah', 'amundson', 'fake@gmail.com'), (2, '2019-07-12', 'john', 'frank', 'hi@gmail.com'),(3, '2019-09-12', 'josiah', 'taylor', 'hello@gmail.com'),(1, '2019-09-12', 'ben', 'arch', 'fake@gmail.com'),(2, '2019-09-12', 'haley', 'black', 'hi@gmail.com');

INSERT INTO employee (first_name, last_name, "password", "role", username) VALUES 
('hank','joseph','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','STAFF','hankjoseph'),
('hannah','amundson','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','STAFF','hannahamundson'),
('megan','forgey','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','STAFF','mforgey'),
('carol','hadley','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','ADMIN','carolhadley');

INSERT INTO print_job (status, fk_email_hash_id, comments, dropbox_sharable_link, dropbox_path, updated_at, created_at, fk_color_type_id, fk_employee_id) VALUES ('PENDING_REVIEW', 1, 'user comment', 'http://fakelink.com', '/fakepath', '2019-05-01','2019-03-01',1,1),('PRINTING', 2, 'user comment 2', 'http://what.com', '/whatpath','2018-05-01','2018-03-01',2,2),('PENDING_REVIEW',2, 'user comment 3', 'http://fake.com', '/fakepath','2018-04-01','2018-03-01',3,3),('PENDING_REVIEW',2, 'user comment 4', 'http://hi.com', '/hipath','2019-05-14','2019-03-12',4,4),('PENDING_REVIEW',3, 'user comment 6', 'http://dotcom.com', '/dotpath','2019-05-15','2019-03-10',2,2),('PENDING_REVIEW', 1, 'user comment 7', 'http://website.com', '/websitepath', '2019-05-23','2019-03-06',3,2),('PENDING_REVIEW', 3, 'user comment 8', 'http://site.com', '/sitepath','2019-05-27','2019-05-07',3,3),('PENDING_REVIEW', 4, 'user comment 9', 'http://web.com', '/webpath','2019-05-03','2019-04-08',4,1);

INSERT INTO print_status_audit (status_after, status_before, created_at, fk_employee_id, fk_print_model_id) VALUES ('Waiting', 'PENDING_REVIEW', '01/01/2019', 1, 1), ('PENDING_REVIEW', 'PRINTING', '01/02/2019', 2, 1), ('Waiting', 'PENDING_REVIEW', '01/01/2019', 1, 2), ('Complete', 'PENDING_REVIEW', '01/01/2019', 1, 3);

INSERT INTO queue ("rank", fk_print_job_id) VALUES (1,1),(2,3),(3,2);