INSERT INTO user (id, name, email, contact, created_timestamp, modified_timestamp, removed_timestamp) VALUES (6,'Manraj Singh Grover','manraj.singh@practo.com','9898989898','2016-10-27 10:23:14','2016-10-27 10:23:14',NULL);

INSERT INTO role (id, name) VALUES (1,'admin');
INSERT INTO role (id, name) VALUES (2,'user');

INSERT INTO user_role (id, user_id, role_id) VALUES (3,6,1);

INSERT INTO product (id, name, company, version, create_timestamp, modified_timestamp, removed_timestamp) VALUES (1,'MacBook','Apple','2017','2016-10-19 22:39:49','2016-10-27 12:18:33',NULL);
INSERT INTO product (id, name, company, version, create_timestamp, modified_timestamp, removed_timestamp) VALUES  (2,'Three','OnePlus','2016','2016-10-19 22:51:07','2016-10-25 17:43:40',NULL);
INSERT INTO product (id, name, company, version, create_timestamp, modified_timestamp, removed_timestamp) VALUES  (3,'iPhone','Apple','7','2016-10-27 10:59:03','2016-10-27 10:59:03',NULL);
INSERT INTO product (id, name, company, version, create_timestamp, modified_timestamp, removed_timestamp) VALUES  (4,'Two','OnePlus','2015','2016-10-27 11:10:27','2016-10-27 11:10:27',NULL);

INSERT INTO item (id, product_id, tag, create_timestamp, available, modified_timestamp, removed_timestamp) VALUES (7,3,'XYZ','2016-10-27 11:17:19','Yes','2016-10-27 12:39:05',NULL);
INSERT INTO item (id, product_id, tag, create_timestamp, available, modified_timestamp, removed_timestamp) VALUES (8,1,'PQR','2016-10-27 11:18:31','No','2016-10-27 11:44:40',NULL);

INSERT INTO history (id, user_id, prod_id, item_id, user_name, issue_timestamp, return_timestamp) VALUES (10,6,1,8,'Manraj Singh Grover','2016-10-27 11:44:40',NULL);
INSERT INTO history (id, user_id, prod_id, item_id, user_name, issue_timestamp, return_timestamp) VALUES (11,6,3,7,'Manraj Singh Grover','2016-10-27 11:58:03','2016-10-27 11:59:24');
INSERT INTO history (id, user_id, prod_id, item_id, user_name, issue_timestamp, return_timestamp) VALUES (12,6,3,7,'Manraj Singh Grover','2016-10-27 12:38:51','2016-10-27 12:39:06');