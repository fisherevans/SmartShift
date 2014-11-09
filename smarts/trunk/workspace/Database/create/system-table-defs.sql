DROP USER 'smarts'@'localhost';
FLUSH PRIVILEGES;
CREATE USER 'smarts'@'localhost' IDENTIFIED BY 'smarts';

-- Database
DROP DATABASE IF EXISTS System;
CREATE DATABASE System;
GRANT ALL PRIVILEGES ON System.* TO 'smarts'@'localhost';
COMMIT;

DROP TABLE IF EXISTS `System`.`NextID`;
CREATE TABLE `System`.`NextID` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	`nextID` INT NOT NULL DEFAULT 0,
	PRIMARY KEY (`id`),
	UNIQUE (`name`)
);