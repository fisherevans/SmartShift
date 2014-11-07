INSERT INTO `Accounts`.`ContactMethod` (`name`) VALUES ('email');
INSERT INTO `Accounts`.`ContactMethod` (`name`) VALUES ('phone');
INSERT INTO `accounts`.`nextid` (`id`, `name`, `nextID`) VALUES (1, 'employee', '10');


INSERT INTO `accounts`.`user` (`id`, `username`, `passHash`, `email`, `createTS`) VALUES ('1', 'testuser', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'test@email.com', NOW());
INSERT INTO `accounts`.`user` (`id`, `username`, `passHash`, `email`, `createTS`) VALUES ('2', 'fisher', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'fisher@email.com', NOW());
INSERT INTO `accounts`.`user` (`id`, `username`, `passHash`, `email`, `createTS`) VALUES ('3', 'drew', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'drew@email.com', NOW());
INSERT INTO `accounts`.`user` (`id`, `username`, `passHash`, `email`, `createTS`) VALUES ('4', 'charlie', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'charlie@email.com', NOW());

INSERT INTO `accounts`.`build` (`id`, `version`, `createTS`) VALUES ('1', '1', NOW());

INSERT INTO `accounts`.`server` (`id`, `hostname`, `ipAddr`) VALUES ('1', 'localhost', '127.0.0.1');

INSERT INTO `accounts`.`business` (`id`, `name`, `buildID`, `servID`) VALUES (1, 'ABC Corp.', '1', '1');
INSERT INTO `accounts`.`business` (`id`, `name`, `buildID`, `servID`) VALUES (2, 'Silph Co.', '1', '1');

INSERT INTO `accounts`.`userbusiness` (`userID`, `busID`, `joinTS`) VALUES ('1', '1', NOW());
INSERT INTO `accounts`.`userbusiness` (`userID`, `busID`, `joinTS`) VALUES ('2', '1', NOW());
INSERT INTO `accounts`.`userbusiness` (`userID`, `busID`, `joinTS`) VALUES ('2', '2', NOW());
INSERT INTO `accounts`.`userbusiness` (`userID`, `busID`, `joinTS`) VALUES ('3', '2', NOW());
