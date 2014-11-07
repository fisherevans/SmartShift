INSERT INTO `Accounts`.`ContactMethod` (`name`) VALUES ('email');
INSERT INTO `Accounts`.`ContactMethod` (`name`) VALUES ('phone');
INSERT INTO `Accounts`.`NextID` (`id`, `name`, `nextID`) VALUES (1, 'employee', '10');


INSERT INTO `Accounts`.`User` (`id`, `username`, `passHash`, `email`) VALUES ('1', 'testuser', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'test@email.com');
INSERT INTO `Accounts`.`User` (`id`, `username`, `passHash`, `email`) VALUES ('2', 'fisher', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'fisher@email.com');
INSERT INTO `Accounts`.`User` (`id`, `username`, `passHash`, `email`) VALUES ('3', 'drew', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'drew@email.com');
INSERT INTO `Accounts`.`User` (`id`, `username`, `passHash`, `email`) VALUES ('4', 'charlie', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'charlie@email.com');

INSERT INTO `Accounts`.`Build` (`id`, `version`) VALUES ('1', '1');

INSERT INTO `Accounts`.`Server` (`id`, `hostname`, `ipAddr`) VALUES ('1', 'localhost', '127.0.0.1');

INSERT INTO `Accounts`.`Business` (`id`, `name`, `buildID`, `servID`) VALUES (1, 'Babcock & Sons', '1', '1');
INSERT INTO `Accounts`.`Business` (`id`, `name`, `buildID`, `servID`) VALUES (2, 'ABC Corp.', '1', '1');
INSERT INTO `Accounts`.`Business` (`id`, `name`, `buildID`, `servID`) VALUES (3, 'Silph Co.', '1', '1');

INSERT INTO `Accounts`.`UserBusiness` (`userID`, `busID`) VALUES ('1', '1');
INSERT INTO `Accounts`.`UserBusiness` (`userID`, `busID`) VALUES ('2', '1');
INSERT INTO `Accounts`.`UserBusiness` (`userID`, `busID`) VALUES ('2', '2');
INSERT INTO `Accounts`.`UserBusiness` (`userID`, `busID`) VALUES ('3', '2');
