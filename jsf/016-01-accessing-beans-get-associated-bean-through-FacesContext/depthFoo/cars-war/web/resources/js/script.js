var ARROWLEFT_KEY_CODE =  37;
var ARROWUP_KEY_CODE   =  38;
var ARROWRIGHT_KEY_CODE = 39;
var ARROWDOWN_KEY_CODE =  40;
var ARROWKEY_CODES = [ARROWLEFT_KEY_CODE, ARROWRIGHT_KEY_CODE, ARROWUP_KEY_CODE, ARROWDOWN_KEY_CODE];
var DOCUMENTWIDE_ARROWKEY_CODES = [ARROWUP_KEY_CODE, ARROWDOWN_KEY_CODE];

var fullIdOfEnclosingDataTable = function (elem) {
    var closestDataTable = elem.closest('.ui-datatable');
    return closestDataTable.attr('id');
}

var getWidgetVar = function (dataTableId) {
    var lastIdComponent =  dataTableId.split(/[:]+/).pop();
    return window[lastIdComponent+'WdgtVar']; // this convention has to be observed in the xhtml code !
                                              // I.e. the name of the widgetVar is the p:datatable id suffixed with suffix shown above
}

var currentSelectedRow;
var currentMasterSelectedRow;

function initActions() {

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
    $('.KeyboardNavigableTable').find('input').keyup(navigateWithArrows); //line: 908lsdkjl2k3jlkf
    $(document).keyup   (documentNavigateWithArrows);
}

function initActionsAjaxPartial() {
    $('.KeyboardNavigableTable').find('input').focus(function() {
        var idOfEnclosingTable = fullIdOfEnclosingDataTable($(this));
        var focusTarget = $(this).closest('tr');
        updateRowSelection(focusTarget);
    });

    $('.KeyboardNavigableTable').find('input').keyup(navigateWithArrows); // I've verified that this redundancy with line: 908lsdkjl2k3jlkf is, sadly, unavoidable. 
}

function updateRowSelection(newSelectedRow) {
    if (currentSelectedRow==null || !currentSelectedRow.is(newSelectedRow)) {
        selectRowJQuery(newSelectedRow); 
    }
    currentSelectedRow = newSelectedRow;
    if (isRowOfTopTable(currentSelectedRow))
        currentMasterSelectedRow = currentSelectedRow;
}

function updateMasterRowSelection(newSelectedRow) {
    if (currentMasterSelectedRow==null || !currentMasterSelectedRow.is(newSelectedRow)) {
        selectRowJQuery(newSelectedRow); 
    }
    currentMasterSelectedRow = newSelectedRow;
}

function isRowOfTopTable(rowElem) {
    var fatherDataTableId = fullIdOfEnclosingDataTable(rowElem);
    var topDataTableId = topNavigableDataTable().attr('id');
    var retValue = (fatherDataTableId==topDataTableId);
    return retValue;
}



function topNavigableDataTable() {
    return $($('.KeyboardNavigableTable').get(0)); // convention: the 1st keyboard-navigable table gets the focus
}

var getWidgetOfTopDataTable = function () {
    var dataTableMaster = topNavigableDataTable();
    var id = dataTableMaster.attr('id');
    return getWidgetVar(id);
}

function focusCursor() {
    var dataTableMasterWdgtVar = getWidgetOfTopDataTable();
    dataTableMasterWdgtVar.unselectAllRows();
    dataTableMasterWdgtVar.selectRow(0);
    updateRowSelection(getFirstRowOfTopTable());
    topNavigableDataTable().find('input').get(0).focus(); // this only works in focusable master tables, fails quierly otherwise.
}

var getFirstRowOfTopTable = function () {
    var dataTableMasterTBody = $(topNavigableDataTable().find('tbody').get(0));
    return dataTableMasterTBody.children(':first');
}

selectRowJQuery = function (el) {
    var dataTableFullId = fullIdOfEnclosingDataTable(el);
    var widgetVar = getWidgetVar(dataTableFullId);
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


var elementInDocument = function(element) {
    while (element = element.parentNode) {
        if (element == document) {
            return true;
        }
    }
    return false;
}


var reIniitializeCurrentMasterSelectedRowIfNoLongerInDom = function() {
    if (!elementInDocument(currentMasterSelectedRow.get(0))) {
        var firstRowOfTopTable = getFirstRowOfTopTable();
        currentMasterSelectedRow = firstRowOfTopTable;
        getWidgetOfTopDataTable().selectRow(0);
    }
}

var documentNavigateWithArrows = function(event) {
    if (event.ctrlKey==false)
        return false;
    if ( !isInArray(event.keyCode, DOCUMENTWIDE_ARROWKEY_CODES) )
        return true;
    else {
        var father = $($($('.KeyboardNavigableTable').get(0)).find('tbody').get(0));
        reIniitializeCurrentMasterSelectedRowIfNoLongerInDom();
        if(event.keyCode==ARROWDOWN_KEY_CODE) {
            if (isLastChild(father, currentMasterSelectedRow)) {
                gotoRow = $(father).children(':first');
            }
            else {
                gotoRow = $(currentMasterSelectedRow).next('tr');
            }
        }
        else if (event.keyCode==ARROWUP_KEY_CODE) { 
            if (isFirstChild(father, currentMasterSelectedRow)) {
                gotoRow = $(father).children(':last');
            }
            else {
                gotoRow = $(currentMasterSelectedRow).prev('tr');
            }
        }
        updateMasterRowSelection(gotoRow);
        event.preventDefault();
        return false;
    }
}

function navigateWithArrows(event, rowIndex) { // rowIndex is not really used - maybe it's ok to remove it
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
        return false;
    }
}