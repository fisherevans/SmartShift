var threads = [
    {
        id: 2,
        name: "Charlie Babcock",
        text: "Drew is creating a hostile work environment.",
        sender: "You",
        time: "Just now"
    },
    {
        id: 3,
        name: "Drew Fead",
        text: " This is a really long message that really is a run on and has no meaning just so I can mess with the CSS styling!",
        sender: "You",
        time: "A few seconds ago"
    },
    {
        id: 4,
        name: "Pointy H. Boss",
        text: "Yeah, no. You're working this weekend.",
        sender: "Pointy",
        time: "25 minutes ago"
    },
    {
        id: 16,
        name: "Company Robot",
        text: "I'm not just some coffee machine.",
        sender: "Company",
        time: "Last week"
    }
];

angular.module('smartsApp').controller('MessagesController', [ '$scope', '$rootScope',
    function($scope, $rootScope){
        this.threads = [
            {
                id: 2,
                name: "Charlie Babcock",
                text: "Drew is creating a hostile work environment.",
                sender: "You",
                time: "Just now"
            },
            {
                id: 3,
                name: "Drew Fead",
                text: " This is a really long message that really is a run on and has no meaning just so I can mess with the CSS styling!",
                sender: "You",
                time: "A few seconds ago"
            },
            {
                id: 4,
                name: "Pointy H. Boss",
                text: "Yeah, no. You're working this weekend.",
                sender: "Pointy",
                time: "25 minutes ago"
            },
            {
                id: 16,
                name: "Company Robot",
                text: "I'm not your private coffee machine...",
                sender: "Company",
                time: "Last week"
            }
        ];

        this.messages = [
            {
                id: 1,
                sender: "Fisher",
                date: "January 1st",
                time: "11:30pm",
                texts: [
                    "Hey, I think your latest commit broke the DB Task Queue..."
                ]
            },
            {
                id: 3,
                sender: "Drew",
                date: "January 1st",
                time: "11:31pm",
                texts: [
                    "Ha.",
                    "Not possible."
                ]
            },
            {
                id: 2,
                sender: "Charlie",
                date: "January 2nd",
                time: "8:14am",
                texts: [
                    "I'm siding with Fisher on this one."
                ]
            },
            {
                id: 3,
                sender: "Drew",
                date: "January 2nd",
                time: "9:01am",
                texts: [
                    "What?!",
                    "You've never even TOUCHED the DB queue!"
                ]
            },
            {
                id: 1,
                sender: "Fisher",
                date: "January 3rd",
                time: "12:30am",
                texts: [
                    "Okay, fixed it.",
                    "Because, you know -- you were never going to get around to it...",
                ]
            },
            {
                id: 16,
                sender: "Company",
                date: "The time between years",
                time: "-65535",
                texts: [
                    "It's a small world, after all."
                ]
            },
            {
                id: 1,
                sender: "Fisher",
                date: "January 1st",
                time: "11:30pm",
                texts: [
                    "Hey, I think your latest commit broke the DB Task Queue..."
                ]
            },
            {
                id: 3,
                sender: "Drew",
                date: "January 1st",
                time: "11:31pm",
                texts: [
                    "Ha.",
                    "Not possible."
                ]
            },
            {
                id: 2,
                sender: "Charlie",
                date: "January 2nd",
                time: "8:14am",
                texts: [
                    "I'm siding with Fisher on this one."
                ]
            },
            {
                id: 3,
                sender: "Drew",
                date: "January 2nd",
                time: "9:01am",
                texts: [
                    "What?!",
                    "You've never even TOUCHED the DB queue!"
                ]
            },
            {
                id: 1,
                sender: "Fisher",
                date: "January 3rd",
                time: "12:30am",
                texts: [
                    "Okay, fixed it.",
                    "Because, you know -- you were never going to get around to it...",
                ]
            },
            {
                id: 16,
                sender: "Company",
                date: "The time between years",
                time: "-65535",
                texts: [
                    "It's a small world, after all."
                ]
            },
            {
                id: 1,
                sender: "Fisher",
                date: "January 1st",
                time: "11:30pm",
                texts: [
                    "Hey, I think your latest commit broke the DB Task Queue..."
                ]
            },
            {
                id: 3,
                sender: "Drew",
                date: "January 1st",
                time: "11:31pm",
                texts: [
                    "Ha.",
                    "Not possible."
                ]
            },
            {
                id: 2,
                sender: "Charlie",
                date: "January 2nd",
                time: "8:14am",
                texts: [
                    "I'm siding with Fisher on this one."
                ]
            },
            {
                id: 3,
                sender: "Drew",
                date: "January 2nd",
                time: "9:01am",
                texts: [
                    "What?!",
                    "You've never even TOUCHED the DB queue!"
                ]
            },
            {
                id: 1,
                sender: "Fisher",
                date: "January 3rd",
                time: "12:30am",
                texts: [
                    "Okay, fixed it.",
                    "Because, you know -- you were never going to get around to it...",
                ]
            },
            {
                id: 16,
                sender: "Company",
                date: "The time between years",
                time: "-65535",
                texts: [
                    "It's a small world, after all."
                ]
            },
            {
                id: 1,
                sender: "Fisher",
                date: "January 1st",
                time: "11:30pm",
                texts: [
                    "Hey, I think your latest commit broke the DB Task Queue..."
                ]
            },
            {
                id: 3,
                sender: "Drew",
                date: "January 1st",
                time: "11:31pm",
                texts: [
                    "Ha.",
                    "Not possible."
                ]
            },
            {
                id: 2,
                sender: "Charlie",
                date: "January 2nd",
                time: "8:14am",
                texts: [
                    "I'm siding with Fisher on this one."
                ]
            },
            {
                id: 3,
                sender: "Drew",
                date: "January 2nd",
                time: "9:01am",
                texts: [
                    "What?!",
                    "You've never even TOUCHED the DB queue!"
                ]
            },
            {
                id: 1,
                sender: "Fisher",
                date: "January 3rd",
                time: "12:30am",
                texts: [
                    "Okay, fixed it.",
                    "Because, you know -- you were never going to get around to it...",
                ]
            },
            {
                id: 16,
                sender: "Company",
                date: "The time between years",
                time: "-65535",
                texts: [
                    "It's a small world, after all."
                ]
            }
        ];

        $scope.threadSearch = '';

        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Messaging" }
        ]);

    }
]);