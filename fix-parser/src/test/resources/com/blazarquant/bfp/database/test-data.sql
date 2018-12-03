INSERT INTO bfp_roles VALUES (1,'Admin'),(2,'User');
INSERT INTO bfp_permissions VALUES (1,'BASIC'),(2,'PRO'),(3,'ENTERPRISE');
INSERT INTO bfp_users VALUES
  (9,'Zano','$2a$12$b9AGT0nw80sLSj/pk3gYkuLczCmuvZu1ZE9IBOuMJy14IjCIItziy','wojciech@zankowski.pl',1,'1460848358000','1460848358001','OTtaYW5vO3dvamNpZWNoQHphbmtvd3NraS5wbA=='),
  (8,'Test','$2a$12$b9AGT0nw80sLSj/pk3gYkuLczCmuvZu1ZE9IBOuMJy14IjCIItziy','test@zankowski.pl',1,'1460841158000','1460841158001','OTtaYW5vO3dvamNpZWNoQHphbmtvd3NraS5wbA==');
INSERT INTO bfp_userroles VALUES (2,9,2),(3,8,1);
INSERT INTO bfp_userpermissions VALUES (2,1,9),(3,2,9),(4,2,8);
INSERT INTO bfp_userparameters VALUES (9,'DEFAULT_PROVIDER','QF#FIX42');
INSERT INTO bfp_tracker VALUES (1, 1460841158000, 14);
INSERT INTO bfp_sharedmessages VALUES
  (1, '9d9c3750-73c7-42fd-bb4c-b4b4714e0135', 'OD1GSVguNC4yIzk9MTIzIzM1PUQjNDk9QmxhemFyUXVhbnQjNTY9QnJva2VyIzUyPTIwMTYwMzI1LTIyOjUyOjMwLjUzMCMxMT0xMDAxIzM4PTIwMCM0MD0xIzU0PTEjNTU9SUJNIzU5PTAjMTA9MDEzIw=='),
  (2, '44d7cbfe-64a5-4f4c-9c11-b6bb67d7495b', 'OD1GSVguNC4yIzk9MTIzIzM1PUQjNDk9QmxhemFyUXVhbnQjNTY9QnJva2VyIzUyPTIwMTYwMzI1LTIzOjAzOjM0Ljc4NCMxMT0xMDA0IzM4PTEwMCM0MD0yIzQ0PTEwMCM1ND0xIzU1PUtPIzU5PTAjMTA9MDY0Iw==');
INSERT INTO bfp_messages VALUES
  (1, 9, 'message1'),
  (2, 9, 'message2'),
  (3, 9, 'message3'),
  (4, 8, 'message1');