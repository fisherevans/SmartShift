-- Database
DROP DATABASE IF EXISTS Business_1;
CREATE DATABASE Business_1;
GRANT ALL PRIVILEGES ON Business_1.* TO 'smarts'@'%';
COMMIT;

USE Business_1;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `Group`;
CREATE TABLE `Group` (
  `id` INT NOT NULL,
  `parentID` INT NULL,
  `name` VARCHAR(45),
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role` (
  `id` INT NOT NULL,
  `name` VARCHAR(45),
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Employee`;
CREATE TABLE `Employee` (
  `id` INT NOT NULL,
  `defaultGrpID` INT NOT NULL,
  `fName` VARCHAR(60) NULL,
  `lName` VARCHAR(60) NULL,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Capability`;
CREATE TABLE `Capability` (
  `id` INT NOT NULL,
  `name` VARCHAR(45),
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `GroupRole`;
CREATE TABLE `GroupRole` (
  `id` INT NOT NULL,
  `grpID` INT NOT NULL,
  `roleID` INT NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `GroupEmployee`;
CREATE TABLE `GroupEmployee` (
  `grpID` INT NOT NULL,
  `empID` INT NOT NULL,
  PRIMARY KEY (`grpID`, `empID`)
);

DROP TABLE IF EXISTS `GroupRoleEmployee`;
CREATE TABLE `GroupRoleEmployee` (
  `grpRoleID` INT NOT NULL,
  `empID` INT NOT NULL,
  PRIMARY KEY (`grpRoleID`, `empID`)
);

DROP TABLE IF EXISTS `GroupRoleCapability`;
CREATE TABLE `GroupRoleCapability` (
  `grpRoleID` INT NOT NULL,
  `capID` INT NOT NULL,
  PRIMARY KEY (`grpRoleID`, `capID`)
);

DROP TABLE IF EXISTS `AvailInstance`;
CREATE TABLE `AvailInstance` (
  `id` INT NOT NULL,
  `templateID` INT NOT NULL,
  `startDate` DATE NOT NULL,
  `endDate` DATE NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `AvailTemplate`;
CREATE TABLE `AvailTemplate` (
  `id` INT NOT NULL,
  `name` VARCHAR(45),
  `empID` INT NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Availability`;
CREATE TABLE `Availability` (
  `id` INT NOT NULL,
  `templateID` INT NOT NULL,
  `start` INT NOT NULL,
  `duration` INT NOT NULL,
  `repeatEvery` INT NOT NULL DEFAULT 0,
  `repeatCount` INT NOT NULL DEFAULT 0,
  `repeatOffset` INT NOT NULL DEFAULT 0,
  `unavailable` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `AvailRepeatWeekly`;
CREATE TABLE `AvailRepeatWeekly` (
    `id` INT NOT NULL,
  `availID` INT NOT NULL,
  `dayOfWeek` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE(`availID`, `dayOfWeek`)
);

DROP TABLE IF EXISTS `AvailRepeatMonthlyByDate`;
CREATE TABLE `AvailRepeatMonthlyByDate` (
    `id` INT NOT NULL,
  `availID` INT NOT NULL,
  -- make day of month negative if should start from end
  `dayOfMonth` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE(`availID`, `dayOfMonth`)
);

DROP TABLE IF EXISTS `AvailRepeatMonthlyByDay`;
CREATE TABLE `AvailRepeatMonthlyByDay` (
    `id` INT NOT NULL,
  `availID` INT NOT NULL,
  `offset` INT NOT NULL DEFAULT 0,
  -- make day of month negative if should start from end
  `dayOfWeek` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE(`availID`, `offset`, `dayOfWeek`)
);

DROP TABLE IF EXISTS `AvailRepeatYearly`;
CREATE TABLE `AvailRepeatYearly` (
    `id` INT NOT NULL,
  `availID` INT NOT NULL,
  `month` INT NOT NULL,
  `dayOfMonth` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE(`availID`, `month`, `dayOfMonth`)
);

DROP TABLE IF EXISTS `Shift`;
CREATE TABLE `Shift` (
	`id` INT NOT NULL,
	`start` TIMESTAMP NOT NULL,
	`duration` INT NOT NULL,
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `SchedTemplateVersion`;
CREATE TABLE `SchedTemplateVersion` (
  `id` INT NOT NULL,
  `schedTempID` INT NOT NULL,
  `name` VARCHAR(60) NULL,
  `createTS` TIMESTAMP NOT NULL,
  `version` INT NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Schedule`;
CREATE TABLE `Schedule` (
  `id` INT NOT NULL,
  `schedTempVersionID` INT NOT NULL,
  `startDate` DATE NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `RoleSchedule`;
CREATE TABLE `RoleSchedule` (
  `id` INT NOT NULL,
  `schedID` INT NOT NULL,
  `grpRoleID` INT NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `RoleSchedShift`;
CREATE TABLE `RoleSchedShift` (
  `roleSchedID` INT NOT NULL,
  `shiftID` INT NOT NULL,
  PRIMARY KEY(`roleSchedID`, `shiftID`)
);

DROP TABLE IF EXISTS `EmpSchedule`;
CREATE TABLE `EmpSchedule` (
  `id` INT NOT NULL,
  `schedID` INT NOT NULL,
  `empID` INT NOT NULL,
  `locked` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `EmpScheduleShift`;
CREATE TABLE `EmpScheduleShift` (
	`empSchedID` INT NOT NULL,
	`shiftID` INT NOT NULL,
	`grpID` INT NOT NULL,
	`roleID` INT NULL,
	PRIMARY KEY (`empSchedID`, `shiftID`)
);

DROP TABLE IF EXISTS `Content`;
CREATE TABLE `Content` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `TextContent`;
CREATE TABLE `TextContent` (
  `id` INT NOT NULL,
  `text` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `CompoundContent`;
CREATE TABLE `CompoundContent` (
  `id` INT NOT NULL,
  `seqNum` INT NOT NULL,
  `childID` INT NOT NULL,
  PRIMARY KEY (`id`, `seqNum`)
);

DROP TABLE IF EXISTS `StyledContent`;
CREATE TABLE `StyledContent` (
  `id` INT NOT NULL,
  `styleID` INT NOT NULL,
  PRIMARY KEY (`id`, `styleID`)
);

DROP TABLE IF EXISTS `Style`;
CREATE TABLE `Style` (
  `id` INT NOT NULL,
  `attribute` VARCHAR(30),
  `rule` VARCHAR(128),
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `TaggedContent`;
CREATE TABLE `TaggedContent` (
  `id` INT NOT NULL,
  `tagID` INT NOT NULL,
  PRIMARY KEY (`id`, `tagID`)
);

DROP TABLE IF EXISTS `Tag`;
CREATE TABLE `Tag` (
  `id` INT NOT NULL,
  `name` VARCHAR(128),
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Message`;
CREATE TABLE `Message` (
  `id` INT NOT NULL,
  `parentID` INT NOT NULL,
  `contentID` INT NOT NULL,
  `authorID` INT NOT NULL,
  `sentTS` DATETIME NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `MessageDelivery`;
CREATE TABLE `MessageDelivery` (
  `id` INT NOT NULL,
  `messageID` INT NOT NULL,
  `recipientID` INT NOT NULL,
  `seenTS` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE(`messageID`, `recipientID`)
);

ALTER TABLE `Group`
ADD CONSTRAINT `group_parent`
  FOREIGN KEY (`parentID`)
  REFERENCES `Group`(`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `Employee`
ADD CONSTRAINT `employee_grp`
  FOREIGN KEY (`defaultGrpID`)
  REFERENCES `Group`(`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `GroupRole`
ADD CONSTRAINT `grouprole_grp`
  FOREIGN KEY (`grpID`)
  REFERENCES `Group`(`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `grouprole_role`
  FOREIGN KEY (`roleID`)
  REFERENCES `Role`(`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `GroupRoleCapability`
ADD CONSTRAINT `grouprolecapability_grprole`
  FOREIGN KEY (`grpRoleID`)
  REFERENCES `GroupRole`(`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `grouprolecapability_capability`
  FOREIGN KEY (`capID`)
  REFERENCES `Capability`(`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `GroupEmployee`
ADD CONSTRAINT `groupemp_grp`
  FOREIGN KEY (`grpID`)
  REFERENCES `Group`(`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `groupemp_emp`
  FOREIGN KEY (`empID`)
  REFERENCES `Employee`(`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `GroupRoleEmployee`
ADD CONSTRAINT `grproleemp_grprole`
  FOREIGN KEY (`grpRoleID`)
  REFERENCES `GroupRole`(`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `grproleemp_emp`
  FOREIGN KEY (`empID`)
  REFERENCES `Employee`(`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `AvailInstance`
ADD CONSTRAINT `availinstance_template`
  FOREIGN KEY (`templateID`)
  REFERENCES `AvailTemplate` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `AvailTemplate`
ADD CONSTRAINT `availtemplate_emp`
  FOREIGN KEY (`empID`)
  REFERENCES `Employee`(`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `Availability`
ADD CONSTRAINT `availability_template`
  FOREIGN KEY (`templateID`)
  REFERENCES `AvailTemplate` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `AvailRepeatWeekly`
ADD CONSTRAINT `repeatweekly_avail`
  FOREIGN KEY (`availID`)
  REFERENCES `Availability` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `AvailRepeatMonthlyByDate`
ADD CONSTRAINT `repeatmonthly_avail`
  FOREIGN KEY (`availID`)
  REFERENCES `Availability` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `AvailRepeatMonthlyByDay`
ADD CONSTRAINT `repeatmonth_avail`
  FOREIGN KEY (`availID`)
  REFERENCES `Availability` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `AvailRepeatYearly`
ADD CONSTRAINT `repeatyearly_avail`
  FOREIGN KEY (`availID`)
  REFERENCES `Availability` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `Schedule`
ADD CONSTRAINT `sched_schedtempvers`
  FOREIGN KEY (`schedTempVersionID`)
  REFERENCES `SchedTemplateVersion` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `EmpSchedule`
ADD CONSTRAINT `empsched_sched`
  FOREIGN KEY (`schedID`)
  REFERENCES `Schedule` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `empsched_emp`
  FOREIGN KEY (`empID`)
  REFERENCES `Employee` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `EmpScheduleShift`
ADD CONSTRAINT `empschedshift_empsched`
	FOREIGN KEY (`empSchedID`)
	REFERENCES `EmpSchedule` (`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
ADD CONSTRAINT `empschedshift_shift`
	FOREIGN KEY (`shiftID`)
	REFERENCES `Shift` (`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
ADD CONSTRAINT `empschedshift_grp`
	FOREIGN KEY (`grpID`)
	REFERENCES `Group` (`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
ADD CONSTRAINT `empschedshift_role`
	FOREIGN KEY (`roleID`)
	REFERENCES `Role` (`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;

ALTER TABLE `RoleSchedule`
ADD CONSTRAINT `rolesched_sched`
  FOREIGN KEY (`schedID`)
  REFERENCES `Schedule` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `rolesched_role`
  FOREIGN KEY (`grpRoleID`)
  REFERENCES `GroupRole` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `RoleSchedShift`
ADD CONSTRAINT `roleschedshift_rolesched`
  FOREIGN KEY (`roleSchedID`)
  REFERENCES `RoleSchedule` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `roleschedshift_shift`
  FOREIGN KEY (`shiftID`)
  REFERENCES `Shift` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `TextContent`
ADD CONSTRAINT `textcontent_content`
  FOREIGN KEY (`id`)
  REFERENCES `Content` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `CompoundContent`
ADD CONSTRAINT `compoundcontent_content`
  FOREIGN KEY (`id`)
  REFERENCES `Content` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `compoundcontent_child`
  FOREIGN KEY (`childID`)
  REFERENCES `Content` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `StyledContent`
ADD CONSTRAINT `styledcontent_content`
  FOREIGN KEY (`id`)
  REFERENCES `Content` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `styledcontent_style`
  FOREIGN KEY (`styleID`)
  REFERENCES `Style` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `TaggedContent`
ADD CONSTRAINT `taggedcontent_content`
  FOREIGN KEY (`id`)
  REFERENCES `Content` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `taggedcontent_tag`
  FOREIGN KEY (`tagID`)
  REFERENCES `Tag` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `Message`
ADD CONSTRAINT `message_content`
  FOREIGN KEY (`contentID`)
  REFERENCES `Content` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `message_author`
  FOREIGN KEY (`authorID`)
  REFERENCES `Employee` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `message_parent`
  FOREIGN KEY (`parentID`)
  REFERENCES `Message` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

ALTER TABLE `MessageDelivery`
ADD CONSTRAINT `mdelivery_recipient`
  FOREIGN KEY (`recipientID`)
  REFERENCES `Employee` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `mdelivery_message`
  FOREIGN KEY (`messageID`)
  REFERENCES `Message` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;

SET FOREIGN_KEY_CHECKS=1;
