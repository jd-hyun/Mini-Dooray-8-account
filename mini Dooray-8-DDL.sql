CREATE TABLE `account` (
	`account_id`	bigint	NOT NULL,
	`login_id`	varchar(50)	NOT NULL,
	`password`	varchar(100)	NOT NULL,
	`email`	varchar(100)	NOT NULL,
	`status`	varchar(10)	NOT NULL	DEFAULT 'ACTIVE'	COMMENT 'ACTIVE,INACTIVE'
);

CREATE TABLE `project` (
	`project_id`	bigint	NOT NULL,
	`project_title`	varchar(100)	NOT NULL,
	`project_contents`	text	NOT NULL,
	`project_state`	varchar(10)	NOT NULL	DEFAULT 'ACTIVE'
);

CREATE TABLE `task` (
	`task_id`	bigint	NOT NULL,
	`project_key`	bigint	NOT NULL	COMMENT 'ON DELETE CASCADE',
	`task_title`	varchar(100)	NOT NULL,
	`tsak_contents`	text	NOT NULL
);

CREATE TABLE `comment` (
	`comment_key`	bigint	NOT NULL,
	`user_key`	bigint	NULL	COMMENT 'ON DELETE NULL',
	`task_key`	bigint	NOT NULL	COMMENT 'ON DELETE CASCADE',
	`comment_date`	datetime	NOT NULL,
	`comment_contents`	text	NOT NULL
);

CREATE TABLE `milestone` (
	`milestone_key`	bigint	NOT NULL,
	`project_key`	bigint	NOT NULL	COMMENT 'ON DELETE CASCADE',
	`milestone_title`	varchar(100)	NOT NULL,
	`start_date`	datetime	NULL,
	`end_date`	datetime	NULL
);

CREATE TABLE `tag` (
	`tag_key`	bigint	NOT NULL,
	`project_key`	bigint	NOT NULL	COMMENT 'ON DELETE CASCADE',
	`tag_name`	varchar(100)	NOT NULL
);

CREATE TABLE `tasks_tags` (
	`task_tag_id`	bigint	NOT NULL,
	`tag_id`	bigint	NOT NULL	COMMENT 'ON DELETE CASCADE',
	`task_id`	bigint	NOT NULL	COMMENT 'ON DELETE CASCADE'
);

CREATE TABLE `project_member` (
	`project_account_int`	bigint	NOT NULL,
	`project_id`	bigint	NOT NULL	COMMENT 'ON DELETE CASCADE',
	`account_id`	bigint	NOT NULL	COMMENT 'ON DELETE CASCADE'
);

CREATE TABLE `task_milestone` (
	`id`	bigint	NOT NULL,
	`milestone_key`	bigint	NOT NULL	COMMENT 'ON DELETE CASCADE',
	`task_key`	bigint	NOT NULL	COMMENT 'ON DELETE CASCADE'
);

ALTER TABLE `account` ADD CONSTRAINT `PK_ACCOUNT` PRIMARY KEY (
	`account_id`
);

ALTER TABLE `project` ADD CONSTRAINT `PK_PROJECT` PRIMARY KEY (
	`project_id`
);

ALTER TABLE `task` ADD CONSTRAINT `PK_TASK` PRIMARY KEY (
	`task_id`
);

ALTER TABLE `comment` ADD CONSTRAINT `PK_COMMENT` PRIMARY KEY (
	`comment_key`
);

ALTER TABLE `milestone` ADD CONSTRAINT `PK_MILESTONE` PRIMARY KEY (
	`milestone_key`
);

ALTER TABLE `tag` ADD CONSTRAINT `PK_TAG` PRIMARY KEY (
	`tag_key`
);

ALTER TABLE `tasks_tags` ADD CONSTRAINT `PK_TASKS_TAGS` PRIMARY KEY (
	`task_tag_id`
);

ALTER TABLE `project_member` ADD CONSTRAINT `PK_PROJECT_MEMBER` PRIMARY KEY (
	`project_account_int`
);

ALTER TABLE `task_milestone` ADD CONSTRAINT `PK_TASK_MILESTONE` PRIMARY KEY (
	`id`
);

