var ENTER_KEY_CODE   = 13;
var ESCAPE_KEY_CODE  = 27;


var ARROWLEFT_KEY_CODE =  37;
var ARROWUP_KEY_CODE   =  38;
var ARROWRIGHT_KEY_CODE = 39;
var ARROWDOWN_KEY_CODE =  40;
var ARROWKEY_CODES = [ARROWLEFT_KEY_CODE, ARROWRIGHT_KEY_CODE, ARROWUP_KEY_CODE, ARROWDOWN_KEY_CODE] ;

var F2_KEY_CODE        = 113;
var F4_KEY_CODE        = 115;
var F8_KEY_CODE        = 119;
var F9_KEY_CODE        = 120;



var zoo = new Array(ARROWLEFT_KEY_CODE);
var boo = [3];

var LOG_TAG = 'log-event';  // single vs. double quotes seems to be immaterial

function initActions() {
    focusCursor();
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

function focusCursor() {
    dataTableWidget.unselectAllRows();
    dataTableWidget.selectRow(0);
    $('#CAR-form\\:CAR-data-table\\:0\\:modelrow').focus(); // we can't track the selection with the focus so let's
                                                            // better not have any PrimeFaces focus at all - use javascript focus
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

selectRow = function(i) {
    dataTableWidget.unselectAllRows(); 
    dataTableWidget.selectRow(i);
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
    var ithChild = nthChild(father, i);
    return $(ithChild).equals(child);
}

isLastButOneChild = function(father, child) {
    var lastButOneChildVar = lastButOneChild(father);
    return $(lastButOneChildVar).equals(child);
}

lastButOneChild = function(father) {
    var numOfChildren = $(father).children().length;
    return nthChild(father, numOfChildren - 2);
}

nthChild = function(father, i) {
    var children = $(father).children();
    console.log(children.length+" children returned");
    return children[i];
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
    var inputValue = e.getAttribute("value"); // line lkj29378dskj
    var caretEnd = paranoidCaretEnd(e);
    if      (caretEnd >inputValue.length) throw "panic";
    else if (caretEnd==inputValue.length) return true;
    else                             return false;
}

function caretAtTheBeginning(e) {
    var caretEnd = paranoidCaretEnd(e);
    if      (caretEnd <0                ) throw "panic";
    else if (caretEnd==0                ) return true;
    else                                  return false;
}

focus = function(elem) {
    $(elem).focus();
    $(elem).caret({start:0,end:0});
}

focusEnd = function(elem) {
    $(elem).focus();
    var inputValue = elem.attr("value"); // elem is a jQuery object, not an HTML object as in line lkj29378dskj
    var endOfInput = inputValue.length;
    $(elem).caret( {start:endOfInput, end:endOfInput} );
}


isInArray = function (val,arr) {
    return arr.indexOf(val)>=0;
}

var caretAtTheEndFlag       = false;
var caretAtTheBeginningFlag = false;

function navigateWithArrows(event, rowIndex) { // rowIndex is not really used
    if ( !isInArray(event.keyCode, ARROWKEY_CODES) )
        return true;
    else {
        var element = event.target || event.srcElement; // srcElement in Internet Explorer, target in other browsers
        var father = $(element).closest('tbody');
        var rowInQuestion = $(element).closest('tr');
        var i = $(element).closest('td').index();
        var gotoRow = null;
        if (event.keyCode==ARROWRIGHT_KEY_CODE) {
            caretAtTheBeginningFlag = false;
            if (caretAtTheEndFlag) {
                var focusTarget = null;
                if (isLastButOneChild(rowInQuestion, $(element).closest('td'))) {
                    focusTarget = $(rowInQuestion).children(':first').find('input');
                } else {
                    focusTarget = $(element).closest('td').next('td').find('input');
                }
                focus(focusTarget);
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
            if (caretAtTheBeginningFlag) {
                console.log('at beginning and caretAtTheBeginningFlag set to true');
                if (isFirstChild(rowInQuestion, $(element).closest('td'))) {
                    var focusTarget = $(lastButOneChild(rowInQuestion)).find('input');
                    focusEnd(focusTarget);
                } else {
                    var focusTarget = $(element).closest('td').prev('td').find('input');
                    focusEnd(focusTarget);
                }
                caretAtTheBeginningFlag = false;
                return false;
            }
            else if (caretAtTheBeginning(element)) {
                console.log('caretAtBeginning now set to true');
                caretAtTheBeginningFlag = true;
                return true;
            }
            else return true;
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

