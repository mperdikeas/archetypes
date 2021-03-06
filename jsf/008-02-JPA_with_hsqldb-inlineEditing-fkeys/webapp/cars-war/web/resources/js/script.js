var ENTER_KEY_CODE   = 13;
var ARROWUP_KEY_UP   = 38;
var ARROWDOWN_KEY_UP = 40;
var ESCAPE_KEY_CODE  = 27;

var F2_KEY_CODE      = 113;
var F3_KEY_CODE      = 114;
var F4_KEY_CODE      = 115;
var F8_KEY_CODE      = 119;


function initActions() { // focusing does not yet work as I need to find a way to
                        // only focus the very first time the page is loaded and
                        // rely on arrow key navigation.
    $('#CAR-form\\:CAR-data-table\\:0\\:modelrow').focus();
    $('#CAR-form\\:RowNext').hide();
    $('#CAR-form\\:RowPrev').hide();
    $('html').keyup(processKeyUp);
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

function cancel(event) {
    if (event.keyCode==ESCAPE_KEY_CODE) {
        $('#newItem\\:cancelBtn').click();
    }
    return false;
}

function hitEnter(event) {
    if(event.keyCode==ENTER_KEY_CODE){
        $("#newItem\\:enter").click();
        return false;
    } else
        return true; // I am not quite sure what is the import of these return statements; I've experimented but didn't arrive at any results.
}



processKeyUp = function(event) {
    if (event.keyCode==F3_KEY_CODE)             { $("#CAR-form\\:BtnAdd").click();
    } else if (event.keyCode==F2_KEY_CODE)      { $("#CAR-form\\:BtnRestore").click();
    } else if (event.keyCode==F4_KEY_CODE)      { $("#CAR-form\\:BtnCommit").click();
    } else if (event.keyCode==F8_KEY_CODE)      { $("#CAR-form\\:BtnDel").click();
    } else if (event.keyCode==ARROWUP_KEY_UP)   { $("#CAR-form\\:RowPrev").click();
    } else if (event.keyCode==ARROWDOWN_KEY_UP) { $("#CAR-form\\:RowNext").click();
    } else return true;
    return false;
};

