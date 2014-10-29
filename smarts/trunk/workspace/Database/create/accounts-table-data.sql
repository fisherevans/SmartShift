INSERT INTO `Accounts`.`ContactMethod` (`name`) VALUES ('email');
INSERT INTO `Accounts`.`ContactMethod` (`name`) VALUES ('phone');

INSERT INTO `Accounts`.`User` (`username`, `passHash`, `email`, `fName`, `lName`, `createTS`)
VALUES ('testuser', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'contact+test@fisherevans.com', 'Test', 'User', NOW());
