function setLogger(id) { 
  var level = $("#level-" + id).val();
  var logger = $("#logger-" + id).val();
    $.ajax({
      type: "POST",
      async: false,
      url: document.URL,
      contentType: 'text/plain',
      data: logger + "|" + level,
      success: function (sucResponse) {
        console.log("Received: ");
        console.log(sucResponse);
      },
      error: function (errResponse) {
        console.log("Received: ");
        console.log(errResponse.responseText);
      }
    });
}