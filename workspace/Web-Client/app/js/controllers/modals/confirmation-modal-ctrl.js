angular.module('smartsApp').controller('ConfirmationModalController', ['$modalInstance', '$scope', 'windowData',
    function($modalInstance, $scope, windowData){
        $scope.title = windowData.title;
        $scope.content = windowData.content;
        $scope.noText = windowData.noText == undefined ? "No" : windowData.noText;
        $scope.yesText = windowData.yesText == undefined ? "Yes" : windowData.yesText;
        $scope.centered = windowData.centered == undefined ? true : windowData.centered;

        $scope.yes = function() {
            $modalInstance.close(true);
        };

        $scope.no = function() {
            $modalInstance.close(false);
        };
    }
]);