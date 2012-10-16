var ENTER_KEY_CODE = 13;
var UP_ARROW_KEY_CODE = 38;
var DOWN_ARROW_KEY_CODE = 40;

function focusToNextInput(event) {
    
    var element = event.target || event.srcElement;
    
    if(event.keyCode==ENTER_KEY_CODE){
        $("input:eq("+($("input").index(element)+1)+")").focus();
        return false;
    }
    else {
        return true;
        //return true;
    }
    
}

function navigateWithArrows(event, rowIndex, firstRowIndex) {
    
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

//TO DO FOCUS TO NEW ROW
function createNewRow(event) {
    if(event.keyCode==ENTER_KEY_CODE){
        $(".insertRowBtn").click();
        return false;
    }
    return true;
}

function focusOnNewRow() {
    if(typeof $(".ui-state-highlight input")[0] !== 'undefined') {
        $(".ui-state-highlight input")[0].focus();
        return false;
    }
    else {
    //navigate to next page
    //alert('navigate to new page');
    }
    return true;
}

function hasChanges(transactionDirty) {
    if (transactionDirty =='true') {
        pcdlg.show();
        return false;
    }
    return true;
}

function selectRow(rowIndex, firstRowIndex) {
    datatableVar.unselectAllRows(); 
    datatableVar.selectRow(rowIndex - firstRowIndex);
    return true;
}