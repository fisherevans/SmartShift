DROP USER 'smarts'@'localhost';
FLUSH PRIVILEGES;
CREATE USER 'smarts'@'%' IDENTIFIED BY 'smarts';

-- Database
DROP DATABASE IF EXISTS Accounts;
CREATE DATABASE Accounts;
GRANT ALL PRIVILEGES ON Accounts.* TO 'smarts'@'%';
COMMIT;

DROP TABLE IF EXISTS `Accounts`.`User`;
CREATE TABLE `Accounts`.`User` (
	`id` INT NOT NULL,
	`username` VARCHAR(50) NOT NULL,
	`passHash` VARCHAR(256) NOT NULL,
	`email` VARCHAR(256) NOT NULL,
	`createTS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`imgID` INT NULL,
	`inactive` TINYINT(1) NOT NULL DEFAULT 0,
	`flags` INT(10) NOT NULL DEFAULT 0,
	PRIMARY KEY (`id`),
	-- UNIQUE (`email`),
	-- UNIQUE (`username`)
);

DROP TABLE IF EXISTS `Accounts`.`Registration`;
CREATE TABLE `Accounts`.`Registration` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`businessID` INT NOT NULL,
	`employeeID` INT NOT NULL,
	`verificationCode` VARCHAR(256) NOT NULL,
	`email` VARCHAR(256) NOT NULL,
	`createTS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE (`businessID`, `employeeID`),
	-- UNIQUE (`email`)
);

DROP TABLE IF EXISTS `Accounts`.`ContactMethod`;
CREATE TABLE `Accounts`.`ContactMethod` (
	`id` INT NOT NULL,
	`name` VARCHAR(60),
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Accounts`.`Business`;
CREATE TABLE `Accounts`.`Business` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(256) NOT NULL,
	`groupID` INT NULL,
	`buildID` INT NOT NULL,
	`servID` INT NOT NULL,
	`imgID` INT NULL,
	`addressID` INT NULL,
	`inactive` TINYINT(0) NOT NULL DEFAULT 0,
	`flags` INT(10) NOT NULL DEFAULT 0,
	PRIMARY KEY (`id`),
	UNIQUE (`groupID`)
);

DROP TABLE IF EXISTS `Accounts`.`Address`;
CREATE TABLE `Accounts`.`Address` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`street1` VARCHAR(256) NULL,
	`street2` VARCHAR(256) NULL,
	`city` VARCHAR(50) NULL,
	`subDivision` VARCHAR(50) NULL,
	`country` VARCHAR(50) NULL,
	`postalCode` VARCHAR(10) NULL,
	`phoneNumber` VARCHAR(11) NULL,
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Accounts`.`Image`;
CREATE TABLE `Accounts`.`Image` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`uri` VARCHAR(256) NOT NULL,
	`alt` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Accounts`.`Server`;
CREATE TABLE `Accounts`.`Server` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`hostname` VARCHAR(256) NOT NULL,
	`ipAddr` VARCHAR(46) NOT NULL,
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Accounts`.`ApplicationTip`;
CREATE TABLE `Accounts`.`ApplicationTip` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`minBuildID` INT NOT NULL,
	`maxBuildID` INT NOT NULL,
	`tip` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Accounts`.`Build`;
CREATE TABLE `Accounts`.`Build` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`version` VARCHAR(20) NOT NULL,
	`createTS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`sqlDir` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Accounts`.`SystemProperty`;
CREATE TABLE `Accounts`.`SystemProperty` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`servID` INT NOT NULL,
	`name` VARCHAR(256) NOT NULL,
	`propVal` VARCHAR(256), 
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Accounts`.`Preference`;
CREATE TABLE `Accounts`.`Preference` (
	`id` INT NOT NULL,
	`name` VARCHAR(256),
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Accounts`.`UserContactMethod`;
CREATE TABLE `Accounts`.`UserContactMethod` (
	`userID` INT NOT NULL,
	`cMethodID` INT NOT NULL,
	`cMethodVal` VARCHAR(60),
	PRIMARY KEY (`userID`, `cMethodID`)
);

DROP TABLE IF EXISTS `Accounts`.`UserBusinessEmployee`;
CREATE TABLE `Accounts`.`UserBusinessEmployee` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`userID` INT NOT NULL,
	`busID` INT NOT NULL,
	`empID` INT NOT NULL,
	`joinTS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE(`userID`, `busID`, `empID`)
);

DROP TABLE IF EXISTS `Accounts`.`Session`;
CREATE TABLE `Accounts`.`Session` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`userBusEmpID` INT NOT NULL,
	`sessionKey` VARCHAR(256) NOT NULL,
	`lastActivityTS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Accounts`.`BusinessPreference`;
CREATE TABLE `Accounts`.`BusinessPreference` (
	`busID` INT NOT NULL,
	`prefID` INT NOT NULL,
	`prefVal` VARCHAR(256),
	PRIMARY KEY (`busID`, `prefID`)
);

DROP TABLE IF EXISTS `Accounts`.`UserBusinessEmployeePreference`;
CREATE TABLE `Accounts`.`UserBusinessEmployeePreference` (
	`userBusEmpID` INT NOT NULL,
	`prefID` INT NOT NULL,
	`prefVal` VARCHAR(256),
	PRIMARY KEY (`userBusEmpID`, `prefID`)
);

ALTER TABLE `Accounts`.`User` 
ADD CONSTRAINT `user_image`
	FOREIGN KEY (`imgID`)
	REFERENCES `Accounts`.`Image` (`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;
  
ALTER TABLE `Accounts`.`Business`
ADD CONSTRAINT `business_image`
	FOREIGN KEY (`imgID`)
	REFERENCES `Accounts`.`Image`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
ADD CONSTRAINT `business_server`
	FOREIGN KEY (`servID`)
	REFERENCES `Accounts`.`Server`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
ADD CONSTRAINT `business_build`
	FOREIGN KEY (`buildID`)
	REFERENCES `Accounts`.`Build`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
ADD CONSTRAINT `business_address`
	FOREIGN KEY (`addressID`)
	REFERENCES `Accounts`.`Address`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;
	
ALTER TABLE `Accounts`.`ApplicationTip`
ADD CONSTRAINT `applicationtip_maxbuild`
	FOREIGN KEY (`maxBuildID`)
	REFERENCES `Accounts`.`Build`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
ADD CONSTRAINT `applicationtip_minbuild`
	FOREIGN KEY (`minBuildID`)
	REFERENCES `Accounts`.`Build`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;
	
ALTER TABLE `Accounts`.`SystemProperty`
ADD CONSTRAINT `systemproperty_server`
	FOREIGN KEY (`servID`)
	REFERENCES `Accounts`.`Server`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;

ALTER TABLE `Accounts`.`UserContactMethod`
ADD CONSTRAINT `usercontactmethod_user`
	FOREIGN KEY (`userID`)
	REFERENCES `Accounts`.`User`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
ADD CONSTRAINT `usercontactmethod_cmethod`
	FOREIGN KEY (`cMethodID`)
	REFERENCES `Accounts`.`ContactMethod`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;
	
ALTER TABLE `Accounts`.`UserBusinessEmployee`
ADD CONSTRAINT `userbusinessemp_user`
	FOREIGN KEY (`userID`)
	REFERENCES `Accounts`.`User`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
ADD CONSTRAINT `userbusinessemp_business`
	FOREIGN KEY (`busID`)
	REFERENCES `Accounts`.`Business`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;


ALTER TABLE `Accounts`.`Session`
ADD CONSTRAINT `session_userbusiness`
	FOREIGN KEY (`userBusEmpID`)
	REFERENCES `Accounts`.`UserBusinessEmployee`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;

ALTER TABLE `Accounts`.`BusinessPreference`
ADD INDEX `i_businesspreference_bus` (`busID`),
ADD INDEX `i_businesspreference_pref` (`prefID`),
ADD CONSTRAINT `businesspreference_business`
	FOREIGN KEY (`busID`)
	REFERENCES `Accounts`.`Business`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
ADD CONSTRAINT `businesspreference_preference`
	FOREIGN KEY (`prefID`)
	REFERENCES `Accounts`.`Preference`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;	

ALTER TABLE `Accounts`.`UserBusinessEmployeePreference`
ADD INDEX `i_ubusinessemppreference_bus` (`userBusEmpID`),
ADD INDEX `i_ubusinessemppreference_pref` (`prefID`),
ADD CONSTRAINT `userbusinessemppreference_userbus`
	FOREIGN KEY (`userBusEmpID`)
	REFERENCES `Accounts`.`UserBusinessEmployee`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
ADD CONSTRAINT `userbusinessemppreference_preference`
	FOREIGN KEY (`prefID`)
	REFERENCES `Accounts`.`Preference`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;	


ALTER TABLE `Accounts`.`Registration`
ADD CONSTRAINT `registration_business`
	FOREIGN KEY (`businessID`)
	REFERENCES `Accounts`.`Business`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;
