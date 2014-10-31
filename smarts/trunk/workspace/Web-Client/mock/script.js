$(document).ready(function() {
  $(".navElement:not(.current)").on("click", function() {
    $(this).addClass("noScroll");
    $(".appWindow").addClass("hidden");
    $(".navElement").removeClass("current");
    $(this).delay(250).queue(function() {
      $(this).removeClass("noScroll");
      $(".appWindow").removeClass("hidden");
      $(this).addClass("current");
      $(this).dequeue();
    });
  });
});