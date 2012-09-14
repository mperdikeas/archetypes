var ENTER_KEY_CODE = 13;
var F3_KEY_UP      = 114;

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
        return true;
}

processKeyEvent = function (eventType, event) {
    if (event.keyCode==F3_KEY_UP) {
        $("#CAR-form\\:BtnAdd").click();
        return false;
    } else
        return true;
}

processKeyUp = function(event) {
       return processKeyEvent("onkeyup", event);
};

