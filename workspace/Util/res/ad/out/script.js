$(document).ready(function() {
  hljs.initHighlightingOnLoad();
  initLink();
  $(".path-header").on("click", function(event) {
    var body = $(this).parent().children(".path-body");
    var bodyContent = body.children(".path-body-content");
    if(body.css("max-height") == "0px") {
      var aName = $(this).children("a").attr("name");
      history.pushState(null, null, "#" + aName);
      setTimeout(navTo(aName),400);
      resizeContent(body, bodyContent);
    } else
      body.css("max-height", "0px");
  });
    
  $(window).resize(function() {
    console.log("resize");
    $(".path-header").each(function() {
      var body = $(this).parent().children(".path-body");
      var bodyContent = body.children(".path-body-content");
      if(body.css("max-height") != "0px")
        resizeContent(body, bodyContent);
    });
  });
});

function initLink() {
  var aName = window.location.hash.substring(1);
  var body = $('[name="' + aName + '"]').parent().parent().children(".path-body");
  var bodyContent = body.children(".path-body-content");
  resizeContent(body, bodyContent);
  setTimeout(navTo(aName),400);
}

function resizeContent(body, bodyContent) {
  body.css("max-height", bodyContent.outerHeight() + "px");
}

function navTo(aName) {
  var aTag = $("a[name='"+ aName +"']");
  if(typeof aTag.offset() != 'undefined') {
    $('html,body').animate({scrollTop: aTag.offset().top-10},'fast');
  }
}