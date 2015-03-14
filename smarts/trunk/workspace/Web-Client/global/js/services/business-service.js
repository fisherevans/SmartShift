'use strict'

angular.module('smartsServices').factory('businessService', ['httpService', '$rootScope',
    function(httpService, $rootScope){
        return {
            getFull: function(){
                httpService.setAuth($rootScope.username, $rootScope.sessionID);
                return httpService.get('/business/dev/fullCache');
            },
            addEmployee: function(firstName, lastName, homeGroupID){
                httpService.setAuth($rootScope.username, $rootScope.sessionID);
                return httpService.put('/business/employee', {
                    "firstName":firstName,
                    "lastName":lastName,
                    "homeGroupID":homeGroupID
                });
            }
        }
    }
]);