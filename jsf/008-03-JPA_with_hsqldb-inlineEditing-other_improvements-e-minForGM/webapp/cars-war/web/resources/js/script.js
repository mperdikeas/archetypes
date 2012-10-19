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

fullIdOfEnclosingDataTable = function (elem) {
    var closestDataTable = elem.closest('.ui-datatable');
    return closestDataTable.attr('id');
}

getWidgetVar = function (dataTableId) {
    var lastIdComponent =  dataTableId.split(/[:]+/).pop();
    return window[lastIdComponent+'WdgtVar']; // this convention has to be observed in the xhtml code !
                                              // I.e. the name of the widgetVar is the p:datatable id suffixed with suffix shown above
}

var previousMasterFocus = null;

function initActions() {

    $('.MasterTable').find('input').focus(function() {
        var focusTarget = $(this).closest('tr');
        if (!focusTarget.is(previousMasterFocus)) {
            console.log('previous master focus changed');
            previousMasterFocus = focusTarget;
            selectRowJQuery(focusTarget);
            $('#FormId\\:UpdateDetail').click(); 
        }
    }
    );


    $('#FormId\\:RowNext').hide();
    $('#FormId\\:RowPrev').hide();
    $('#FormId\\:UpdateDetail').hide();
    $('html').keyup(functionKeysUp);

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

hookOnDetailTable = function() {
    $('.DetailTable').find('input').focus(function() {  
        console.log('detail handler');
        var focusTarget = $(this).closest('tr');
        selectRowJQuery(focusTarget);
    }
    );
}


function focusCursor() {
    dataTableMasterWdgtVar.unselectAllRows(); // convention: the Prime Faces id of the master table should be dataTableMaster,
                                              // and accordingly (see other conventions) the widget var's name should be dataTableMasterWdgtVar
    dataTableMasterWdgtVar.selectRow(0);
    var dataTable = $('#FormId\\:dataTableMaster');
    // var dataTable = $('.MasterTable');
    dataTable.find('input').get(0).focus();
    // $('#FormId\\:dataTableMaster\\:0\\:modelrow').focus(); // we can't track the selection with the focus so let's
                                                            // better not have any PrimeFaces focus at all - use javascript focus
}


function focusToNextInput(event, element) {
    if (event.keyCode==ENTER_KEY_CODE){
        $(":input:eq("+($(":input").index(element)+1)+")").focus();
        return false;
    } else
        return true;
}



selectRowJQuery = function (el) {
    var dataTableFullId = fullIdOfEnclosingDataTable(el);
    console.log('data table full id is: '+dataTableFullId+'. Widget var follows:');
    var widgetVar = getWidgetVar(dataTableFullId);
    console.log(widgetVar);
    widgetVar.unselectAllRows();
    widgetVar.selectRow(el);
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
                caretAtTheEndFlag = true;
                return true;
            }
            else return true;
        }
        if (event.keyCode==ARROWLEFT_KEY_CODE) {
            caretAtTheEndFlag = false;
            if (caretAtTheBeginningFlag) {
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
//      selectRow(gotoRow);
        return false;
    }
}


functionKeysUp = function(event) {
           if (event.keyCode==F9_KEY_CODE)     { $("#FormId\\:BtnAdd")    .click();
    } else if (event.keyCode==F2_KEY_CODE)     { $("#FormId\\:BtnRestore").click();
    } else if (event.keyCode==F4_KEY_CODE)     { $("#FormId\\:BtnCommit") .click();
    } else if (event.keyCode==F8_KEY_CODE)     { $("#FormId\\:BtnDel")    .click();
    } else if (event.keyCode==ESCAPE_KEY_CODE) { $('#newItem\\:cancelBtn')  .click();
    } else return true;
    event.stopPropagation();
    return false;
};