function showPopup(divName, response) {
    jQuery('#' + divName).html(response.responseText);
    showModalDialog(divName)
}

function confirmDelete() {
    if (confirm("Remove location?", "Yes", "No")) {
        jQuery('#contractorLocationAction').attr('value', 'remove');
        return true;
    }
    return false;
}

function isWorkerRoleHistoryItemEmpty(inputControl, errorDiv) {
    var a = jQuery('#' + inputControl).val();
    if (jQuery.trim(a).length == 0) {
        jQuery('#' + errorDiv).show();
        jQuery('#' + inputControl).css('border', '1px solid red').focus();
        return true;
    } else {
        jQuery('#' + inputControl).css('border', '1px solid #000');
        return false;
    }
}
function isStatusChangeWorkerRoleHistoryItemEmpty(inputControl, errorDiv) {
    var a = jQuery('#' + inputControl).val();
    var isWorkerRoleHistoryItemValid = false;
    if (jQuery.trim(a).length == 0) {
        jQuery('#' + errorDiv).show();
        jQuery('#' + inputControl).css('border', '1px solid red').focus();
        isWorkerRoleHistoryItemValid = false;
    } else {
        jQuery('#boxborder.affidavitList').css('border', '1px solid black');
        jQuery('#' + inputControl).css('border', '1px solid black').focus();
        isWorkerRoleHistoryItemValid = true;
    }
    var isAttachmentValid = isValidStatusForm();
    return (isWorkerRoleHistoryItemValid && isAttachmentValid);
}


function isStatusChangeCommentEmpty(inputControl, errorDiv) {
    var a = jQuery('#' + inputControl).val();
    var isCommentValid = false;
    if (jQuery.trim(a).length == 0) {
        jQuery('#' + errorDiv).show();
        jQuery('#' + inputControl).css('border', '1px solid red').focus();
        isCommentValid = false;
    } else {
        jQuery('#boxborder.affidavitList').css('border', '1px solid black');
        jQuery('#' + inputControl).css('border', '1px solid black').focus();
        isCommentValid = true;
    }
    var isAttachmentValid = isValidStatusForm();
    return (isCommentValid && isAttachmentValid);
}

function isValidStatusForm() {
    if (!jQuery('#supportDocument').is(':visible')) {
        return true;
    }
    var a = jQuery('#boxborder.affidavitList li').size();
    if (a == 0) {
        jQuery('#boxborder.affidavitList').css('border', '1px solid red');
        return false;
    } else {
        jQuery('#boxborder.affidavitList').css('border', '1px solid black');
        return true;
    }
}

//div with id #editContractorCertificationDiv is in _missingCertifications.gsp template
function editWorkerCertification(responseObj) {
    var editPageValue = responseObj.responseText;
    jQuery('#editWorkerCertificationDiv').empty().html(editPageValue);
    jQuery.modal.close();
    showModalDialog('editWorkerCertificationDiv', true);
}
function showEditCertificationPopup() {
    jQuery.modal.close();
    showModalDialog('editWorkerCertificationDiv', true);
}

function showChangeStatusDialog() {
    if (jQuery('td>input:checkbox:enabled:checked').size() < 1) {
        alert('No location selected.');
        return false;
    }
    jQuery('#change-status-error3').hide();
    jQuery('#accessJustification3').val('');
    jQuery('#accessJustification3').css('border', '1px solid #7f7f7f').focus();
    showModalDialog('change-status-multiple', true);
    jQuery('input[name=newAccessStatusForMultiple]').eq(3).attr('checked', true);
    return true;
}

function changeStatusMultipleSetup(inputControl, errorDiv) {
    if (isWorkerRoleHistoryItemEmpty(inputControl, errorDiv)) {
        return true;
    }
    jQuery('#contractorLocationHiddenFields').html("");
    jQuery('td>input:checkbox:enabled:checked').each(function () {
        if (jQuery(this).val()) {
            jQuery('#contractorLocationHiddenFields').
                append("<input type='hidden' name='checkecdContractorLocations' value='" + jQuery(this).val() + "'/>");
        }
    });
    return false;
}


function addEntitlementRoleToworker(workerId, entitlementRoleId, entitlementRoleName) {
    jQuery('[name=workerId]').val(workerId);
    jQuery('#entitlementRoleId').val(entitlementRoleId);
    jQuery('#head-add_contractor').text("Add EntitlementRole (" + entitlementRoleName + ")");
    showAccessJustificationWorkerRoleHistoryItem();
    jQuery('#container_id').show();
    jQuery('#entitlementRolesTree').hide();
}

function hideAccessJustificationWorkerRoleHistoryItem() {
    jQuery('#container_id').show();
    jQuery('#entitlementRolesTree').show();
    jQuery('#workerRoleHistoryItemArea').hide();
    jQuery('#close-button').show();
    jQuery('#head-add_contractor').text('Add Entitlement Role');
}
function showAccessJustificationWorkerRoleHistoryItem() {
    jQuery('#change-status-error2').hide();
    jQuery('#accessJustification2').val('')
        .css('border', '1px solid #7f7f7f')
        .focus();

    jQuery('#container_id').hide();
    jQuery('#close-button').hide();
    jQuery('#workerRoleHistoryItemArea').show();
}

function postLocationAdded(e, tickMarkImagePath) {
    if (e.startsWith('failure')) {
        alert('This location can not be added.');
    } else {
        var alink = jQuery.trim(e);
        var y = jQuery("a[rel='" + alink + "']");
        jQuery(y).parents('span').html('&nbsp;&nbsp;<img src="' + tickMarkImagePath + '"  height="12" alt="Tick Mark"/>');
    }
    hideAccessJustificationWorkerRoleHistoryItem();
}

function changeWorkerPerimetetStatusMultipleSetup(inputControl, errorDiv) {
    if (isWorkerRoleHistoryItemEmpty(inputControl, errorDiv)) {
        return true;
    }
    jQuery('#workerEntitlementRoleHiddenFields').html("");
    jQuery('td>input:checkbox:enabled:checked').each(function () {
        if (jQuery(this).val()) {
            jQuery('#workerEntitlementRoleHiddenFields').
                append("<input type='hidden' name='checkedWorkerEntitlementRoles' value='" + jQuery(this).val() + "'/>");
        }
    });
    return false;
}

function updateThisLocation(locationId, locationTitle) {
    var y = jQuery("a[rel='" + locationId + "']");
    jQuery(y).parents('span').html('&nbsp;&nbsp;<img src="../images/tickmark.jpg" height="12" alt="Tick Mark"/>');
    jQuery("<li class='li-input1'><input type='hidden' name='selectedLocations' value='"
        + locationId + "'/>" + locationTitle + "<a href='#' class='cross-link' onclick='jQuery(this).parent(\"li\").remove();'>&nbsp;</a></li>").
        appendTo('#selectedLocationsList');
}

function populatePopup(ajaxURL) {
    var buttonElement = jQuery("#requestSubmitMainButton");
    var workerNumber = jQuery.trim(jQuery('#workerNumber').val());
    var employeeSlid = jQuery.trim(jQuery('#employeeSlid').val());
    if (workerNumber.length < 1 && employeeSlid.length < 1) {
        alert('Please enter employee# or slid');
        buttonElement.removeAttr('disabled').css({color:'#000'});
        return false;
    }
    jQuery.post(ajaxURL,
        { ajax:'true', workerNumber:workerNumber, employeeSlid:employeeSlid}, function (htmlText) {
            if (htmlText == "error") {
                alert('Employee not found');
            } else {
                jQuery('#container_id').html(htmlText);
                showModalDialogWithPosition('add_entitlementRole_tree', false);
            }

            buttonElement.removeAttr('disabled').css({color:'#000'});
        });
}

function isBlank(x) {
    return jQuery.trim(x).length < 1;
}

function validateForm() {
    var isValid = true;
    if (isBlank(jQuery('#workerNumber').val()) && isBlank(jQuery('#employeeSlid').val())) {
        isValid = false;
        jQuery('#workerNumber').addClass('errorBorder');
        jQuery('#employeeSlid').addClass('errorBorder');
    } else {
        jQuery('#workerNumber').removeClass('errorBorder');
        jQuery('#employeeSlid').removeClass('errorBorder');
    }
    if (isBlank(jQuery('#workerRoleHistoryItem').val())) {
        isValid = false;
        jQuery('#workerRoleHistoryItem').addClass('errorBorder');
    } else {
        jQuery('#workerRoleHistoryItem').removeClass('errorBorder');
    }
    if (jQuery('#selectedLocationsList>li').size() < 1) {
        isValid = false;
        jQuery('#selectLocation a.department').addClass('errorBorder');
    } else {
        jQuery('#selectLocation a.department').removeClass('errorBorder');
    }

    if (isValid) jQuery('#access-request-error').hide(); else jQuery('#access-request-error').show();
    return isValid;
}

function checkForDeleteCertification() {
    if (confirm("Delete this Personnel Certification?")) {
        return true;
    }
    return false;
}

function showTerminateForCauseDialog(divId) {
    jQuery('#iagreediv').removeClass('errorBorder');
    jQuery('#terminateForCauseWorkerRoleHistoryItem').removeClass('errorBorder');
    jQuery('#terminateForCauseWorkerRoleHistoryItem').val('');
    showModalDialog(divId, true);
    return false;
}

function validateTerminateForCauseForm() {
    var isValid = true;
    if (isBlank(jQuery('#terminateForCauseWorkerRoleHistoryItem').val())) {
        isValid = false;
        jQuery('#terminateForCauseWorkerRoleHistoryItem').addClass('errorBorder');
    } else {
        jQuery('#terminateForCauseWorkerRoleHistoryItem').removeClass('errorBorder');
    }
    if (!jQuery('#iagree').attr('checked')) {
        isValid = false;
    }
    return isValid;
}

function emptyValues() {
    jQuery('#firstName').val('');
    jQuery('#middleName').val('');
    jQuery('#lastName').val('');
    jQuery('#phone').val('');
    jQuery('#notes').val('');
}

function populateValues(jsonData) {
    jQuery('#firstName').val(jsonData.firstName);
    jQuery('#middleName').val(jsonData.middleName);
    jQuery('#lastName').val(jsonData.lastName);
    jQuery('#phone').val(jsonData.phone);
    jQuery('#notes').val(jsonData.notes);
    jQuery('#slid').attr('readonly', 'true');
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

function validateRevokeForm(radio24h, radio7d, dateId, justificationId, errorDiv) {
    if (!checkRevocationPeriodinRevokeForm(radio24h, radio7d)) {
        return false
    }

    checkDateInRevokeForm(dateId);
    var a = jQuery('#' + justificationId).val();
    if (jQuery.trim(a).length > 254) {
        alert("Revoke justification can not be more than 255 characters.");
        return false;
    }

    if (isDateNotEmpty(dateId) && !isWorkerRoleHistoryItemEmpty(justificationId, errorDiv)) {
        return true
    }
    else {
        isWorkerRoleHistoryItemEmpty(justificationId, errorDiv);
        return false
    }
}

function checkRevocationPeriodinRevokeForm(radio24h, radio7d) {
    if (!jQuery('#' + radio24h).attr('checked') && !jQuery('#' + radio7d).attr('checked')) {
        jQuery("#dateRadioButtons").css('border', '1px solid #f00')
        return false
    } else {
        jQuery("#dateRadioButtons").css('border', 'none')
        return true
    }
}

function checkDateInRevokeForm(dateIdF) {
    dateId = dateIdF;

    date = jQuery("#" + dateId + "_value").val()
    ds = new String(date);
    ds = ds.split('/');
    hour = jQuery("#selected_hour").val();
    min = jQuery("#selected_min").val();
    var de = new Date(ds[2], parseInt(ds[0], 10) - 1, ds[1]);
    de.setHours(parseInt(hour, 10));
    de.setMinutes(parseInt(min, 10));

    var diff = new Date().getTime() - de.getTime();

    if (date != "") {
        if (de > new Date()) {
            alert("Future Date and Time is Not Allowed");
            jQuery("#" + dateId + "_value").css('border', '1px solid #f00');
            jQuery("#" + dateId).val("");
        }
        else {
            jQuery("#" + dateId + "_value").css('border', '1px solid #000');
        }
    }

    var HRFDiff = null;
    var minutes = true;
    var hours = true;
    if (jQuery("#ckb24h").attr('checked')) {
        if (diff >= 0) {
            if (diff > (1000 * 60 * 60 * 24)) {
                HRFDiff = getTimeDifferenceInHRF(diff);

                if (HRFDiff.mins == 0) {
                    HRFDiff.mins = "";
                    minutes = false;
                }
                else {
                    if (HRFDiff.mins == 1) {
                        HRFDiff.mins = HRFDiff.mins + " minute";

                    } else {
                        HRFDiff.mins = HRFDiff.mins + " minutes";
                    }
                    minutes = true;
                }

                if (HRFDiff.hours == 0) {
                    HRFDiff.hours = "";
                    hours = false;
                }
                else {
                    if (HRFDiff.mins == 1) {
                        HRFDiff.hours = HRFDiff.hours + " hour";
                    } else {
                        HRFDiff.hours = HRFDiff.hours + " hours";
                    }
                    if (minutes) {
                        HRFDiff.hours = HRFDiff.hours + " and "
                    }
                    hours = true;
                }

                if (HRFDiff.days == 0) {
                    HRFDiff.days = "";
                }
                else {
                    if (HRFDiff.days == 1) {
                        HRFDiff.days = HRFDiff.days + " day";
                    }
                    else {
                        HRFDiff.days = HRFDiff.days + " days";
                    }
                    if (hours) {
                        HRFDiff.days = HRFDiff.days + " and "
                    }
                }

                var res = confirm(HRFDiff.days + HRFDiff.hours + HRFDiff.mins + " have already passed. Is the effective date you entered correct? If yes click OK else cancel and reenter the correct effective date.");
                if (res == false) {
                    jQuery("#" + dateId + "_value").css('border', '1px solid #f00');
                    jQuery("#" + dateId).val("");
                } else {
                    jQuery("#" + dateId + "_value").css('border', '1px solid #000');
                    jQuery("#" + dateId).val("val");
                }
            }
            else {
                jQuery("#" + dateId + "_value").css('border', '1px solid #000');
                jQuery("#" + dateId).val("val");
            }
        }
    }
    else {
        if (diff >= 0) {
            if (diff > (1000 * 60 * 60 * 24 * 7)) {
                HRFDiff = getTimeDifferenceInHRF(diff);
                if (HRFDiff.mins == 0) {
                    HRFDiff.mins = "";
                    minutes = false;
                }
                else {
                    if (HRFDiff.mins == 1) {
                        HRFDiff.mins = HRFDiff.mins + " minute";

                    } else {
                        HRFDiff.mins = HRFDiff.mins + " minutes";
                    }
                    minutes = true;
                }


                if (HRFDiff.hours == 0) {
                    HRFDiff.hours = "";
                    hours = false;
                }
                else {
                    if (HRFDiff.mins == 1) {
                        HRFDiff.hours = HRFDiff.hours + " hour ";
                    } else {
                        HRFDiff.hours = HRFDiff.hours + " hours ";
                    }
                    if (minutes) {
                        HRFDiff.hours = HRFDiff.hours + "and "
                    }
                    hours = true;
                }

                if (HRFDiff.days == 0) {
                    HRFDiff.days = "";
                }
                else {
                    if (HRFDiff.days == 1) {
                        HRFDiff.days = HRFDiff.days + " day ";
                    }
                    else {
                        HRFDiff.days = HRFDiff.days + " days ";
                    }
                    if (hours) {
                        HRFDiff.days = HRFDiff.days + "and "
                    }
                }


                var res1 = confirm(HRFDiff.days + HRFDiff.hours + HRFDiff.mins + " have already passed. Is the effective date you entered correct? If yes click OK else cancel and reenter the correct effective date.");
                if (res1 == false) {
                    jQuery("#" + dateId + "_value").css('border', '1px solid #f00');
                    jQuery("#" + dateId).val("");
                } else {
                    jQuery("#" + dateId + "_value").css('border', '1px solid #000');
                    jQuery("#" + dateId).val("val");
                }
            }
            else {
                jQuery("#" + dateId + "_value").css('border', '1px solid #000');
                jQuery("#" + dateId).val("val");
            }
        }
    }
}

function getTimeDifferenceInHRF(timestamp) {
    var dayt = (1000 * 60 * 60 * 24);
    var hourt = (1000 * 60 * 60 );
    var mint = (1000 * 60 );
    var retk = new Object();
    retk.hours = 0;
    retk.days = 0;
    retk.mins = 0;
    retk.days = Math.floor(timestamp / dayt);
    var rem = Math.floor(timestamp % dayt);
    retk.hours = Math.floor(rem / hourt);
    rem = Math.floor(rem % hourt)
    retk.mins = Math.floor(rem / mint)
    return retk;
}

function ChangeSelectByValue(ddlID, value, change) {
    var ddl = document.getElementById(ddlID);
    for (var i = 0; i < ddl.options.length; i++) {
        if (ddl.options[i].value == value) {
            if (ddl.selectedIndex != i) {
                ddl.selectedIndex = i;
                if (change)
                    ddl.onchange();
            }
            break;
        }
    }
}

