-- All roles need to have prefix ROLE_ !!
INSERT INTO AUTHORITY (name) VALUES ('ROLE_REGULAR_USER');

insert into user_account (discriminator, email, password, name, surname, username, date_of_birth, gender, profile_status, phone_number) values ('RegularUser', 'admin@gmail.com', '12345', 'Miroslav', 'Mikic', 'miki123', '2021-02-03', 'M', 0, '062255256465');
insert into privacy_settings(is_private, allow_messages_from_not_followed, allow_tags, regular_user_id) values (false, true, true, 1);
update user_account set privacy_settings_id = 1 where id = 1;