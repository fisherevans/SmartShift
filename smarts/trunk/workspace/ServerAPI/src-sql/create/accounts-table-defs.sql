CREATE DATABASE Accounts;
CREATE USER 'smarts'@'localhost' IDENTIFIED BY 'smarts';
GRANT ALL PRIVILEGES ON Accounts.* TO 'smarts'@'localhost';
COMMIT;

CREATE TABLE `Accounts`.`User` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(50) NOT NULL,
	`pass` VARCHAR(256) NOT NULL,
	`email` VARCHAR(256) NOT NULL,
	`fName` VARCHAR(60) NULL,
	`lName` VARCHAR(60) NULL,
	`createTS` DATE NOT NULL,
	`imgID` INT NULL,
	`inactive` TINYINT(1) NOT NULL DEFAULT 0,
	`flags` INT(10) NOT NULL DEFAULT 0,
	PRIMARY KEY (`id`),
	UNIQUE (`email`)
);

CREATE TABLE `Accounts`.`ContactMethod` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(60),
	PRIMARY KEY (`id`)
);

CREATE TABLE `Accounts`.`Business` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(256) NOT NULL,
	`buildID` INT NOT NULL,
	`servID` INT NOT NULL,
	`imgID` INT NULL,
	`inactive` TINYINT(0) NOT NULL DEFAULT 0,
	`flags` INT(10) NOT NULL DEFAULT 0,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Accounts`.`Image` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`url` VARCHAR(256) NOT NULL,
	`alt` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Accounts`.`Server` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`hostname` VARCHAR(256) NOT NULL,
	`ipAddr` VARCHAR(46) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Accounts`.`ApplicationTip` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`minBuildID` INT NOT NULL,
	`maxBuildID` INT NOT NULL,
	`tip` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Accounts`.`Build` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`version` VARCHAR(20) INT NOT NULL,
	`createTS` DATE NOT NULL,
	`sqlDir` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Accounts`.`SystemProperty` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`serverID` INT NOT NULL,
	`name` VARCHAR(256) NOT NULL,
	`propVal` VARCHAR(256), 
	PRIMARY KEY (`id`)
);

CREATE TABLE `Accounts`.`Preference` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(256),
	PRIMARY KEY (`id`)
);

CREATE TABLE `Accounts`.`UserContactMethod` (
	`userID` INT NOT NULL,
	`cMethodID` INT NOT NULL,
	`cMethodVal` VARCHAR(60),
	PRIMARY KEY (`id`)
);

CREATE TABLE `Accounts`.`UserBusiness` (
	`userID` INT NOT NULL,
	`busID` INT NOT NULL,
	`joinTS` DATE NOT NULL,
	PRIMARY KEY (`userID`, `busID`)
);

CREATE TABLE `Accounts`.`BusinessPreference` (
	`busID` INT NOT NULL,
	`prefID` INT NOT NULL,
	`prefVal` VARCHAR(256),
	PRIMARY KEY (`busID`, `prefID`)
);

CREATE TABLE `Accounts`.`UserBusinessPreference` (
	`userBusID` INT NOT NULL,
	`prefID` INT NOT NULL,
	`prefVal` VARCHAR(256),
	PRIMARY KEY (`userBusID`, `prefID`)
);

ALTER TABLE `Accounts`.`User` 
ADD CONSTRAINT `user_image`
	FOREIGN KEY (`imageID`)
	REFERENCES `Accounts`.`Image` (`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;
  
ALTER TABLE `Accounts`.`Business`
ADD CONSTRAINT `business_image`
	FOREIGN KEY (`imageID`)
	REFERENCES `Accounts`.`Image`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
CONSTRAINT `business_server`
	FOREIGN KEY (`srvID`)
	REFERENCES `Accounts`.`Server`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
CONSTRAINT `business_build`
	FOREIGN KEY (`busID`)
	REFERENCES `Accounts`.`Business`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;
	
ALTER TABLE `Accounts`.`ApplicationTip`
ADD CONSTRAINT `applicationtip_maxbuild`
	FOREIGN KEY (`maxBuildID`)
	REFERENCES `Accounts`.`Build`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
CONSTRAINT `applicationtip_minbuild`
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
CONSTRAINT `usercontactmethod_cmethod`
	FOREIGN KEY (`cMethodID`)
	REFERENCES `Accounts`.`ContactMethod`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;
	
ALTER TABLE `Accounts`.`UserBusinessPreference`
ADD CONSTRAINT `userbusinesspreference_userbus`
	FOREIGN KEY (`userBusID`)
	REFERENCES `Accounts`.`UserBusiness`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
CONSTRAINT `userbusinesspreference_preference`
	FOREIGN KEY (`prefID`)
	REFERENCES `Accounts`.`Preference`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;
	
ALTER TABLE `Accounts`.`UserBusiness`
ADD CONSTRAINT `userbusiness_user`
	FOREIGN KEY (`userID`)
	REFERENCES `Accounts`.`User`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
CONSTRAINT `userbusiness_business`
	FOREIGN KEY (`busID`)
	REFERENCES `Accounts`.`Business`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;

ALTER TABLE `Accounts`.`BusinessPreference`
ADD CONSTRAINT `businesspreference_business`
	FOREIGN KEY (`busID`)
	REFERENCES `Accounts`.`Business`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
CONSTRAINT `businesspreference_preference`
	FOREIGN KEY (`prefID`)
	REFERENCES `Accounts`.`Preference`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;	

ALTER TABLE `Accounts`.`UserBusinessPreference`
ADD CONSTRAINT `userbusinesspreference_userbus`
	FOREIGN KEY (`userBusID`)
	REFERENCES `Accounts`.`UserBusiness`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
CONSTRAINT `userbusinesspreference_preference`
	FOREIGN KEY (`prefID`)
	REFERENCES `Accounts`.`Preference`(`id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
;	
