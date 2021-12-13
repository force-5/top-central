function showDialog(divId, isPersistent) {
    jQuery('#' + divId).modal({
        overlay:100,
        escClose:false,
        position:["20%","40%"],
        overlayCss: {backgroundColor:"#3C3638"},
        persist:isPersistent,
        minHeight:50,
        minWidth:50
    });
}

function showDialogWithPosition(divId, isPersistent, top, left) {
    jQuery('#' + divId).modal({
        overlay:100,
        escClose:false,
        position:[top,left],
        overlayCss: {backgroundColor:"#3C3638"},
        persist:isPersistent,
        minHeight:50,
        minWidth:50
    });
}


function editEmployeeCertification(responseObj) {
    var editPageValue = responseObj.responseText;
    jQuery('#editEmployeeCertificationDiv').empty().html(editPageValue);
    showDialog('editEmployeeCertificationDiv', true);
}

function isDateNotEmpty(inputControl) {
    var obj = jQuery("#" + inputControl);
    if (obj.val() == "" || obj.val() == null) {
        jQuery("#" + inputControl + "_value").css('border', '1px solid #f00');
        return false
    }
    else {
        jQuery("#" + inputControl + "_value").css('border', '1px solid #000');
        return true
    }
}
