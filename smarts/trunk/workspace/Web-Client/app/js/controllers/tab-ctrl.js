angular.module('smartsApp').controller('TabController', ['$scope', '$rootScope', '$location',
    function($scope, $rootScope, $location){
        var tabController = this;
        tabController.currentTab = 1;
        tabController.navAvailable = true;
        tabController.navTabs = [
            { "tabID":1, "link":"newsfeed", "iconClass":"icon-newspaper", "label":"News Feed" },
            { "tabID":2, "link":"messages", "iconClass":"icon-chat",      "label":"Messages" },
            { "tabID":3, "link":"requests", "iconClass":"icon-drawer",    "label":"Requests" },
            { "tabID":4, "link":"schedule", "iconClass":"icon-calendar",  "label":"Schedule" },
            { "tabID":5, "link":"groups",   "iconClass":"icon-users",     "label":"Group Management", "otherClass":"navElementAddPadding" },
            { "tabID":6, "link":"settings", "iconClass":"icon-cog",       "label":"Settings" }
        ];

        // Update tabs on location change
        $rootScope.$on("$locationChangeStart", function(event, next, current){
            var split = next.split("#");
            if(split.length > 1) {
                var path = split[1];
                if (path.startsWith("/messages"))
                    tabController.setTab(2);
                else if (path.startsWith("/requests"))
                    tabController.setTab(3);
                else if (path.startsWith("/schedule"))
                    tabController.setTab(4);
                else if (path.startsWith("/groups"))
                    tabController.setTab(5);
                else if (path.startsWith("/settings"))
                    tabController.setTab(6);
                else
                    tabController.setTab(1);
            }
        });

        tabController.setTab = function(tab){
            if(this.navAvailable){
                this.navAvailable = false;
                this.currentTab = tab;
                this.navAvailable = true;
            }
        };

        tabController.isSet = function(tab){
            return this.currentTab === tab;
        };
    }
]);