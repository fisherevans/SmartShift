'use strict'

angular.module('smartsServices').factory('httpService', ['$http',
    function($http) {
        var API_URL = 'http://lando.smartshift.info:6380';
        return {
            get: function(path){
                var req = {
                    method: 'GET',
                    url: API_URL + path
                };

                return $http(req);
            },
            put: function(path, data){
                $http.defaults.headers.put = { 'Content-Type' : 'application/json'};
                return $http.put((API_URL + path), data);
            },
            post: function(path, data){
                console.log("Path: " + path);
                console.log(data);
                $http.defaults.headers.put = { 'Content-Type' : 'application/json'};
                return $http.post((API_URL + path), data);
            },
            setAuth: function(user, pass){
                var base64 = window.btoa(user + ':' + pass);
                $http.defaults.headers.common.Authorization = 'Basic ' + base64;
            },
            setRootPath: function(server){
                API_URL = 'http://' + server;
            }

        }
    }
]);