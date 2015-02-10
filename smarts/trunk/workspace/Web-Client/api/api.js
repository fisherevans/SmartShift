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

var employeeOwnerOrManager = {
  "name":"Employee Ownership or Management",
  "description":"The access this method, you must be the employee in question, or you must be a manager to the employee."
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
    "/business/groupRoles": {
      "GET": {
        "notImplemented":true,
        "shortDescription":"Gets a listing of all groups, and what roles belong to it.",
        "requires": [sessionAuthRequirement],
        "responses": {
          401: badAuthResult,
          200: {
            "result":"The mapping of groups to roles",
            "data": {
              1: {
                "groupID":1,
                "parentGroupID":2,
                "groupName":"Some name",
                "roles": {
                  1: {
                    "roleID":1,
                    "roleName":"Some name"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/business/employee/{employeeID}": {
      "GET": {
        "notImplemented":true,
        "shortDescription":"Gets basic information about an employee",
        "requires": [sessionAuthRequirement],
        "responses": {
          401: badAuthResult,
          200: {
            "result":"The base employee object",
            "data": {
              "firstName":"Joe",
              "lastName":"Shmoe",
              "defaultGroupID":1
            }
          }
        }
      }
    },
    "/business/employee/full/{employeeID}": {
      "GET": {
        "notImplemented":true,
        "shortDescription":"Gets all information about an employee",
        "requires": [
          sessionAuthRequirement,
          employeeOwnerOrManager
        ],
        "responses": {
          401: badAuthResult,
          200: {
            "result":"The full employee object",
            "explanation":"The 'groupRoles' object contains a map of <GroupID -> Array<RoleID>>.",
            "data": {
              "employee": {
                "firstName":"Joe",
                "lastName":"Shmoe",
                "defaultGroupID":1
              },
              "groupRoles": {
                1:[1, 2],
                2:[2, 3]
              }
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
  $scope.updateCurrent = function(path, methods) {
    var input = document.getElementById('filterInput');
    var scope = angular.element(input).scope();
    scope.currentPath = path;
    scope.currentMethods = methods;
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
          || mValue.shortDescription.toLowerCase().replace(/\W+/g, " ").search(filter) >= 0
          || (mValue.longDescription != null && mValue.longDescription.toLowerCase().replace(/\W+/g, " ").search(filter) >= 0)) {
          methods[mKey] = mValue;
        }
      });
      if(Object.size(methods) > 0)
        scope.filteredPaths[key] = methods;
    });
    scope.$apply();
  };
  $scope.returnStructure = function(response) {
    var r = {};
    var contents = 0;
    if(response.data != undefined) {
      r['data'] = response.data;
      contents++;
    }
    if(response.message != undefined) {
      r['message'] = response.message;
      contents++;
    }
    if(contents == 0)
      return undefined;
    return r;
  }
  $scope.prettyJson = function(obj) {
    return JSON.stringify(obj, undefined, 2);
  }
  $scope.objSize = function(obj) {
    return Object.size(obj);
  };
}