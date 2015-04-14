angular.module('smartsDirectives')
    .directive('ngBlur', function () {
        return {
            restrict: 'A',
            link: function (scope, element, attr) {
                element.bind('blur', function () {
                    scope.$apply(attr.ngBlur);
                })
            }
        }
    })
    .directive("enableAnimations", function ($animate) {
        return function (scope, element) {
            $animate.enabled(false, element);
        };
    })
    .directive('elementRendered', function() {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                element.on('load', function(event) {
                    scope.$apply(attrs.elementRendered);
                });
            }
        };
    })
;