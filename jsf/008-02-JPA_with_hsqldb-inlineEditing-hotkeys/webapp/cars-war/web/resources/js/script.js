var ENTER_KEY_CODE   = 13;
var F3_KEY_UP        = 114;
/*var ARROWUP_KEY_UP   = 38;
  var ARROWDOWN_KEY_UP = 40;*/

var not_yet_focused = true;

function focusInput() { // focusing does not yet work as I need to find a way to
                        // only focus the very first time the page is loaded and
                        // rely on arrow key navigation.
    $('#CAR-form\\:CAR-data-table\\:0\\:modelrow').focus();
}

function focusToNextInput(event, element) {
    if(event.keyCode==ENTER_KEY_CODE){
        $(":input:eq("+($(":input").index(element)+1)+")").focus();
        return false;
    } else
        return true;
}

//TODO: FOCUS TO NEW ROW
function createNewRow(event) {
    if(event.keyCode==ENTER_KEY_CODE){
        $("#CAR-form\\:BtnAdd").click();
        return false;
    } else
        return true;
}



function hitEnter(event) {
    if(event.keyCode==ENTER_KEY_CODE){
        $("#newItem\\:enter").click();
        return false;
    } else
        return true; // I am not quite sure what is the import of these return statements; I've experimented but didn't arrive at any results.
}

processKeyEvent = function (eventType, event) {
    if (event.keyCode==F3_KEY_UP) {
        $("#CAR-form\\:BtnAdd").click();
        return false; 
    } else
/*    if (event.keyCode==ARROWUP_KEY_UP) {
        $("#CAR-form\\:RowPrev").click();
        return false; 
    } else
    if (event.keyCode==ARROWDOWN_KEY_UP) {
        $("#CAR-form\\:RowNext").click();
        return false; 
    } else */
        return true;
}

processKeyUp = function(event) {
       return processKeyEvent("onkeyup", event);
};

