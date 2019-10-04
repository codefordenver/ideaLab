INSERT INTO email_hash (email_hash, created_at, updated_at) VALUES 
('hash1', current_timestamp, current_timestamp),
('hash2', current_timestamp, current_timestamp),
('hash3', current_timestamp, current_timestamp),
('hash4', current_timestamp, current_timestamp);

INSERT INTO color_type (color, available, created_at, updated_at) VALUES 
('blue', true, current_timestamp, current_timestamp),
('green', false, current_timestamp, current_timestamp),
('purple', true, current_timestamp, current_timestamp), 
('red', true, current_timestamp, current_timestamp);

INSERT INTO customer_info (fk_email_hash_id, first_name, last_name, email, created_at, updated_at) VALUES
(1, 'hannah', 'amundson', 'fake@gmail.com', current_timestamp, current_timestamp), 
(2, 'john', 'frank', 'hi@gmail.com', current_timestamp, current_timestamp),
(3, 'josiah', 'taylor', 'hello@gmail.com', current_timestamp, current_timestamp),
(1, 'ben', 'arch', 'fake@gmail.com', current_timestamp, current_timestamp),
(2, 'haley', 'black', 'hi@gmail.com', current_timestamp, current_timestamp);

INSERT INTO employee (first_name, last_name, "password", "role", username, created_at, updated_at) VALUES 
('hank','joseph','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','STAFF','hankjoseph', current_timestamp, current_timestamp),
('hannah','amundson','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','STAFF','hannahamundson', current_timestamp, current_timestamp),
('megan','forgey','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','STAFF','mforgey', current_timestamp, current_timestamp),
('carol','hadley','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','ADMIN','carolhadley', current_timestamp, current_timestamp);

INSERT INTO print_job (status, fk_email_hash_id, comments, dropbox_sharable_link, dropbox_path, fk_color_type_id, fk_employee_id, created_at, updated_at) VALUES 
('PENDING_REVIEW', 1, 'user comment', 'http://fakelink.com', '/fakepath', 1,1, current_timestamp, current_timestamp),
('PRINTING', 2, 'user comment 2', 'http://what.com', '/whatpath',2,2, current_timestamp, current_timestamp),
('PENDING_REVIEW',2, 'user comment 3', 'http://fake.com', '/fakepath',3,3, current_timestamp, current_timestamp),
('PENDING_REVIEW',2, 'user comment 4', 'http://hi.com', '/hipath',4,4, current_timestamp, current_timestamp),
('PENDING_REVIEW',3, 'user comment 6', 'http://dotcom.com', '/dotpath',2,2, current_timestamp, current_timestamp),
('PENDING_REVIEW', 1, 'user comment 7', 'http://website.com', '/websitepath', 3,2, current_timestamp, current_timestamp),
('PENDING_REVIEW', 3, 'user comment 8', 'http://site.com', '/sitepath',3,3, current_timestamp, current_timestamp),
('PENDING_REVIEW', 4, 'user comment 9', 'http://web.com', '/webpath',4,1, current_timestamp, current_timestamp);

INSERT INTO print_status_audit (status_after, status_before, fk_employee_id, fk_print_model_id, created_at, updated_at) VALUES 
('Waiting', 'PENDING_REVIEW', 1, 1, current_timestamp, current_timestamp), 
('PENDING_REVIEW', 'PRINTING', 2, 1, current_timestamp, current_timestamp), 
('Waiting', 'PENDING_REVIEW', 1, 2, current_timestamp, current_timestamp), 
('Complete', 'PENDING_REVIEW', 1, 3, current_timestamp, current_timestamp);

INSERT INTO queue ("rank", fk_print_job_id, created_at, updated_at) VALUES 
(1,1, current_timestamp, current_timestamp),
(2,3, current_timestamp, current_timestamp),
(3,2, current_timestamp, current_timestamp);