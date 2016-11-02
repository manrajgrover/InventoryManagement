# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.16)
# Database: inventory
# Generation Time: 2016-11-02 09:33:23 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table history
# ------------------------------------------------------------

DROP TABLE IF EXISTS `history`;

CREATE TABLE `history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `prod_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `issue_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `return_timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `fk_history_user_id` (`user_id`),
  KEY `fk_history_prod_id` (`prod_id`),
  KEY `fk_history_item_id` (`item_id`),
  CONSTRAINT `FK8sfcqoyogr7dpji8fbcmbn4wo` FOREIGN KEY (`prod_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKbtex7kw1vv34hjdy08rx6d1q5` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `FKn4gjyu69m6xa5f3bot571imbe` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_history_item_id` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `fk_history_prod_id` FOREIGN KEY (`prod_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_history_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;

INSERT INTO `history` (`id`, `user_id`, `prod_id`, `item_id`, `issue_timestamp`, `return_timestamp`)
VALUES
	(1,1,1,1,'2016-11-02 07:54:21',NULL);

/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `tag` varchar(30) NOT NULL,
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `available` text NOT NULL,
  `modified_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `removed_timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag` (`tag`),
  UNIQUE KEY `UKf8gk1c3u3gaam59xdm6di9ri6` (`tag`),
  KEY `id` (`id`),
  KEY `fk_item_product_id` (`product_id`),
  CONSTRAINT `FKd1g72rrhgq1sf7m4uwfvuhlhe` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;

INSERT INTO `item` (`id`, `product_id`, `tag`, `create_timestamp`, `available`, `modified_timestamp`, `removed_timestamp`)
VALUES
	(1,1,'PQR','2016-11-01 16:27:55','No','2016-11-02 07:54:21',NULL),
	(2,2,'XYZ','2016-11-01 16:28:08','Yes','2016-11-01 16:28:08',NULL),
	(3,4,'LMN','2016-11-01 16:28:22','Yes','2016-11-01 17:35:34','2016-11-01 17:35:34'),
	(4,4,'STU','2016-11-01 16:28:35','Yes','2016-11-01 17:35:34','2016-11-01 17:35:34'),
	(5,4,'DER','2016-11-01 16:28:52','Yes','2016-11-01 17:35:34','2016-11-01 17:35:34');

/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `company` text NOT NULL,
  `version` text NOT NULL,
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `removed_timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;

INSERT INTO `product` (`id`, `name`, `company`, `version`, `create_timestamp`, `modified_timestamp`, `removed_timestamp`)
VALUES
	(1,'MacBook','Apple','2014','2016-11-01 16:26:09','2016-11-01 16:26:09',NULL),
	(2,'iPhone','Apple','6','2016-11-01 16:26:30','2016-11-01 16:26:30',NULL),
	(3,'iPhone','Apple','5','2016-11-01 16:26:46','2016-11-01 16:26:46',NULL),
	(4,'One','OnePlus','2013','2016-11-01 16:27:04','2016-11-01 17:35:34','2016-11-01 17:35:34'),
	(5,'Two','OnePlus','2014','2016-11-01 16:27:29','2016-11-01 16:27:29',NULL);

/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table request
# ------------------------------------------------------------

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `reply` text NOT NULL,
  `modified_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `fk_user_id` (`user_id`),
  KEY `fk_product_id` (`product_id`),
  CONSTRAINT `FKbywsb4tohr9bviei9hqvp7r4i` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKqws2fdeknk90txm7qnm9bxd07` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;

INSERT INTO `request` (`id`, `user_id`, `product_id`, `reply`, `modified_timestamp`, `created_timestamp`)
VALUES
	(1,1,1,'Replyingtohelo','2016-11-01 20:25:35','2016-11-01 19:27:44');

/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `name`)
VALUES
	(1,'admin'),
	(2,'user');

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `email` text NOT NULL,
  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `name`, `email`, `created_timestamp`)
VALUES
	(1,'Manraj Singh Grover','manraj.singh@practo.com','2016-11-01 16:24:07');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `fk_user_roles_id` (`user_id`),
  KEY `fk_user_roles_roles_id` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_user_roles_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_roles_roles_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;

INSERT INTO `user_role` (`id`, `user_id`, `role_id`)
VALUES
	(1,1,1);

/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
