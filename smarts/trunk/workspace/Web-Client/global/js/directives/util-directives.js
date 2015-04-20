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
//    .directive("disableAnimations", function ($animate) {
//        return function (scope, element) {
//            $animate.enabled(false, element);
//        };
//    })
    .directive('elementRendered', function($timeout) {
        return {
            restrict: 'A',
            scope: { elementRendered : '&' },
            link: function(scope, element, attrs) {
                $timeout(function () {
                    scope.$apply(scope.elementRendered());
                }, 50);
            }
        };
    })
    .directive('focusOnRender', function ($timeout) {
        return {
            scope: { focusOnRender : '=' },
            link: function (scope, element, attrs, model) {
                if(angular.isUndefined(scope.focusOnRender) || scope.focusOnRender == true) {
                    $timeout(function () {
                        element[0].focus();
                    }, 50);
                }
            }
        };
    });
;