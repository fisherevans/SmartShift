angular.module('smartsApp').controller('TabController', ['$location',
    function($location){
        this.currentTab = (function(){
            switch($location.path()){
            case '/messages':
                return 2;
                break;
            case '/requests':
                return 3;
                break;
            case '/schedule':
                return 4;
                break;
            case '/groups':
                return 5;
                break;
            case '/settings':
                return 6;
                break;
            default :
                return 1;
        }})();
        this.navAvailable = true;
        this.setTab = function(tab){
            if(this.navAvailable){
                this.navAvailable = false;
                this.currentTab = tab;
                this.navAvailable = true;
            }
        };
        this.isSet = function(tab){
            return this.currentTab === tab;
        };
    }
]);