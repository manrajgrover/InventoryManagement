INSERT INTO user (id, name, email, created_timestamp) VALUES (1,'Manraj Singh Grover','manraj.singh@practo.com','2016-10-27 10:23:14');

INSERT INTO role (id, name) VALUES (1,'admin');
INSERT INTO role (id, name) VALUES (2,'user');

INSERT INTO user_role (id, user_id, role_id) VALUES (1,1,1);

INSERT INTO product (id, name, company, version, create_timestamp, modified_timestamp, removed_timestamp) VALUES (1,'MacBook','Apple','2017','2016-10-19 22:39:49','2016-10-27 12:18:33',NULL);
INSERT INTO product (id, name, company, version, create_timestamp, modified_timestamp, removed_timestamp) VALUES  (2,'Three','OnePlus','2016','2016-10-19 22:51:07','2016-10-25 17:43:40',NULL);
INSERT INTO product (id, name, company, version, create_timestamp, modified_timestamp, removed_timestamp) VALUES  (3,'iPhone','Apple','7','2016-10-27 10:59:03','2016-10-27 10:59:03',NULL);
INSERT INTO product (id, name, company, version, create_timestamp, modified_timestamp, removed_timestamp) VALUES  (4,'Two','OnePlus','2015','2016-10-27 11:10:27','2016-10-27 11:10:27',NULL);

INSERT INTO item (id, product_id, tag, create_timestamp, available, modified_timestamp, removed_timestamp) VALUES (7,3,'XYZ','2016-10-27 11:17:19','Yes','2016-10-27 12:39:05',NULL);
INSERT INTO item (id, product_id, tag, create_timestamp, available, modified_timestamp, removed_timestamp) VALUES (8,1,'PQR','2016-10-27 11:18:31','No','2016-10-27 11:44:40',NULL);

INSERT INTO request (id, user_id, product_id, reply, modified_timestamp, created_timestamp) VALUES (1,1,2,'YES','2016-10-27 12:18:33','2016-10-19 22:39:49');
INSERT INTO request (id, user_id, product_id, reply, modified_timestamp, created_timestamp) VALUES (2,1,3,'YES','2016-10-27 12:19:33','2016-10-19 22:40:49');

INSERT INTO history (id, user_id, prod_id, item_id, user_name, issue_timestamp, return_timestamp) VALUES (10,1,1,8,'Manraj Singh Grover','2016-10-27 11:44:40',NULL);
INSERT INTO history (id, user_id, prod_id, item_id, user_name, issue_timestamp, return_timestamp) VALUES (11,1,3,7,'Manraj Singh Grover','2016-10-27 11:58:03','2016-10-27 11:59:24');
INSERT INTO history (id, user_id, prod_id, item_id, user_name, issue_timestamp, return_timestamp) VALUES (12,1,3,7,'Manraj Singh Grover','2016-10-27 12:38:51','2016-10-27 12:39:06');