/**
 * Created by charlie on 1/27/15.
 */
angular.module('storefrontApp', [])
    .directive('username', function() {
        return {
            restrict: 'A',
            link: function(){}
        }

    })
    .directive('ngBlur', function () {
        return {
            restrict: 'A',
            link: function (scope, element, attr) {
                element.bind('blur', function () {
                    scope.$apply(attr.ngBlur);
                })
            }
        }
    });