var ENTER_KEY_CODE   = 13;
var ESCAPE_KEY_CODE  = 27;


var ARROWLEFT_KEY_CODE =  37;
var ARROWUP_KEY_CODE   =  38;
var ARROWRIGHT_KEY_CODE = 39;
var ARROWDOWN_KEY_CODE =  40;
var F2_KEY_CODE        = 113;
var F4_KEY_CODE        = 115;
var F8_KEY_CODE        = 119;
var F9_KEY_CODE        = 120;

var LOG_TAG = 'log-event';  // single vs. double quotes seems to be immaterial

function initActions() { // focusing does not yet work as I need to find a way to
                        // only focus the very first time the page is loaded and
                        // rely on arrow key navigation.
    $('#CAR-form\\:CAR-data-table\\:0\\:modelrow').focus(); // we can't track the selection with the focus so let's
                                                            // better not have any PrimeFaces focus at all - use javascript focus
    $('#CAR-form\\:RowNext').hide();
    $('#CAR-form\\:RowPrev').hide();
    $('html').keyup(processKeyUp);
    $('#clear-registry').click(clearEvents);

    $.fn.equals = function(compareTo) {
        if (!compareTo || !compareTo.length || this.length!=compareTo.length) {
            return false;
        }
        for (var i=0; i<this .length; i++) {
            if (this[i]!==compareTo[i]) {
                return false;
            }
        }
        return true;
    } 
}


clearEvents = function() {
        $('.'+LOG_TAG).remove();
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

logMessage = function(msg) {
    $(".log").append("<p class='"+LOG_TAG+"'>"+msg+"</p>");
}

selectRow = function( i ) {
    dataTableWidget.unselectAllRows(); 
    dataTableWidget.selectRow( i );
}

isLastChild = function(father, child) {
    var lastChild = $(father).children(':last');
    return lastChild.equals(child);
}

isFirstChild = function(father, child) {
    var firstChild = $(father).children(':first');
    return firstChild.equals(child);
}

isNthChild = function(father, child, i) {
    console.log('inside isNthChild('+i+')');
    var ithChild = $(father).children()[i];
    return $(ithChild).equals(child);
}

isLastButOneChild = function(father, child) {
    var numOfChildren = $(father).children().length;
    return isNthChild(father, child, numOfChildren - 2);
}

function getInputSelection(el) {
    var start = 0, end = 0, normalizedValue, range,
    textInputRange, len, endRange;

    if (typeof el.selectionStart == "number" && typeof el.selectionEnd == "number") {
        start = el.selectionStart;
        end = el.selectionEnd;
    } else {
        range = document.selection.createRange();

        if (range && range.parentElement() == el) {
            len = el.value.length;
            normalizedValue = el.value.replace(/\r\n/g, "\n");

            // Create a working TextRange that lives only in the input
            textInputRange = el.createTextRange();
            textInputRange.moveToBookmark(range.getBookmark());

            // Check if the start and end of the selection are at the very end
            // of the input, since moveStart/moveEnd doesn't return what we want
            // in those cases
            endRange = el.createTextRange();
            endRange.collapse(false);

            if (textInputRange.compareEndPoints("StartToEnd", endRange) > -1) {
                start = end = len;
            } else {
                start = -textInputRange.moveStart("character", -len);
                start += normalizedValue.slice(0, start).split("\n").length - 1;

                if (textInputRange.compareEndPoints("EndToEnd", endRange) > -1) {
                    end = len;
                } else {
                    end = -textInputRange.moveEnd("character", -len);
                    end += normalizedValue.slice(0, end).split("\n").length - 1;
                }
            }
        }
    }

    return {
        start: start,
        end: end
    };
}

function paranoidCaretEnd(e) {
    var caretEndAccordingToGetInputSelection = getInputSelection(e).end;
    var caretEndAccordingToJCaret            = $(e).caret().end;
    if (caretEndAccordingToJCaret != caretEndAccordingToGetInputSelection) throw "panic";
    return 2*caretEndAccordingToGetInputSelection-caretEndAccordingToJCaret; // if they are not the same return a value that's
                                                                             // neither the one nor the other
}

function caretAtTheEnd(e) {
    var inputValue = e.getAttribute("value");
    var caretEnd = paranoidCaretEnd(e);
    if (caretEnd >inputValue.length) throw "panic";
    if (caretEnd==inputValue.length) return true;
    else                             return false;
}

focus = function(elem) {
    $(elem).focus();
    $(elem).caret({start:0,end:0});
}


var caretAtTheEndFlag = false;

function navigateWithArrows(event, rowIndex) { // rowIndex is not really used
    if ((event.keyCode != ARROWDOWN_KEY_CODE) && (event.keyCode != ARROWUP_KEY_CODE) && (event.keyCode != ARROWRIGHT_KEY_CODE))
        return true;
    else {
        var element = event.target || event.srcElement; // srcElement in Internet Explorer, target in other browsers
 
        // console.log("length of input ("+inputValue+") at this element is: "+inputValue.length+", caret end at: "+getInputSelection(element).end + "according to jCaret: "+ ($(element).caret().end)+", again: "+paranoidCaretEnd(element));

        var father = $(element).closest('tbody');
        // logMessage("number of rows is: "+$(father).children().length);
        var rowInQuestion = $(element).closest('tr');
        var i = $(element).closest('td').index();
        var gotoRow = null;

        if (event.keyCode==ARROWRIGHT_KEY_CODE) {
            if (caretAtTheEndFlag) {
                // console.log('caretAtTheEndFlag is set and right arrow key pressed');
                // console.log('the row has: '+$(rowInQuestion).children().length+' children');
                if (isLastButOneChild(rowInQuestion, $(element).closest('td'))) {
                    var focusTarget = $(rowInQuestion).children(':first').find('input');
                    focus(focusTarget);
                } else {
                    var focusTarget = $(element).closest('td').next('td').find('input');
                    focus(focusTarget);
                }
                caretAtTheEndFlag = false;
                return false;
            }
            else if (caretAtTheEnd(element)) {
                console.log('caretAtTheEndFlag is now set');
                caretAtTheEndFlag = true;
                return true;
            }
            else return true;
        }
        if (event.keyCode==ARROWLEFT_KEY_CODE) {
            caretAtTheEndFlag = false;

        }
        else if(event.keyCode==ARROWDOWN_KEY_CODE) {
            caretAtTheEndFlag = false;
            if (isLastChild(father, rowInQuestion))
                gotoRow = $(father).children(':first');
            else
                gotoRow = $(rowInQuestion).next('tr');
        }
        else if (event.keyCode==ARROWUP_KEY_CODE) { 
            caretAtTheEndFlag = false;
            if (isFirstChild(father, rowInQuestion))
                gotoRow = $(father).children(':last');
            else
                gotoRow = $(rowInQuestion).prev('tr');
        }
        var focusTarget = $(gotoRow).find('input')[i];
        focus(focusTarget);
        selectRow(gotoRow);
        return false;
    }
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

