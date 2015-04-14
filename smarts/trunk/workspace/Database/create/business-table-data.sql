
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

INSERT INTO `Group` (`id`, `name`) VALUES ('1', 'Root Group');
INSERT INTO `Group` (`id`, `parentID`, `name`) VALUES ('2', '1', 'Kitchen');
INSERT INTO `Group` (`id`, `parentID`, `name`) VALUES ('3', '1', 'Dining Room');
INSERT INTO `Group` (`id`, `parentID`, `name`) VALUES ('4', '3', 'Bar');

INSERT INTO `Role` (`id`, `name`) VALUES ('1', 'Management');
INSERT INTO `Role` (`id`, `name`) VALUES ('2', 'Chef');
INSERT INTO `Role` (`id`, `name`) VALUES ('3', 'Dish Washer');
INSERT INTO `Role` (`id`, `name`) VALUES ('4', 'Prep');
INSERT INTO `Role` (`id`, `name`) VALUES ('5', 'Host');
INSERT INTO `Role` (`id`, `name`) VALUES ('6', 'Server');
INSERT INTO `Role` (`id`, `name`) VALUES ('7', 'Cleaning');
INSERT INTO `Role` (`id`, `name`) VALUES ('8', 'Bartender');

INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('1', '1', '1');
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('2', '2', '2');
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('3', '2', '3');
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('4', '2', '4');
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('5', '3', '5');
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('6', '3', '6');
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('7', '3', '7');
INSERT INTO `GroupRole` (`id`, `grpID`, `roleID`) VALUES ('8', '4', '8');

INSERT INTO `GroupRoleCapability` (`grpRoleID`, `capID`) VALUES ('1', '999');

INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('1', '1', 'Chris', 'Billups');
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('2', '2', 'Fisher', 'Evans');
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('3', '2', 'Drew', 'Fead');
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('4', '3', 'Charlie', 'Babcock');
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('5', '3', 'Peter', 'Chapin');
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('6', '3', 'Lisa', 'Steinman');
INSERT INTO `Employee` (`id`, `defaultGrpID`, `fName`, `lName`) VALUES ('7', '4', 'Nancy', 'Mai');

INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('1', '1');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('2', '2');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('2', '3');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('3', '4');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('3', '5');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('3', '6');
INSERT INTO `GroupEmployee` (`grpID`, `empID`) VALUES ('4', '7');

INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('1', '1');
INSERT INTO `GroupRoleEmployee` (`grpRoleID`, `empID`) VALUES ('1', '2');
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
