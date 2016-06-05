INSERT INTO bfp_roles VALUES (1,'Admin'),(2,'User');
INSERT INTO bfp_permissions VALUES (1,'BASIC'),(2,'PRO'),(3,'ENTERPRISE');
INSERT INTO bfp_users VALUES (9,'Zano','$2a$12$b9AGT0nw80sLSj/pk3gYkuLczCmuvZu1ZE9IBOuMJy14IjCIItziy','wojciech@zankowski.pl',1,'2016-04-16 23:12:38','2016-04-16 23:12:38','OTtaYW5vO3dvamNpZWNoQHphbmtvd3NraS5wbA==');
INSERT INTO bfp_userroles VALUES (2,9,2);
INSERT INTO bfp_userpermissions VALUES (2,1,9),(3,2,9);
INSERT INTO bfp_userparameters VALUES (9,'DEFAULT_PROVIDER','QF#FIX42');