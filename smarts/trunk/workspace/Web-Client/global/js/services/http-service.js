'use strict'

angular.module('smartsServices').factory('httpService', ['$http', '$q', '$rootScope',
    function($http, $q, $rootScope) {
        var httpService = {};

        httpService.httpCallID = 1;

        httpService.httpRequest = function(server, username, password, method, path, data) {
            var request = {
                method: method,
                //url: 'http://localhost:8080' + path,
                url: server + path,
                headers: {
                    'Authorization' : 'Basic ' + window.btoa(username + ':' + password),
                    'Content-Type' : 'application/json'
                },
                data: data
            };
            $rootScope.api.waitingCalls++;
            var callID = httpService.httpCallID++;
            console.log("HTTP Call " + callID);
            console.log(request);
            var defer = $q.defer();
            $http(request).then(
                function(response, status, headers, config) {
                    console.log("HTTP Response (Success) " + callID);
                    console.log(response);
                    defer.resolve(response.data);
                    $rootScope.api.waitingCalls--;
                },
                function(response, status, headers, config) {
                    console.log("HTTP Response (Error) " + callID);
                    console.log(response);
                    if(response.status == 401) $rootScope.handleHTTP401();
                    defer.reject(response);
                    $rootScope.api.waitingCalls--;
                }
            );
            return defer.promise;
        };

        httpService.businessRequest = function(method, path, data) {
            return httpService.httpRequest($rootScope.api.businessServer, $rootScope.api.username, $rootScope.api.sessionID, method, path, data);
        };

        httpService.accountsRequest = function(method, path, data) {
            return httpService.httpRequest($rootScope.api.accountsServer, $rootScope.api.username, $rootScope.api.password, method, path, data);
        };

        httpService.business = {
            put: function(path, data) {
                return httpService.businessRequest('PUT', path, data);
            },
            get: function(path, data) {
                return httpService.businessRequest('GET', path, data);
            },
            post: function(path, data) {
                return httpService.businessRequest('POST', path, data);
            },
            delete: function(path, data) {
                return httpService.businessRequest('DELETE', path, data);
            }
        };

        httpService.accounts = {
            put: function(path, data) {
                return httpService.accountsRequest('PUT', path, data);
            },
            get: function(path, data) {
                return httpService.accountsRequest('GET', path, data);
            },
            post: function(path, data) {
                return httpService.accountsRequest('POST', path, data);
            },
            delete: function(path, data) {
                return httpService.accountsRequest('DELETE', path, data);
            }
        };

        return httpService;
    }
]);