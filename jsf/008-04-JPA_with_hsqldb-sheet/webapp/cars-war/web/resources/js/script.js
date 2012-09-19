var ESCAPE_KEY_CODE  = 27;

var F2_KEY_CODE      = 113;
var F4_KEY_CODE      = 115;
var F8_KEY_CODE      = 119;
var F9_KEY_CODE      = 120;



function initActions() { // focusing does not yet work as I need to find a way to
                        // only focus the very first time the page is loaded and
                        // rely on arrow key navigation.
    $('html').keyup(processKeyUp);
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

