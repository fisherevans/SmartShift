'use strict';

function updateNavigation(elementDefs) { // TODO - charlie, i don't know where to put this
    var navElements = [];
    for(var id in elementDefs) {
        var elementDef = elementDefs[id];
        if(elementDef.type == "arrow") {
            var navElement =  $("<div></div>");
            navElement.addClass("subArrow icon-arrow-right");
            navElements.push(navElement);
        } else if(elementDef.type == "text") {
            var navElement =  $("<div></div>");
            navElement.addClass("textElement");
            navElement.text(elementDef.text);
            navElements.push(navElement);
        } else if(elementDef.type == "link") {
            var navElement =  $("<a></a>");
            navElement.addClass("linkElement fadeColors");
            navElement.text(elementDef.text);
            navElement.attr("href", "#/" + elementDef.href);
            navElements.push(navElement);
        }
    }
    $("#dynamicAppNavigationTree").html("");
    for (var id = 0; id < navElements.length; id++) {
        if(navElements[id] !== undefined)
            navElements[id].first().appendTo("#dynamicAppNavigationTree");
    }
}

var app = angular.module('smartsApp', [
    'ngRoute',
    'ui.bootstrap',
    'smartsServices',
    'ngCookies'
]);