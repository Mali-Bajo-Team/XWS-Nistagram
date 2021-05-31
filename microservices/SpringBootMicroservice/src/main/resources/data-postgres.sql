insert into person (discriminator, address, city, country, name, surname, phone_number) values ('SystemAdmin', 'Marka Miljanova 7', 'Novi Sad', 'Srbija', 'Milana', 'Todorović', '0601452700');

insert into user_account (email, password, is_active, person_id) values ('admin@gmail.com', 'admin', true, 1);