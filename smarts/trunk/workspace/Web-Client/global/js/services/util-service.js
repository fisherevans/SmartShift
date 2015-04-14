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
            getGroupSelectOption: function(groups) {
                var groupOptionsWorkingCopy = [];
                var generateGroupOptions = function(group, prefix) {
                    groupOptionsWorkingCopy.push({
                        "id":group.id,
                        "name":prefix + " " + group.name
                    });
                    angular.forEach(group.childGroups, function(childGroup, childGroupID) {
                        generateGroupOptions(childGroup, prefix + "---");
                    });
                }
                angular.forEach($scope.groups, function(group, groupID) {
                    $scope.selectedGroupRoles[group.id] = {};
                    if(group.parentGroupID == null) generateGroupOptions(group, "");
                });
                return groupOptionsWorkingCopy;
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