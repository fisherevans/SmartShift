angular.module('smartsServices').factory('accountsService', ['$rootScope', 'httpService',
    function($rootScope, httpService) {
        var accountsService = {};

        accountsService.getSelf = function() {
            return httpService.accounts.get('/accounts/user/self');
        };

        accountsService.getFull = function() {
            return httpService.accounts.get('/accounts/user/full');
        };

        accountsService.getSession = function(businessID, employeeID) {
            return httpService.accounts.put('/accounts/user/session', {
                businessID: businessID,
                employeeID: employeeID
            });
        };

        return accountsService;
    }
]);