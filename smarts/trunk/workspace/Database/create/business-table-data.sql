
INSERT INTO `Capability` (`id`, `name`) VALUES ('0', 'Add Employees');
INSERT INTO `Capability` (`id`, `name`) VALUES ('2', 'Edit Employees');
INSERT INTO `Capability` (`id`, `name`) VALUES ('4', 'Delete Employees');
INSERT INTO `Capability` (`id`, `name`) VALUES ('10', 'Add Roles');
INSERT INTO `Capability` (`id`, `name`) VALUES ('12', 'Edit Roles');
INSERT INTO `Capability` (`id`, `name`) VALUES ('14', 'Delete Roles');
INSERT INTO `Capability` (`id`, `name`) VALUES ('20', 'Add Child Groups');
INSERT INTO `Capability` (`id`, `name`) VALUES ('22', 'Edit Child Groups');
INSERT INTO `Capability` (`id`, `name`) VALUES ('24', 'Delete Child Groups');
INSERT INTO `Capability` (`id`, `name`) VALUES ('26', 'Manage Child Groups');
INSERT INTO `Capability` (`id`, `name`) VALUES ('999', 'Master Wizard');

INSERT INTO `Group` (`id`, `name`) VALUES ('1', 'Root Group');                   -- 1
INSERT INTO `Group` (`id`, `parentID`, `name`) VALUES ('2', '1', 'Kitchen');     -- 2
INSERT INTO `Group` (`id`, `parentID`, `name`) VALUES ('3', '1', 'Dining Room'); -- 3
INSERT INTO `Group` (`id`, `parentID`, `name`) VALUES ('4', '3', 'Bar');         -- 4

INSERT INTO `Role` (`id`, `name`) VALUES ('1', 'Management');  -- 1
INSERT INTO `Role` (`id`, `name`) VALUES ('2', 'Chef');        -- 2
INSERT INTO `Role` (`id`, `name`) VALUES ('3', 'Dish Washer'); -- 3
INSERT INTO `Role` (`id`, `name`) VALUES ('4', 'Prep');        -- 4
INSERT INTO `Role` (`id`, `name`) VALUES ('5', 'Host');        -- 5
INSERT INTO `Role` (`id`, `name`) VALUES ('6', 'Server');      -- 6
INSERT INTO `Role` (`id`, `name`) VALUES ('7', 'Cleaning');    -- 7
INSERT INTO `Role` (`id`, `name`) VALUES ('8', 'Bartender');   -- 8

INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('1', '1', '1'); -- 1
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('2', '2', '2'); -- 2
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('3', '2', '3'); -- 3
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('4', '2', '4'); -- 4 
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('5', '3', '5'); -- 5 
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('6', '3', '6'); -- 6 
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('7', '3', '7'); -- 7 
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('8', '4', '8'); -- 8 

INSERT INTO `GroupRoleCapability` (`id`, `grpRoleID`, `capID`) VALUES ('1', '999');

INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('1', '1', '1', 'Chris', 'Billups');   -- 1
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('2', '2', '2', 'Fisher', 'Evans');    -- 2
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('3', '3', '2', 'Drew', 'Fead');       -- 3
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('4', '4', '3', 'Charlie', 'Babcock'); -- 4
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('5', '5', '3', 'Peter', 'Chapin');    -- 5
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('6', '6', '3', 'Lisa', 'Steinman');   -- 6
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('7', '7', '4', 'Nancy', 'Mai');       -- 7

INSERT INTO `GroupEmployee` (`id`, `grpID`, `empID`) VALUES ('1', '1', '1');
INSERT INTO `GroupEmployee` (`id`, `grpID`, `empID`) VALUES ('2', '2', '2');
INSERT INTO `GroupEmployee` (`id`, `grpID`, `empID`) VALUES ('3', '2', '3');
INSERT INTO `GroupEmployee` (`id`, `grpID`, `empID`) VALUES ('4', '3', '4');
INSERT INTO `GroupEmployee` (`id`, `grpID`, `empID`) VALUES ('5', '3', '5');
INSERT INTO `GroupEmployee` (`id`, `grpID`, `empID`) VALUES ('6', '3', '6');
INSERT INTO `GroupEmployee` (`id`, `grpID`, `empID`) VALUES ('7', '4', '7');

INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('1', '1', '1');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('2', '1', '2');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('3', '6', '2');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('4', '3', '2');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('5', '4', '2');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('6', '5', '3');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('7', '6', '3');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('8', '7', '3');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('9', '6', '4');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('10', '7', '4');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('11', '7', '5');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('12', '5', '6');
INSERT INTO `GroupRoleEmployee` (`id`, `grpRoleID`, `empID`) VALUES ('13', '8', '7');
