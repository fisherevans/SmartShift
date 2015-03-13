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
            }
        }
    }
]);