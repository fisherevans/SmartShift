var server = "http://localhost:8080/Tomcat-API/";

$(document).ready(function() {
  $("#actionPre").text(server);
  $("#sendButton").on("click", function() {
    $("#sendButton").prop("disabled",true);
    var username = $("#username").val();
    var password = $("#password").val();
    var method = $("#method").val();
    var action = $("#action").val();
    var dataText = $("#data").val();
    callAPIAsync(username, password, method, action, dataText, displayResult, displayResult);
  });
});

function displayResult(request) {
  $("#response").text(request.responseText);
  $("#sendButton").prop("disabled",false);
}

function callAPIAsync(username, password, method, action, dataText, sucFunction, errFunction) {
  var basicAuth = "Basic " + btoa(username + ':' + password);
  var fullAction = server + action;
  var request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (request.readyState == 4) { // If complete
      if(request.status == 200) // If 200 OK
        sucFunction(request);
      else // Any other error
        errFunction(request);
   }
  }
  request.open(method, fullAction, true);
  request.setRequestHeader("Authorization", basicAuth); 
  request.send(dataText);
}