INSERT INTO `Accounts`.`ContactMethod` (`name`) VALUES ('email');
INSERT INTO `Accounts`.`ContactMethod` (`name`) VALUES ('phone');

-- USER 1
INSERT INTO `Accounts`.`User` (`username`, `passHash`, `email`, `createTS`)
VALUES ('testuser', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'contact+test@fisherevans.com', NOW());

-- USER 2
INSERT INTO `Accounts`.`User` (`username`, `passHash`, `email`, `createTS`)
VALUES ('fisher', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'contact+fisher@fisherevans.com', NOW());

-- USER 3
INSERT INTO `Accounts`.`User` (`username`, `passHash`, `email`, `createTS`)
VALUES ('lisa', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'contact+lisa@fisherevans.com', NOW());


-- BUILD 1
INSERT INTO `Accounts`.`Build` (`version`, `createTS`, `sqlDir`)
VALUES ('1.0', NOW(), 'idk what this is');

-- SERV 1
INSERT INTO `Accounts`.`Server` (`hostname`, `ipAddr`)
VALUES ('localhost', '127.0.0.1');


-- BUSI 1
INSERT INTO `Accounts`.`Business` (`name`, `buildID`, `servID`)
VALUES ('ABC Corp.', 1, 1);

-- BUSI 2
INSERT INTO `Accounts`.`Business` (`name`, `buildID`, `servID`)
VALUES ('Smart Tools', 1, 1);


-- TestUser - ABC
INSERT INTO `Accounts`.`UserBusiness` (`userID`, `busID`, `joinTS`)
VALUES (1, 1, NOW());

-- Fisher - ABC
INSERT INTO `Accounts`.`UserBusiness` (`userID`, `busID`, `joinTS`)
VALUES (2, 1, NOW());

-- Fisher - Smart
INSERT INTO `Accounts`.`UserBusiness` (`userID`, `busID`, `joinTS`)
VALUES (2, 2, NOW());

-- Lisa - Smart
INSERT INTO `Accounts`.`UserBusiness` (`userID`, `busID`, `joinTS`)
VALUES (2, 2, NOW());




