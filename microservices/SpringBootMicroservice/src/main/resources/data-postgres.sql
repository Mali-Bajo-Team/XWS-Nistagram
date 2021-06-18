-- All roles need to have prefix ROLE_ !!
INSERT INTO AUTHORITY (name) VALUES ('ROLE_REGULAR');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMINISTRATOR');
-- Password is 123456789
insert into user_account (discriminator, email, password, name, surname, username, date_of_birth, gender, status, phone_number) values ('Administrator', 'admin@gmail.com', '$2a$10$..x6rUkBGlsSIx0dqhMYx.d1jYLx4dpdQQQCAq35ZOenqIBP38zfq', 'Miroslav', 'Mikic', 'miki123', '2021-02-03', 'M', 1, '062255256465');
insert into user_authority (user_id, authority_id) values (1, 2);
insert into privacy_settings(is_private, allow_messages_from_not_followed, allow_tags) values (false, true, true);
update user_account set privacy_settings_id = 1 where id = 1;


-- INSERT USER CATEGORY
insert into user_category(name) values ('influencer');
insert into user_category(name) values ('sports');
insert into user_category(name) values ('new/media');
insert into user_category(name) values ('business');
insert into user_category(name) values ('brand');
insert into user_category(name) values ('organization');