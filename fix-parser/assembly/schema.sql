DROP SCHEMA IF EXISTS blazarfixparser;
CREATE SCHEMA blazarfixparser;
USE blazarfixparser;

CREATE TABLE `bfp_users` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_login` varchar(60) NOT NULL,
  `user_pass` varchar(64) NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `user_status` int(11) NOT NULL,
  `user_registerdate` datetime NOT NULL,
  `user_lastlogin` datetime NOT NULL,
  `user_confirmationkey` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB;

CREATE TABLE `bfp_roles` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB;

CREATE TABLE `bfp_userroles` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `user_id_idx` (`user_id`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `bfp_roles` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `bfp_users` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB;
  
CREATE TABLE `bfp_messages` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `message` varchar(16383) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id_msg` FOREIGN KEY (`user_id`) REFERENCES `bfp_users` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB;

CREATE TABLE `bfp_sharedmessages` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `share_key` varchar(256) NOT NULL,
  `share_message` varchar(8192) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB;

CREATE TABLE `bfp_tracker` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `tracker_date` datetime NOT NULL,
  `tracker_number` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB;