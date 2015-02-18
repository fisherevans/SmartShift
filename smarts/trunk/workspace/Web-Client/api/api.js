// Main controller for the API app
function APIDocController($scope) {
  // Scope variables
  $scope.api = apiDef;
  $scope.currentPath = '';
  $scope.currentMethods = {};
  $scope.filteredPaths = apiDef.paths;

  // Sets the current method to be viewed
  $scope.updateCurrent = function(path, methods) {
    var input = document.getElementById('filterInput');
    var scope = angular.element(input).scope();
    scope.currentPath = path;
    scope.currentMethods = methods;
  };

  // Filters the scope paths based on the user input
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

  // Returns the data response, strucutred with the proper data and msg values
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

  // Converts a JSON object to formatted text
  $scope.prettyJson = function(obj) { return JSON.stringify(obj, undefined, 2); };

  // Gets the size of an associative/json object
  $scope.objSize = function(obj) {
	var size = 0, key;
	for (key in obj) {
	    if (obj.hasOwnProperty(key)) size++;
	}
	return size;
  };
}