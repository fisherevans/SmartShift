angular.module('smartsServices').factory('accountsService', ['$rootScope', 'httpService',
    function($rootScope, httpService) {
        httpService.setAuth($rootScope.username, $rootScope.password);
        return {
            getSelf: function(){
                return httpService.get('/accounts/user/self');
            },
            getFull: function(user, pass){
                httpService.setAuth(user, pass);
                return httpService.get('/accounts/user/full');
            },
            getSession: function(businessId, employeeId){
                var data = {
                    businessID: businessId,
                    employeeID: employeeId
                };
                return httpService.put('/accounts/user/session', data);

            },
            util: {
                login: function() {

                }
            }
        }
    }
]);