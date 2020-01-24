-- This creates the original database required for ideaLab
create sequence hibernate_sequence start 1 increment 1;
create table color_type (id  serial not null, created_at timestamp, updated_at timestamp, available boolean not null, color varchar(255) not null, primary key (id));
create table customer_info (id  serial not null, created_at timestamp, updated_at timestamp, email varchar(254) not null, first_name varchar(254) not null, last_name varchar(254) not null, primary key (id));
create table email_message (id  serial not null, created_at timestamp, updated_at timestamp, email_message varchar(255), status varchar(255) not null, primary key (id));
create table employee (id  serial not null, created_at timestamp, updated_at timestamp, first_name varchar(254) not null, last_name varchar(254) not null, password varchar(255) not null, role varchar(255) not null, username varchar(254) not null, primary key (id));
create table print_job (id  serial not null, created_at timestamp, updated_at timestamp, comments varchar(255), email_hash varchar(254), file_path varchar(254), file_sharable_link varchar(254), status varchar(255) not null, fk_color_type_id int4 not null, fk_customer_info_id int4 not null, fk_employee_id int4 not null, primary key (id));
create table print_job_aud (id int4 not null, rev int4 not null, revtype int2, comments varchar(255), email_hash varchar(255), file_path varchar(255), file_sharable_link varchar(255), status varchar(255), fk_color_type_id int4, fk_customer_info_id int4, fk_employee_id int4, primary key (id, rev));
create table queue (id  serial not null, created_at timestamp, updated_at timestamp, rank int8 not null, fk_print_job_id int4 not null, primary key (id));
create table revinfo (rev int4 not null, revtstmp int8, primary key (rev));
alter table if exists queue add constraint UK_kp5t2t9evuub0kg3qjp69b94c unique (fk_print_job_id);
alter table if exists print_job add constraint FK99v01thloyq28gjasmboxidob foreign key (fk_color_type_id) references color_type;
alter table if exists print_job add constraint FK3kqmfmhtcx6o4afnjm7hhj1d7 foreign key (fk_customer_info_id) references customer_info;
alter table if exists print_job add constraint FK9gyhrnjalygjlys5ax1lbh18t foreign key (fk_employee_id) references employee;
alter table if exists print_job_aud add constraint FKt12ekx6fgt7klxdgb9d5nawaw foreign key (rev) references revinfo;
alter table if exists queue add constraint FKsg4hqrp3p9kuww6pvxx11u3tk foreign key (fk_print_job_id) references print_job;
-- Create a list of base colors to be used
INSERT INTO color_type (color, available, created_at, updated_at) VALUES 
('#FFFFFF', true, current_timestamp, current_timestamp),
('#FFCC00', true, current_timestamp, current_timestamp),
('#008000', true, current_timestamp, current_timestamp),
('#6600CC', true, current_timestamp, current_timestamp),
('#CC0000', true, current_timestamp, current_timestamp),
('#CC6600', true, current_timestamp, current_timestamp),
('#FF0099', true, current_timestamp, current_timestamp),
('#40E0D0', true, current_timestamp, current_timestamp),
('#996633', true, current_timestamp, current_timestamp),
('#800080', true, current_timestamp, current_timestamp),
('#0000FF', true, current_timestamp, current_timestamp),
('#000000', true, current_timestamp, current_timestamp);
-- Create a base admin employee with a username admin and a password of password
INSERT INTO employee (first_name, last_name, "password", "role", username, created_at, updated_at) VALUES 
('admin','admin','$2a$10$riZO8iqbcCR1X/4fEgh0Au/VBNqgTOjVhZu9ZdlaMxztQEPYF2o8i','ADMIN','admin', current_timestamp, current_timestamp);
-- Create the email messages needed
INSERT INTO email_message (email_message, created_at, updated_at, status) VALUES
 ('Your print job failed.', current_timestamp, current_timestamp, 'FAILED'), 
 ('Your print job was completed.', current_timestamp, current_timestamp, 'COMPLETED');
