DELETE FROM ss_users_roles;
DELETE FROM ss_roles_system_roles;
DELETE FROM ss_system_roles_privileges;
DELETE FROM ss_users;
DELETE FROM ss_roles;
DELETE FROM ss_system_roles;
DELETE FROM ss_system_roles_privileges;
DELETE FROM ss_system_privileges;
DELETE FROM ss_system_elements;
DELETE FROM ss_subscribers;


INSERT INTO ss_subscribers(subs_id, enst_id) VALUES (1, 0);
-- the various user passwords are encoding using the SHA-256 algorithm, 1M iterations, with the indicated salt
-- unhashed passwords are: bill/gatesfoo, james/the_king, mary/bones, jack/paranoid

INSERT INTO ss_users(user_id, subs_id, enst_id, user_email, user_password, user_password_salt, user_firstname, user_surname, user_is_administrator, user_registration_date)
VALUES (1, 1, 0, 'bill', 'a603d7fea04d4ae942b9828c41c0f19761d8868a50046c53664a7b69bf357286', 'foo', 'Bill', 'Gates', false, now());

INSERT INTO ss_users(user_id, subs_id, enst_id, user_email, user_password, user_password_salt, user_firstname, user_surname, user_is_administrator, user_registration_date)
VALUES (2, 1, 0, 'james', 'd50ffcd0b397b3ddf3814b9256c8f3149ee5517d85b13bc9c967217a828483c0', 'foo', 'James', 'Stewart', false, now());

INSERT INTO ss_users(user_id, subs_id, enst_id, user_email, user_password, user_password_salt, user_firstname, user_surname, user_is_administrator, user_registration_date)
VALUES (3, 1, 0, 'mary', '553277b52de90ca45ed8a689a24019ba1eb31bee9555806c0cb8a1cc06c4feb5', 'foo', 'Mary', 'Bones', false, now());

INSERT INTO ss_users(user_id, subs_id, enst_id, user_email, user_password, user_password_salt, user_firstname, user_surname, user_is_administrator, user_registration_date)
VALUES (4, 1, 0, 'jack', '256179882f18c58616eeb27e28d04ccb31ccc6bb44a1a977a314415201682a2f', 'foo', 'Jack', 'Paranoid', false, now());


INSERT INTO ss_system_elements      (syel_id, syet_id, modu_id, syel_description) VALUES(1, 1, NULL, NULL);

INSERT INTO ss_system_privileges    (sypr_id, syel_id, sypr_name) VALUES ( 1, 1, 'secure');
INSERT INTO ss_system_privileges    (sypr_id, syel_id, sypr_name) VALUES ( 2, 1, 'can_cache');

INSERT INTO ss_system_roles         (syro_id, syro_name, syro_description) VALUES (1, 'staff'  , NULL);
INSERT INTO ss_system_roles         (syro_id, syro_name, syro_description) VALUES (2, 'cacher' , NULL);
INSERT INTO ss_system_roles         (syro_id, syro_name, syro_description) VALUES (3, 'admin'  , NULL);
INSERT INTO ss_system_roles         (syro_id, syro_name, syro_description) VALUES (4, 'user'   , NULL);

INSERT INTO ss_system_roles_privileges (syro_id, sypr_id) VALUES (1,  1);
INSERT INTO ss_system_roles_privileges (syro_id, sypr_id) VALUES (2,  2);
INSERT INTO ss_system_roles_privileges (syro_id, sypr_id) VALUES (3,  2);

INSERT INTO ss_roles (role_id, subs_id, enst_id, role_caption) VALUES (1, 1, 0, 'alpha-staff' );
INSERT INTO ss_roles (role_id, subs_id, enst_id, role_caption) VALUES (2, 1, 0, 'alpha-cacher');
INSERT INTO ss_roles (role_id, subs_id, enst_id, role_caption) VALUES (3, 1, 0, 'alpha-admin' );
INSERT INTO ss_roles (role_id, subs_id, enst_id, role_caption) VALUES (4, 1, 0, 'alpha-user'  );

INSERT INTO ss_roles_system_roles (role_id, syro_id) VALUES (1, 1);
INSERT INTO ss_roles_system_roles (role_id, syro_id) VALUES (2, 2);
INSERT INTO ss_roles_system_roles (role_id, syro_id) VALUES (3, 3);
INSERT INTO ss_roles_system_roles (role_id, syro_id) VALUES (4, 4);

INSERT INTO ss_users_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO ss_users_roles(user_id, role_id) VALUES (2, 3);
INSERT INTO ss_users_roles(user_id, role_id) VALUES (3, 4);
INSERT INTO ss_users_roles(user_id, role_id) VALUES (4, 2);