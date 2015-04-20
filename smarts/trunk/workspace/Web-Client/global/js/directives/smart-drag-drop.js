var smartDragData = {};
angular.module('smartsDirectives')
    // reference http://blog.parkji.co.uk/2013/08/11/native-drag-and-drop-in-angularjs.html
    .directive('smartDraggable', function () {
        return {
            restrict: 'AC',
            scope: {
                begin : '&dragBegin',
                end : '&dragEnd',
                class : '@dragClass',
                data : '=dragData'
            },
            link: function(scope, element, attrs) {
                attrs.$set('draggable', true);
                element[0].addEventListener('dragstart', function (event) {
                    event.dataTransfer.effectAllowed = 'move';
                    smartDragData = scope.data;
                    this.classList.add(scope.class);
                    scope.begin();
                    return false;
                }, false);
                element[0].addEventListener('dragend', function (event) {
                    this.classList.remove(scope.class);
                    scope.end();
                    return false;
                }, false);
            }
        }
    })
    .directive('smartDropzone', ['$rootScope', function ($rootScope) {
        return {
            restrict: 'A',
            scope: {
                isValid : '&isValid',
                onDrop : '&onDrop',
                validClass : '@validClass',
                invalidClass : '@invalidClass'
            },
            link: function(scope, element, attrs) {
                var nestDepth = 0;
                var validDrop = false;
                element[0].addEventListener('dragenter', function(event) {
                    if(nestDepth == 0) {
                        var response = scope.isValid({dropData:smartDragData});
                        if(response === true) {
                            validDrop = true;
                            event.preventDefault();
                            this.classList.add(scope.validClass);
                        } else {
                            validDrop = false;
                            this.classList.add(scope.invalidClass);
                            if(typeof response == 'string')
                                $rootScope.updateDragDropMessage(response);
                        }
                    }
                    nestDepth++;
                    return false;
                }, false);
                element[0].addEventListener('dragover', function(event) {
                    if(validDrop)
                        event.preventDefault();
                    return false;
                }, false);
                element[0].addEventListener('dragleave', function(event) {
                    nestDepth--;
                    if(nestDepth == 0) {
                        this.classList.remove(scope.validClass);
                        this.classList.remove(scope.invalidClass);
                        $rootScope.updateDragDropMessage();
                    }
                    return false;
                }, false);
                element[0].addEventListener('drop', function(event) {
                    nestDepth = 0;
                    event.stopPropagation();
                    this.classList.remove(scope.validClass);
                    this.classList.remove(scope.invalidClass);
                    if(scope.isValid({dropData:smartDragData}) === true)
                        scope.onDrop({dropData:smartDragData});
                    return false;
                }, false);
            }
        }
    }])
;