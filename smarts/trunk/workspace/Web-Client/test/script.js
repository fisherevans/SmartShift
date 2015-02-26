function print(text) {
  console.log(text);
  $("#log").append(text);
  location.hash = "#bottom";
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

function api(method, action, data, username, password, callback) {
  print("<div class='rule'></div>");
  var json = JSON.stringify(data);
  action = conf.server + action;
  printlnb(method + " " + action);
  var response = null;
  var failed = false;
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
      response = sucResponse;
    },
    error : function(errResponse) {
      response = errResponse.responseText;
      failed = true;
    },
    complete : function() {
      println("User/Pass: " + username + "/" + password);
      printlnb("Sent:");
      printobj(data);
      printlnb("Got:");
      printobj(response);
      var text = (failed ? "FAILED" : "SUCCESS") + " (" + ((new Date().getTime()) - start) + "ms)";
      var color = (failed ? "red" : "green");
      printlnb("<span style='color:" + color + ";font-size:18px;'>" + text + "</span>");
      if(!failed)
        callback(response);
      else
        throw "Failed a call!";
    }
  });
  return response;
}

var conf = {};

function startTest() {
  conf.server = $("#server").val();
  conf.username = $("#username").val();
  conf.password = $("#password").val();
  conf.business = $("#business").val();
  conf.employee = $("#employee").val();
  conf.groups = $("#groups").val();
  conf.roles = $("#roles").val();
  $("#setup").hide();
  println("Staring tests");
  try {
    var user = "fisher";
    var pass = "password";
    var session, userSelf, userFull, sessionPut1, sessionPut2, sessionDel;
    var employee, employeeBig, group, role;
    
    var log = $("#log");
    $(log)
    .queue(function() {
      api("GET", "/accounts/user/self", {}, conf.username, conf.password, function(response) {
        userSelf = response;
        $(log).dequeue();
      });
    })
    .queue(function() {
      api("GET", "/accounts/user/full", {}, conf.username, conf.password, function(response) {
        userFull = response;
        $(log).dequeue();
      });
    })
    .queue(function() {
      api("PUT", "/accounts/user/session", {
        "businessID" : conf.business,
        "employeeID" : conf.employee
      }, conf.username, conf.password, function(response) {
        sessionPut1 = response;
        session = sessionPut1.data.sessionKey;
        $(log).dequeue();
      });
    })
    .queue(function() {
      api("PUT", "/accounts/user/session", {
        "businessID" : 1,
        "employeeID" : 2
      }, user, pass, function(response) {
        sessionPut2 = response;
        $(log).dequeue();
      });
    })
    .queue(function() {
      api("DELETE", "/accounts/user/session", {
        "businessID" : 1,
        "employeeID" : 2,
        "sessionKey" : sessionPut2['data']['sessionKey']
      }, user, pass, function(response) {
        sessionDel = response;
        $(log).dequeue();
      });
    })
    .queue(function() {
      api("GET", "/business/employee/" + conf.employee, {}, conf.username, session, function(response) {
        employee = response;
        $(log).dequeue();
      });
    })
    .queue(function() {
      api("GET", "/business/employee/full/" + conf.employee, {}, conf.username, session, function(response) {
        employeeFull = response;
        $(log).dequeue();
      });
    })
    .queue(function() {
      api("GET", "/business/group/" + conf.groups, {}, conf.username, session, function(response) {
        group = response;
        $(log).dequeue();
      });
    })
    .queue(function() {
      api("GET", "/business/role/" + conf.roles, {}, conf.username, session, function(response) {
        role = response;
        $(log).dequeue();
      });
    })
    .queue(function() {
      print("<div class='rule'></div>");
      println("Test finished!");
    })
    ;
  } catch (e) {
    print("<div class='rule'></div>");
    println(e);
    die();
  }
}
