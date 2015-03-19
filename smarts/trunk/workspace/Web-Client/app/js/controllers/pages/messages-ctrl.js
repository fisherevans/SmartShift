var threads = [
    {
        name: "Charlie Babcock",
        text: "Fuck Eclipse",
        sender: "You",
        time: "Just now"
    },
    {
        name: "Drew Fead",
        text: " This is a really fucking long message that really is a run on and has no meaning so I can just fuck with the css styling!",
        sender: "You",
        time: "A few seconds ago"
    },
    {
        name: "Fisher Evans",
        text: "Here is some text. Here is some more text",
        sender: "Fisher",
        time: "Just now"
    },
    {
        name: "Bossman",
        text: "Yeah, no. You're working this weekend.",
        sender: "Boss",
        time: "25 min ago"
    },
    {
        name: "Charlie Babcock",
        text: "No, really, fuck Eclipse",
        sender: "You",
        time: "2 hrs ago"
    }
];

angular.module('smartsApp').controller('MessagesController', [ '$scope', '$rootScope',
    function($scope, $rootScope){
        this.threads = threads;
        $scope.threadSearch = '';

        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Messaging" }
        ]);


        $scope.$on("$routeChangeSuccess", function (scope, next, current) {
            $scope.transitionState = "active"
        });
    }
]);