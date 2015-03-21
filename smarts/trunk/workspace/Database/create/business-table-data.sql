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

INSERT INTO `Group` (`name`) VALUES ('Root Group');                   -- 1
INSERT INTO `Group` (`parentID`, `name`) VALUES ('1', 'Kitchen');     -- 2
INSERT INTO `Group` (`parentID`, `name`) VALUES ('1', 'Dining Room'); -- 3
INSERT INTO `Group` (`parentID`, `name`) VALUES ('3', 'Bar');         -- 4

INSERT INTO `Role` (`name`) VALUES ('Management');  -- 1
INSERT INTO `Role` (`name`) VALUES ('Chef');        -- 2
INSERT INTO `Role` (`name`) VALUES ('Dish Washer'); -- 3
INSERT INTO `Role` (`name`) VALUES ('Prep');        -- 4
INSERT INTO `Role` (`name`) VALUES ('Host');        -- 5
INSERT INTO `Role` (`name`) VALUES ('Server');      -- 6
INSERT INTO `Role` (`name`) VALUES ('Cleaning');    -- 7
INSERT INTO `Role` (`name`) VALUES ('Bartender');   -- 8

INSERT INTO `GroupRole` (`grpID`, `roleID`) VALUES ('1', '1'); -- 1
INSERT INTO `GroupRole` (`grpID`, `roleID`) VALUES ('2', '2'); -- 2
INSERT INTO `GroupRole` (`grpID`, `roleID`) VALUES ('2', '3'); -- 3
INSERT INTO `GroupRole` (`grpID`, `roleID`) VALUES ('2', '4'); -- 4 
INSERT INTO `GroupRole` (`grpID`, `roleID`) VALUES ('3', '5'); -- 5 
INSERT INTO `GroupRole` (`grpID`, `roleID`) VALUES ('3', '6'); -- 6 
INSERT INTO `GroupRole` (`grpID`, `roleID`) VALUES ('3', '7'); -- 7 
INSERT INTO `GroupRole` (`grpID`, `roleID`) VALUES ('4', '8'); -- 8 

INSERT INTO `GroupRoleCapability` (`grpRoleID`, `capID`) VALUES ('1', '999');

INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('1', '1', 'Chris', 'Billups');   -- 1
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('2', '2', 'Fisher', 'Evans');    -- 2
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('3', '2', 'Drew', 'Fead');       -- 3
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('4', '3', 'Charlie', 'Babcock'); -- 4
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('5', '3', 'Peter', 'Chapin');    -- 5
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('6', '3', 'Lisa', 'Steinman');   -- 6
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('7', '4', 'Nancy', 'Mai');       -- 7

INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('1', '1');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('2', '2');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('2', '3');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('3', '4');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('3', '5');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('3', '6');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('4', '7');

INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('1', '1');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('6', '2');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('3', '2');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('4', '2');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('5', '3');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('6', '3');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('7', '3');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('6', '4');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('7', '4');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('7', '5');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('5', '6');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('8', '7');
