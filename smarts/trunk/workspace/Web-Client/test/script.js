function print(text) {
  console.log(text);
  $("#log").append(text);
}

function println(text) {
  print(text + "\n");
}

function printlnb(text) {
  println("<b>" + text + "</b>");
}

function printobj(obj) {
  console.log(obj);
  $("#log").append(JSON.stringify(obj, null, 2) + "\n");
}

function doNothing(response) {
}

function die(response) {
  println('Test Failed!');
}

function api(method, action, data, username, password, sucFunction, errFunction) {
  print("<div class='rule'></div>");
  var json = JSON.stringify(data);
  action = "http://lando.smartshift.info:6380" + action;
  printlnb(method + " " + action);
  println("User/Pass: " + username + "/" + password);
  printobj(data);
  var response = null;
  var start = new Date().getTime();
  $.ajax({
    type : method,
    async : true,
    url : action,
    contentType : 'application/json',
    data : json,
    beforeSend : function(xhr) {
      xhr.setRequestHeader("Authorization", "Basic " + btoa(username + ":" + password));
    },
    success : function(sucResponse) {
      printlnb("<span style='color:green'>SUCCESS</span>");
      printobj(sucResponse);
      printlnb("Took: " + ((new Date().getTime()) - start) + "ms");
      sucFunction(sucResponse);
    },
    error : function(errResponse) {
      printlnb("<span style='color:red;font-size:18px;'>ERROR</span>");
      printobj(errResponse.responseText);
      printlnb("Took: " + ((new Date().getTime()) - start) + "ms");
      errFunction(jQuery.parseJSON(errResponse.responseText));
    }
  });
  return response;
}

function startTest() {
  $("#start").hide();
  println("Staring tests");
  try {
    var user = "fisher";
    var pass = "password";
    var session, userSelf, userFull, sessionPut1, sessionPut2, sessionDel;
    var employee, employeeBig, group, role;
    
    var log = $("#log");
    $(log)
    .queue(function() {
      api("GET", "/accounts/user/self", {}, user, pass, function(response) {
        userSelf = response;
        $(log).dequeue();
      }, die);
    })
    .queue(function() {
      api("GET", "/accounts/user/full", {}, user, pass, function(response) {
        userFull = response;
        $(log).dequeue();
      }, die);
    })
    .queue(function() {
      api("PUT", "/accounts/user/session", {
        "businessID" : 1,
        "employeeID" : 2
      }, user, pass, function(response) {
        sessionPut1 = response;
        session = sessionPut1.data.sessionKey;
        $(log).dequeue();
      }, die);
    })
//    .queue(function() {
//      api("PUT", "/accounts/user/session", {
//        "businessID" : 1,
//        "employeeID" : 2
//      }, user, pass, function(response) {
//        sessionPut2 = response;
//        $(log).dequeue();
//      }, die);
//    })
//    .queue(function() {
//      api("DELETE", "/accounts/user/session", {
//        "businessID" : 1,
//        "employeeID" : 2,
//        "sessionKey" : sessionPut2['data']['sessionKey']
//      }, user, pass, function(response) {
//        sessionDel = response;
//        $(log).dequeue();
//      }, die);
//    })
    .queue(function() {
      api("GET", "/business/employee/2", {}, user, session, function(response) {
        employee = response;
        $(log).dequeue();
      }, die);
    })
    .queue(function() {
      api("GET", "/business/employee/full/2", {}, user, session, function(response) {
        employeeFull = response;
        $(log).dequeue();
      }, die);
    })
    .queue(function() {
      api("GET", "/business/group/1-2", {}, user, session, function(response) {
        group = response;
        $(log).dequeue();
      }, die);
    })
    .queue(function() {
      api("GET", "/business/role/3-5", {}, user, session, function(response) {
        role = response;
        $(log).dequeue();
      }, die);
    })
    ;
  } catch (e) {
    println(e);
    die();
  }
}
