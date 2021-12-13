<div id="addEntitlementAccessPopup" style="width:400px;">
    <div id="head-add_contractor">Edit Standard</div>
    <div class="contractor_cert-dept">
        <div class="contract_cert" style="height:100px;">
            <div id="editStandard-validation-error-div" class="error-status" style="margin-top:0; padding-top:0; display:none;"><span>Field can not be left blank.</span></div>
            <div id="editStandard-unique-error-div" class="error-status" style="margin-top:0; padding-top:0; display:none;"><span>Please enter a unique value.</span></div>
            <input type="hidden" name="divToRemove" id="divToRemove">
            <input type="hidden" name="notIncluded">
            <div class="alert-con-popup">
                <div class="custom-property-name"><g:message code="entitlementPolicy.name.label" default="Standard"/>&nbsp;<span class="asterisk">*</span></div>
                <div id='edit-standard-name' class="standard-input">
                    <g:textField name="editStandardName" id="editStandardName"/>
                </div>
                <div class="clr"></div>
            </div>
        </div>
        <div class="department1">
            <input type="button" class="button" value="Add" onclick="updateStandard('${g.resource(dir: 'images', file: 'cross.gif')}');"/>
            <input type="button" class="button simplemodal-close" value="Cancel" onclick="jQuery.modal.close();"/>
        </div>

    </div>
    <div id="close-add_contractor">
        <img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/>
    </div>
</div>
<script type="text/javascript">
    function updateStandard(imagePath) {
        var standardName = jQuery('#editStandardName').val();
        var notIncluded = jQuery("input[name='notIncluded']").val();
        var validateValue = validateNameOfStandard(notIncluded);
        if (validateValue) {
            var divId = jQuery("input[name='divToRemove']").val();
            jQuery('#' + divId).remove();
            var myHtml = '<input type="hidden" name="standardName" value="' + standardName + '">'
            jQuery('#standard-div').append('<div id="new-standard-' + standardName + '"><strong>' + standardName +
                                           '</strong>&nbsp;&nbsp;<a onclick="removeStandard(this);"><img src="' + imagePath + '"/></a><br/>' + myHtml + '</div>')
            jQuery.modal.close();
        }
    }
    function validateNameOfStandard(notIncluded) {
        var notToInclude = notIncluded;
        var standardName = jQuery('#editStandardName').val();
        var isValid = true;
        if (standardName == "") {
            jQuery('#editStandardName').css('border', '1px solid red').focus();
            jQuery('#editStandard-validation-error-div').show()
            jQuery('#editStandard-unique-error-div').hide();
            isValid = false;
        }
        else if (!isStandardNameUnique(standardName, notToInclude)) {
            isValid = false;
            jQuery('#editStandardName').css('border', '1px solid red').focus();
            jQuery('#editStandard-unique-error-div').show();
            jQuery('#editStandard-validation-error-div').hide()
        }
        return isValid;
    }
    function isStandardNameUnique(valueToCheck, notIncluded) {
        return jQuery('input[name=standardName]').filter("[value='" + jQuery.trim(valueToCheck) + "'][value!='" + jQuery.trim(notIncluded) + "']").size() == 0
    }
</script>