INSERT INTO email_hash (email_hash) VALUES ('hash1'),('hash2'),('hash3'),('hash4');

INSERT INTO color_type (color, available) VALUES ('blue', true),('green', false),('purple', true), ('red', true);

INSERT INTO customer_info (fk_email_hash_id, first_name, last_name, email) VALUES (1, 'hannah', 'amundson', 'fake@gmail.com'), (2, 'john', 'frank', 'hi@gmail.com'),(3, 'josiah', 'taylor', 'hello@gmail.com'),(1, 'ben', 'arch', 'fake@gmail.com'),(2, 'haley', 'black', 'hi@gmail.com');

INSERT INTO employee (first_name, last_name, "password", "role", username) VALUES ('hank','joseph','sadfsdf','Staff','hankjoseph'),('hannah','amundson','sasdfadfdfsdf','Staff','hannahamundson'),('megan','forgey','sasdfsdfsdadfsdf','Staff','mforgey'),('carol','hadley','2sfkdjlsa','Admin','carolhadley')

INSERT INTO print_job (status, fk_email_hash_id, comments, dropbox_sharable_link, dropbox_path, updated_at, created_at, fk_color_type_id, fk_employee_id) VALUES ('Pending Review', 1, 'user comment', 'http://fakelink.com', '/fakepath', '2019-05-01','2019-03-01',1,1),('Printing', 2, 'user comment 2', 'http://what.com', '/whatpath','2018-05-01','2018-03-01',2,2),('Pending Review',2, 'user comment 3', 'http://fake.com', '/fakepath','2018-04-01','2018-03-01',3,3),('Pending Review',2, 'user comment 4', 'http://hi.com', '/hipath','2019-05-14','2019-03-12',4,4),('Pending Review',3, 'user comment 6', 'http://dotcom.com', '/dotpath','2019-05-15','2019-03-10',2,2),('Pending Review', 1, 'user comment 7', 'http://website.com', '/websitepath', '2019-05-23','2019-03-06',3,2),('Pending Review', 3, 'user comment 8', 'http://site.com', '/sitepath','2019-05-27','2019-05-07',3,3),('Pending Review', 4, 'user comment 9', 'http://web.com', '/webpath','2019-05-03','2019-04-08',4,1);

INSERT INTO print_status_audit (status_after, status_before, created_at, fk_employee_id, fk_print_model_id) VALUES ('Waiting', 'Pending Review', '01/01/2019', 1, 1), ('Pending Review', 'Printing', '01/02/2019', 2, 1), ('Waiting', 'Pending Review', '01/01/2019', 1, 2), ('Complete', 'Pending Review', '01/01/2019', 1, 3);

INSERT INTO queue ("rank", fk_print_job_id) VALUES (1,1),(2,3),(3,2);
