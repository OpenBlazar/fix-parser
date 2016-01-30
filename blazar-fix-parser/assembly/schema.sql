CREATE TABLE `blazarfixparser`.`bfp_users` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '',
  `user_login` VARCHAR(60) NOT NULL COMMENT '',
  `user_pass` VARCHAR(64) NOT NULL COMMENT '',
  `user_email` VARCHAR(100) NOT NULL COMMENT '',
  `user_status` INT(11) NOT NULL COMMENT '',
  `user_registerdate` DATETIME NOT NULL COMMENT '',
  `user_lastlogin` DATETIME NOT NULL COMMENT '',
  PRIMARY KEY (`ID`)  COMMENT '');