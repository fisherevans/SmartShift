'use strict'

angular.module('smartsServices').factory('utilService', [
    function() {
        return {
            getSize : function( obj ){
                var size = 0, key;
                for (key in obj) {
                    if (obj.hasOwnProperty(key)) size++;
                }
                return size;
            },
            getKeys : function( obj ){
                var size = 0, key;
                var keys = [];
                for ( key in obj ){
                    if( obj.hasOwnProperty(key)) keys.push(key);
                }
                return keys;
            },
            validName: function(name) {
                if(name == null || name === undefined)
                    return false;
                if(name.length < 1 || name.length > 40)
                    return false;
                return true;
            },
            getGroupSelectOption: function(groups, includeAll) {
                var groupOptions = [];
                if(includeAll) {
                    groupOptions.push({
                        "name": "[All Groups]",
                        "id": 0
                    });
                }
                var generateGroupOptions = function(group, prefix) {
                    groupOptions.push({
                        "id":group.id,
                        "name":prefix + " " + group.name
                    });
                    angular.forEach(group.childGroups, function(childGroup, childGroupID) {
                        generateGroupOptions(childGroup, prefix + "---");
                    });
                }
                angular.forEach(groups, function(group, groupID) {
                    if(group.parentGroupID == null)
                        generateGroupOptions(group, "");
                });
                return groupOptions;
            },
            getRoleSelectOptions: function(roles, includeAll) {
                var roleOptions = [];
                if(includeAll) {
                    roleOptions.push({
                        "name": "[All Roles]",
                        "id": 0
                    });
                }
                angular.forEach(roles, function(role, roleID) {
                    roleOptions.push({
                        "name": role.name,
                        "id": role.id
                    });
                });
                return roleOptions;
            },
            getWeeks: function(back, forward) {
                var weeks = [];
                var now = new Date();
                var today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                for(var id = -back;id < forward;id++) {
                    var sunday = new Date(today.setDate(today.getDate()-today.getDay()));
                    sunday.setDate(sunday.getDate()+id*7);
                    var saturday = new Date(sunday);
                    saturday.setDate(sunday.getDate()+6);
                    var week = {
                        "id":id,
                        "sunday":sunday,
                        "saturday":saturday,
                        "current":id == 0
                    };
                    weeks[id] = week;
                }
                return weeks;
            }
        }
    }
]);