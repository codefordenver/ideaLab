
INSERT INTO color_type (color, available, created_at, updated_at) VALUES 
('#FFFFFF', true, current_timestamp, current_timestamp),
('#AAD6E6', true, current_timestamp, current_timestamp),
('#0000FF', true, current_timestamp, current_timestamp),
('#00008B', true, current_timestamp, current_timestamp),
('#FF0000', true, current_timestamp, current_timestamp),
('#FF681F', true, current_timestamp, current_timestamp),
('#808080', true, current_timestamp, current_timestamp),
('#90EE90', true, current_timestamp, current_timestamp),
('#006400', false, current_timestamp, current_timestamp),
('#800080', false, current_timestamp, current_timestamp),
('#FFC0CB', false, current_timestamp, current_timestamp),
('#000000', false, current_timestamp, current_timestamp);

INSERT INTO customer_info (first_name, last_name, email, created_at, updated_at) VALUES
('hannah', 'amundson', 'fake@gmail.com', current_timestamp, current_timestamp),
('john', 'frank', 'hi@gmail.com', current_timestamp, current_timestamp),
('josiah', 'taylor', 'hello@gmail.com', current_timestamp, current_timestamp),
('ben', 'arch', 'fake@gmail.com', current_timestamp, current_timestamp),
('haley', 'black', 'hi@gmail.com', current_timestamp, current_timestamp);

INSERT INTO employee (first_name, last_name, "password", "role", username, created_at, updated_at) VALUES 
('hank','joseph','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','STAFF','hankjoseph', current_timestamp, current_timestamp),
('hannah','amundson','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','STAFF','hannahamundson', current_timestamp, current_timestamp),
('megan','forgey','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','STAFF','mforgey', current_timestamp, current_timestamp),
('carol','hadley','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','ADMIN','carolhadley', current_timestamp, current_timestamp);

INSERT INTO print_job (status, comments, file_sharable_link, file_path, fk_color_type_id, fk_employee_id, created_at, updated_at, fk_customer_info_id) VALUES
('PENDING_REVIEW', 'user comment', 'http://fakelink.com', '/fakepath', 1,1, current_timestamp, current_timestamp, 1),
('PRINTING', 'user comment 2', 'http://what.com', '/whatpath',2,2, current_timestamp, current_timestamp, 2),
('PRINTING', 'user comment 3', 'http://fake.com', '/fakepath',3,3, current_timestamp, current_timestamp, 3),
('PENDING_REVIEW', 'user comment 4', 'http://hi.com', '/hipath',4,4, current_timestamp, current_timestamp, 4),
('PENDING_REVIEW', 'user comment 6', 'http://dotcom.com', '/dotpath',5,2, current_timestamp, current_timestamp, 5),
('PENDING_REVIEW', 'user comment 7', 'http://website.com', '/websitepath', 6,2, current_timestamp, current_timestamp, 1),
('PENDING_REVIEW', 'user comment 8', 'http://site.com', '/sitepath',7,3, current_timestamp, current_timestamp, 2),
('FAILED', 'user comment 9', 'http://web.com', '/webpath',8,1, current_timestamp, current_timestamp, 3);

INSERT INTO queue ("rank", fk_print_job_id, created_at, updated_at) VALUES 
(1,1, current_timestamp, current_timestamp),
(2,3, current_timestamp, current_timestamp),
(3,2, current_timestamp, current_timestamp);

/*
 INSERT INTO revinfo (rev, revtstmp) VALUES
 (1, 1574735497536),
 (2, 1574735497536 + 1000*60),
 (3, 1574735497536 + 1000*60*2),
 (4, 1574735497536 + 1000*60*3),
 (5, 1574735497536 + 1000*60*4);

 INSERT INTO print_job_aud (id, rev, revtype, comments, email_hash, file_path, file_sharable_link, status, fk_color_type_id, fk_customer_info_id, fk_employee_id) VALUES
 (1, 1, 0, 'Initial Comment', 'e20a0a24179358c62654610000ca2b76', '/fake_audit_path_1', 'http://fake_audit_link.com', 'PENDING_REVIEW', 1, 6, 3),
 (2, 2, 1, 'Initial Comment', 'e20a0a24179358c62654610000ca2b76', '/fake_audit_path_1', 'http://fake_audit_link.com', 'PENDING_REVIEW', 1, 4, 4),
 (1, 3, 1, 'Updated Print Job Color', 'e20a0a24179358c62654610000ca2b76', '/fake_audit_path_1', 'http://fake_audit_link.com', 'PENDING_REVIEW', 4, 6, 3),
 (1, 4, 1, 'Completed Print Job Example', 'e20a0a24179358c62654610000ca2b76', '/fake_audit_path_1', 'http://fake_audit_link.com', 'COMPLETED', 4, 6, 3),
 (2, 5, 2, 'Deleted Print Job Example', 'e20a0a24179358c62654610000ca2b76', '/fake_audit_path_1', 'http://fake_audit_link.com', 'PENDING_REVIEW', 1, 4, 4);
 */