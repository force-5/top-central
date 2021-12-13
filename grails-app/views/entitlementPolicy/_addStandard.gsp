<div id="addEntitlementAccessPopup" style="width:400px;">
    <div id="head-add_contractor">Add Standard</div>
    <div class="contractor_cert-dept">
        <div class="contract_cert" style="height:100px;">
            <div id="standard-validation-error-div" class="error-status" style="margin-top:0; padding-top:0; display:none;"><span>Field can not be left blank.</span></div>
            <div id="standard-unique-error-div" class="error-status" style="margin-top:0; padding-top:0; display:none;"><span>Please enter a unique value.</span></div>

            <div class="alert-con-popup">
                <div class="custom-property-name"><g:message code="entitlementPolicy.name.label" default="Standard"/>&nbsp;<span class="asterisk">*</span></div>
                <div id='standard-name' class="standard-input">
                    <g:textField name="standardName" id="standardName"/>
                </div>
                <div class="clr"></div>
            </div>
        </div>
        <div class="department1">
            <input type="button"  class="button" value="Add" onclick="addStandard('${g.resource(dir: 'images', file: 'cross.gif')}');"/>
            <input type="button" class="button simplemodal-close" value="Cancel" onclick="jQuery.modal.close();"/>
        </div>

    </div>
    <div id="close-add_contractor">
        <img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/>
    </div>
</div>
<script type="text/javascript">
    function addStandard(imagePath) {
        var standardName = jQuery('#standardName').val();
        var validateValue = validateNameOfStandard();
        if (validateValue) {
            var myHtml = '<input type="hidden" name="standardName" value="' + standardName + '">'
            jQuery('#standard-div').append('<div id="new-standard-' + standardName + '"><strong>' + standardName +
                                              '</strong>&nbsp;&nbsp;<a onclick="removeStandard(this);"><img src="' + imagePath + '"/></a><br/>'+myHtml+'</div>')
            jQuery.modal.close();
        }
    }
    function validateNameOfStandard() {
        var standardName = jQuery('#standardName').val();
        var isValid = true;
        if (standardName == "") {
            jQuery('#standardName').css('border', '1px solid red').focus();
            jQuery('#standard-validation-error-div').show()
            jQuery('#standard-unique-error-div').hide();
            isValid = false;
        }
        else if (!isStandardNameNameUnique(standardName)) {
            isValid = false;
            jQuery('#standardName').css('border', '1px solid red').focus();
            jQuery('#standard-unique-error-div').show();
            jQuery('#standard-validation-error-div').hide()
        }
        return isValid;
    }
    function isStandardNameNameUnique(valueToCheck) {
        return jQuery('input[name=standardName]').filter("[value='" + valueToCheck + "']").size() == 1
    }
</script>