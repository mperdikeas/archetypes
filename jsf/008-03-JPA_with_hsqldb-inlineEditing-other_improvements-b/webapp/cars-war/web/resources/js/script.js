var ENTER_KEY_CODE   = 13;
var ARROWUP_KEY_UP   = 38;
var ARROWDOWN_KEY_UP = 40;
var ESCAPE_KEY_CODE  = 27;

var F2_KEY_CODE      = 113;
var F4_KEY_CODE      = 115;
var F8_KEY_CODE      = 119;
var F9_KEY_CODE      = 120;


function initActions() { // focusing does not yet work as I need to find a way to
                        // only focus the very first time the page is loaded and
                        // rely on arrow key navigation.
    $('#CAR-form\\:CAR-data-table\\:0\\:modelrow').focus(); // we can't track the selection with the focus so let's better not have any focus at all
    $('#CAR-form\\:RowNext').hide();
    $('#CAR-form\\:RowPrev').hide();
    $('html').keyup(processKeyUp);
    $('#clear-registry').click(clearEvents);
}


clearEvents = function() {
        $('.logEvent').remove();
        return false; // cancel default behaviour
    }


function focusToNextInput(event, element) {
    if (event.keyCode==ENTER_KEY_CODE){
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


function navigateWithArrows(event, rowIndex) {
    $(".log").append("<p class='logEvent'> inside navigateWithArrows (rowIndex="+rowIndex+")</p>");
    var element = event.target || event.srcElement;
    if(event.keyCode==DOWN_ARROW_KEY_CODE){
        $(element).closest('tr').next('tr').find('input')[$(element).closest('td').index()].focus();
        selectRow(rowIndex + 1, firstRowIndex);
        return false;
    }
    else if (event.keyCode==UP_ARROW_KEY_CODE){ 
        $(element).closest('tr').prev('tr').find('input')[$(element).closest('td').index()].focus();
        selectRow(rowIndex - 1, firstRowIndex);
        return false;
    }
    return true;
}


processKeyUp = function(event) {
           if (event.keyCode==F9_KEY_CODE)     { $("#CAR-form\\:BtnAdd")    .click();
    } else if (event.keyCode==F2_KEY_CODE)     { $("#CAR-form\\:BtnRestore").click();
    } else if (event.keyCode==F4_KEY_CODE)     { $("#CAR-form\\:BtnCommit") .click();
    } else if (event.keyCode==F8_KEY_CODE)     { $("#CAR-form\\:BtnDel")    .click();
    } else if (event.keyCode==ESCAPE_KEY_CODE) { $('#newItem\\:cancelBtn')  .click();
    } else return true;
    event.stopPropagation();
    return false;
};
