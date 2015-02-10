var userAuthRequirement = {
  "name":"Basic Authentication (User)",
  "description":"Basic Authentication with the username and password associated with the user",
  "example":"HTTP Header - 'Authentication: Basic ' + base64(username + ':' + password)"
};

var sessionAuthRequirement = {
  "name":"Basic Authentication (Session)",
  "description":"Basic Authentication with the username associated with the user, and the session key associated with the employee",
  "example":"HTTP Header - 'Authentication: Basic ' + base64(username + ':' + sessionKey)"
};

var badAuthResult = {
  "result":"The Basic Authentication credentials were invalid"
};

var apiDef = {
  "paths": {
    "/accounts/user/full": {
      "GET": {
        "shortDescription":"Gets all Account data for a user",
        "requires": [userAuthRequirement],
        "responses": {
          401: badAuthResult,
          200: {
            "result":"The full user object",
            "data": {
              "user": {
                "id":1,
                "username":"username",
                "email":"email@address.com"
              },
              "businesses":{
                1: {
                  "id":1,
                  "name":"Business Name",
                  "server":"some.domain",
                  "employeeID":1,
                  "image":{
                    "id":1,
                    "alt":"Alternate text"
                  },
                  "address":{
                    "street1": "101 Loop Lane",
                    "street2": "Suite 8",
                    "city": "Somewhere",
                    "subDivision": "In Cali",
                    "country": "USA",
                    "postalCode": "12345",
                    "phoneNumber": "18885551234"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/accounts/user/self": {
      "GET": {
        "shortDescription":"Gets basic user info",
        "requires": [userAuthRequirement],
        "responses": {
          401: badAuthResult,
          200: {
            "result":"The basic user object",
            "data": {
                "id":1,
                "username":"username",
                "email":"email@address.com"
            }
          }
        }
      }
    },
    "/accounts/user/session": {
      "PUT": {
        "shortDescription":"Creates a new session token",
        "longDescription":"The method will create a sessions key for use as a temporary password on a business server for a given employee business. The key is unique to the issuing user and will timeout after the allotted timeout period if it is not used.",
        "requires": [userAuthRequirement],
        "takes":{
          "businessID":1,
          "employeeID":1
        },
        "responses": {
          401: badAuthResult,
          201: {
            "result":"A session was created",
            "data": {
              "sessionID":"Some session id",
              "server":"someserver.domain",
              "timeout":1
            }
          }
        }
      },
      "DELETE": {
        "shortDescription":"Destroys and existing session token",
        "requires": [userAuthRequirement],
        "takes":{
          "businessID":1,
          "employeeID":1,
          "sessionID":"The session id to destroy"
        },
        "responses": {
          401: badAuthResult,
          202: {
            "result":"A session was created"
          }
        }
      }
    },
    "/accounts/zzz/test": {
      "GET": {
        "notImplemented":true,
        "shortDescription":"Gets basic user info",
        "requires": [userAuthRequirement],
        "responses": {
          401: badAuthResult,
          200: {
            "result":"The basic user object",
            "data": {
                "id":1,
                "username":"username",
                "email":"email@address.com"
            }
          }
        }
      }
    }
  }
};

Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};

function APIDocController($scope) {
  $scope.test = function() { alert('t'); };
  $scope.api = apiDef;
  $scope.currentPath = '';
  $scope.currentMethods = {};
  $scope.currentMethod = '';
  $scope.updateCurrent = function(path, methods, method) {
    var input = document.getElementById('filterInput');
    var scope = angular.element(input).scope();
    scope.currentPath = path;
    scope.currentMethods = methods;
    scope.currentMethod = method;
  };
  $scope.filteredPaths = apiDef.paths;
  $scope.filterListener = function () {
    var input = document.getElementById('filterInput');
    var filter = input.value.toLowerCase().replace(/\W+/g, " ");
    var scope = angular.element(input).scope();
    if(filter.length == 0) {
      scope.filteredPaths = scope.api.paths;
      return;
    }
    scope.filteredPaths = {};
    angular.forEach(scope.api.paths, function(value, key) {
      if(key.toLowerCase().replace(/\W+/g, " ").search(filter) >= 0) {
        scope.filteredPaths[key] = value;
        return;
      }
      var methods = {};
      angular.forEach(value, function(mValue, mKey) {
        if(mKey.toLowerCase().search(filter) >= 0
          || mValue.shortDescription.toLowerCase().replace(/\W+/g, " ").search(filter) >= 0) {
          methods[mKey] = mValue;
        }
      });
      if(Object.size(methods) > 0)
        scope.filteredPaths[key] = methods;
    });
    scope.$apply();
  };
  $scope.prettyJson = function(obj) {
    return JSON.stringify(obj, undefined, 2);
  }
  $scope.objSize = function(obj) {
    return Object.size(obj);
  };
}