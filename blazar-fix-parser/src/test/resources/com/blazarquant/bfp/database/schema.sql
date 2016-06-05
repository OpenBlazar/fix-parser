CREATE TABLE bfp_users (
ID bigint NOT NULL IDENTITY,
user_login varchar(60) NOT NULL,
user_pass varchar(64) NOT NULL,
user_email varchar(100) NOT NULL,
user_status int NOT NULL,
user_registerdate datetime NOT NULL,
user_lastlogin datetime NOT NULL,
user_confirmationkey varchar(256) DEFAULT NULL,
PRIMARY KEY (ID)
);

CREATE TABLE bfp_roles (
ID bigint NOT NULL IDENTITY,
role_name varchar(45) NOT NULL,
PRIMARY KEY (ID)
);

CREATE TABLE bfp_userroles (
ID bigint NOT NULL IDENTITY,
user_id bigint NOT NULL,
role_id bigint NOT NULL,
PRIMARY KEY (ID, user_id, role_id),
CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES bfp_roles (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES bfp_users (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE bfp_messages (
ID bigint NOT NULL IDENTITY,
user_id bigint NOT NULL,
message varchar(16383) NOT NULL,
PRIMARY KEY (ID, user_id),
CONSTRAINT user_id_msg FOREIGN KEY (user_id) REFERENCES bfp_users (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE bfp_sharedmessages (
ID bigint NOT NULL IDENTITY,
share_key varchar(256) NOT NULL,
share_message varchar(8192) NOT NULL,
PRIMARY KEY (ID)
);

CREATE TABLE bfp_tracker (
ID bigint NOT NULL IDENTITY,
tracker_date datetime NOT NULL,
tracker_number int NOT NULL,
PRIMARY KEY (ID)
);

CREATE TABLE bfp_userparameters (
user_id BIGINT NOT NULL,
user_setting VARCHAR(64) NOT NULL,
setting_value VARCHAR(128) NOT NULL,
PRIMARY KEY (user_id, user_setting),
CONSTRAINT fk_user_id_up
FOREIGN KEY (user_id)
REFERENCES bfp_users (ID)
);

CREATE TABLE bfp_permissions (
id int NOT NULL IDENTITY,
permission_name varchar(45) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE bfp_userpermissions (
id INT NOT NULL IDENTITY,
permission_id INT NOT NULL,
user_id INT NOT NULL,
PRIMARY KEY (id)
);