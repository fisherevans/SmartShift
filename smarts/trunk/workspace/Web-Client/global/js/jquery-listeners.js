$(document).ready(function() {;
    $("body").on("change", ".smartCheckbox", function(event) {
        var parent = $(event.target).closest(".smartCheckboxLabel");
        if($(event.target).prop("checked")) {
            parent.addClass("checked");
            parent.find(".smartCheckboxCheck").removeClass("icon-not-checked");
            parent.find(".smartCheckboxCheck").addClass("icon-checked");
        } else {
            parent.removeClass("checked");
            parent.find(".smartCheckboxCheck").addClass("icon-not-checked");
            parent.find(".smartCheckboxCheck").removeClass("icon-checked");
        }
    });
});