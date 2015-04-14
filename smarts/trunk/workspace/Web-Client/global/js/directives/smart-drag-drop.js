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
    .directive('smartDropzone', function () {
        return {
            restrict: 'A',
            scope: {
                isValid : '&isValid',
                onDrop : '&onDrop',
                validClass : '@validClass',
                invalidClass : '@invalidClass'
            },
            link: function(scope, element, attrs) {
                element[0].addEventListener('dragover', function(event) {
                    if(scope.isValid({dropData:smartDragData})) {
                        if (event.preventDefault)
                            event.preventDefault();
                        this.classList.add(scope.validClass);
                    } else
                        this.classList.add(scope.invalidClass);
                    return false;
                }, false);
                element[0].addEventListener('dragenter', function(event) {
                    if(scope.isValid({dropData:smartDragData})) {
                        if (event.preventDefault)
                            event.preventDefault();
                        this.classList.add(scope.validClass);
                    } else
                        this.classList.add(scope.invalidClass);
                    return false;
                }, false);
                element[0].addEventListener('dragleave', function(event) {
                    this.classList.remove(scope.validClass);
                    this.classList.remove(scope.invalidClass);
                    return false;
                }, false);
                element[0].addEventListener('drop', function(event) {
                    if (event.stopPropagation)
                        event.stopPropagation();
                    if(scope.isValid({dropData:smartDragData})) {
                        scope.onDrop({dropData:smartDragData});
                    }
                    this.classList.remove(scope.validClass);
                    this.classList.remove(scope.invalidClass);
                    return false;
                }, false);
            }
        }
    })
;