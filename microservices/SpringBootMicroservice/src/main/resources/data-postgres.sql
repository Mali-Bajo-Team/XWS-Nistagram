-- All roles need to have prefix ROLE_ !!
INSERT INTO AUTHORITY (name) VALUES ('ROLE_REGULAR');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMINISTRATOR');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_AGENT');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_INFLUENCER');
-- Password is 123456789
insert into user_account (discriminator, email, password, name, surname, username, date_of_birth, gender, status, phone_number) values ('Administrator', 'admin@gmail.com', '$2a$10$nshjokILtTylR5sz//j/3OowjgJOtRLk0Z8gI4E2/2QP.VtlDSeEa', 'Miroslav', 'Mikic', 'administrator', '2021-02-03', 'Man', 1, '062255256465');
-- Password for admin account is: administrator
insert into user_authority (user_id, authority_id) values (1, 2);
insert into privacy_settings(is_private, allow_messages_from_not_followed, allow_tags) values (false, true, true);
update user_account set privacy_settings_id = 1 where id = 1;


-- INSERT USER CATEGORY
insert into user_category(name) values ('Influencer');
insert into user_category(name) values ('Sports');
insert into user_category(name) values ('News/Media');
insert into user_category(name) values ('Business');
insert into user_category(name) values ('Brand');
insert into user_category(name) values ('Organization');