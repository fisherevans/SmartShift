'use strict';

var KeyCodes = {
    BACKSPACE : 8,
    TABKEY : 9,
    RETURNKEY : 13,
    ESCAPE : 27,
    SPACEBAR : 32,
    LEFTARROW : 37,
    UPARROW : 38,
    RIGHTARROW : 39,
    DOWNARROW : 40,
};

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
    'smartsDirectives',
    'ngCookies'
]);