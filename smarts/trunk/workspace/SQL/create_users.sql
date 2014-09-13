create database smartshift;
CREATE USER 'smarts'@'localhost' IDENTIFIED BY 'smarts';
grant all privileges on smartshift.* to 'smarts'@'localhost';
commit;


CREATE TABLE `smartshift`.`artists` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `artist_name` VARCHAR(45) NOT NULL,
  `date_born` DATE NOT NULL,
  `date_died` DATE NULL,
  `gender` VARCHAR(1) NOT NULL,
  `location_born` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

  CREATE TABLE `smartshift`.`works` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `artist_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `smartshift`.`works` 
ADD INDEX `works_artist_idx` (`artist_id` ASC);
ALTER TABLE `smartshift`.`works` 
ADD CONSTRAINT `works_artist`
  FOREIGN KEY (`artist_id`)
  REFERENCES `smartshift`.`artists` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

INSERT INTO `smartshift`.`artists` (`id`, `artist_name`, `date_born`, `date_died`, `gender`, `location_born`) VALUES (1, 'Leonardo da Vinci', '15-APR-1452', '2-MAY-1519', 'M', 'Vinci, Republic of Florence');
INSERT INTO `smartshift`.`artists` (`id`, `artist_name`, `date_born`, `date_died`, `gender`, `location_born`) VALUES (2, 'Pablo Picasso', '25-OCT-1881', '8-APR-1973', 'M', 'Malaga, Spain');
INSERT INTO `smartshift`.`artists` (`id`, `artist_name`, `date_born`, `date_died`, `gender`, `location_born`) VALUES (3, 'Vincent van Gogh', '30-MAR-1853', '29-JUL-1890', 'M', 'Zundert, Netherlands');
INSERT INTO `smartshift`.`artists` (`id`, `artist_name`, `date_born`, `date_died`, `gender`, `location_born`) VALUES (4, 'Artemisia Gentileschi', '8-JUL-1593', '1-JAN-1639', 'F', 'Rome, Italy');

INSERT INTO `smartshift`.`works` (`artist_id`, `name`) VALUES ('1', 'Baptism of Christ');
INSERT INTO `smartshift`.`works` (`artist_id`, `name`) VALUES ('1', 'Verrocchio');
INSERT INTO `smartshift`.`works` (`artist_id`, `name`) VALUES ('1', 'St. Jerome in the Wilderness');
INSERT INTO `smartshift`.`works` (`artist_id`, `name`) VALUES ('2', 'La Vie');
INSERT INTO `smartshift`.`works` (`artist_id`, `name`) VALUES ('2', 'Woman with Mustard Pot');
INSERT INTO `smartshift`.`works` (`artist_id`, `name`) VALUES ('4', 'Judith Slaying Holofernes');

