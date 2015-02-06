-- Initial Dara
INSERT INTO `Accounts`.`ContactMethod` (`id`, `name`) VALUES (1, 'email');
INSERT INTO `Accounts`.`ContactMethod` (`id`, `name`) VALUES (2, 'phone');

INSERT INTO `Accounts`.`Preference` (`id`, `name`) VALUES (1, 'getSpamEmail');
INSERT INTO `Accounts`.`Preference` (`id`, `name`) VALUES (2, 'somePreference');

-- Test Data
INSERT INTO `Accounts`.`Build` (`id`, `version`) VALUES (1, 1);

INSERT INTO `Accounts`.`Server` (`id`, `hostname`, `ipAddr`) VALUES (1, 'localhost', '127.0.0.1');

INSERT INTO `Accounts`.`Address` (`id`, `street1`, `street2`, `city`, `subDivision`, `country`, `postalCode`, `phoneNumber`) VALUES (1, '101 Loop Lane', 'Suite 8', 'Somewhere', 'In Cali', 'USA', '12345', '18885551234');

INSERT INTO `Accounts`.`Business` (`id`, `name`, `addressID`, `buildID`, `servID`) VALUES (1, 'Babcock & Sons', 1, 1, 1);
INSERT INTO `Accounts`.`Business` (`id`, `name`, `buildID`, `servID`) VALUES (2, 'ABC Corp.', 1, 1);
INSERT INTO `Accounts`.`Business` (`id`, `name`, `buildID`, `servID`) VALUES (3, 'Silph Co.', 1, 1);

INSERT INTO `Accounts`.`User` (`id`, `username`, `passHash`, `email`) VALUES (1, 'testuser', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'test@email.com');
INSERT INTO `Accounts`.`User` (`id`, `username`, `passHash`, `email`) VALUES (2, 'fisher', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'fisher@email.com');
INSERT INTO `Accounts`.`User` (`id`, `username`, `passHash`, `email`) VALUES (3, 'drew', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'drew@email.com');
INSERT INTO `Accounts`.`User` (`id`, `username`, `passHash`, `email`) VALUES (4, 'charlie', '$2a$10$HxaCFIvtTGuv038oOpL1muLiZZLpjm8MOT2ymDjoYrPYyf3007bym', 'charlie@email.com');

INSERT INTO `Accounts`.`UserBusinessEmployee` (`userID`, `busID`, `empID`) VALUES (1, 1, 1);
INSERT INTO `Accounts`.`UserBusinessEmployee` (`userID`, `busID`, `empID`) VALUES (2, 1, 2);
INSERT INTO `Accounts`.`UserBusinessEmployee` (`userID`, `busID`, `empID`) VALUES (3, 2, 4);
INSERT INTO `Accounts`.`UserBusinessEmployee` (`userID`, `busID`, `empID`) VALUES (3, 3, 5);

INSERT INTO `Accounts`.`Registration` (`businessID`, `employeeID`, `verificationCode`, `email`) VALUES (2, 3, 'SOMECODE', 'fisher@email.com');