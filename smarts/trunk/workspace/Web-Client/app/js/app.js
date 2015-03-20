'use strict';

Array.prototype.findBy = function(key, value) {
    for(var i in this){
        if(this.hasOwnProperty(i)
            && this[i].hasOwnProperty(key)
            && this[i][key] == value) {
            return this[i];
        }
    }
    return undefined;
};

var app = angular.module('smartsApp', [
    'ngRoute',
    'ngAnimate',
    'ui.calendar',
    'ui.tree',
    'ui.bootstrap',
    'smartsServices',
    'ngCookies'
]);