'use strict'

angular.module('smartsServices').factory('businessService', ['httpService', '$rootScope',
    function(httpService, $rootScope){
        return {
            getFull: function(){
                httpService.setAuth($rootScope.username, $rootScope.sessionID);
                return httpService.get('/business/dev/fullCache');
            },
            addEmployee: function(employeeModel){
                httpService.setAuth($rootScope.username, $rootScope.sessionID);
                return httpService.put('/business/employee', employeeModel);
            },
            updateEmployee: function(employeeModel){
                httpService.setAuth($rootScope.username, $rootScope.sessionID);
                return httpService.post('/business/employee', employeeModel);
            }
        }
    }
]);