CREATE TABLE `Account` (
	`account_id`	bigint	NOT NULL,
	`login_id`	varchar(255)	NOT NULL,
	`password`	varchar(255)	NOT NULL,
	`email`	varchar(255)	NOT NULL,
	`status`	enum('ACTIVE,INACTIVE')	NULL	DEFAULT ACTIVE
);

ALTER TABLE `Account` ADD CONSTRAINT `PK_ACCOUNT` PRIMARY KEY (
	`account_id`
);

