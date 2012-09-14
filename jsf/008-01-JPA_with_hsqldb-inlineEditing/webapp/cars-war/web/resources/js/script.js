var ENTER_KEY_CODE = 13;

function focusToNextInput(event, element) {
    if(event.keyCode==ENTER_KEY_CODE){
        $(":input:eq("+($(":input").index(element)+1)+")").focus();
        return false;
    }
    return true;
}

//TO DO FOCUS TO NEW ROW
function createNewRow(event) {
    if(event.keyCode==ENTER_KEY_CODE){
        $("$CAR-form:BtnAdd").click();
    return false;
    }
return true;
}

