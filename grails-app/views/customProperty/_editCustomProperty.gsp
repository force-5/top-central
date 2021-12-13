<%@ page import="com.force5solutions.care.common.CustomPropertyType; com.force5solutions.care.common.CustomPropertyType" %>
<div id="addEntitlementAccessPopup" style="width:400px;">
    <div id="head-add_contractor">Edit Custom Property</div>
    <div class="contractor_cert-dept">
        <div class="contract_cert" style="height:130px;">
            <div id="edit-custom-property-error" class="error-status" style="margin-top:0; padding-top:0; display:none;"><span>Field can not be left blank.</span></div>
            <div id="custom-edit-unique-error-div" class="error-status" style="margin-top:0; padding-top:0; display:none;"><span>Please enter a unique value.</span></div>
            <div id="not-integer-error-div" class="error-status" style="margin-top:0; padding-top:0; display:none;"><span>Please enter a valid number.</span></div>
            <input type="hidden" name="divToRemove" id="divToRemove">
            <input type="hidden" name="notIncludedCustomProperty">

            <div class="alert-con-popup">
                <div class="custom-property-name"><g:message code="customProperty.name.label" default="Name"/>&nbsp;<span class="asterisk">*</span></div>
                <div id='edit-custom-property-name' class="custom-property-input">
                    <g:textField name="editCustomPropertyName" id="editCustomPropertyName"/>
                </div>
                <div class="clr"></div>
            </div>
            <div class="alert-con-popup">
                <div class="custom-property-name"><g:message code="customProperty.propertyType.label" default="Property Type"/>&nbsp;<span class="asterisk">*</span></div>
                <div id='custom-property-type' class="custom-property-input"><label>
                    <g:select name="editPropertyType" id="editPropertyType"
                            noSelection="['':'(Select One)']"
                            onChange="showHideCustomPropertySizeDiv(this.value,'edit-custom-property-size');"
                            from="${CustomPropertyType?.values()}"/>
                </label></div>
                <div class="clr"></div>
            </div>
            <div class="alert-con-popup" id='edit-custom-property-size' style="display:none">
                <div class="custom-property-name">Size</div>
                <div class="custom-property-input"><label>
                    <g:textField name="editCustomPropertySize" id="editCustomPropertySize" style="width:60px;"/>
                </label></div>
                <div class="clr"></div>
            </div>
            <div class="alert-con-popup">
                <div class="custom-property-name"><g:message code="customProperty.isRequired.label" default="Mandatory"/></div>
                <div id='custom-property-required' class="check-box"><label>
                    <input type="checkBox" name="isRequiredEdit" id="isRequiredEdit"/>
                </label></div>
                <div class="clr"></div>
            </div>
        </div>
        <div class="department1">
            <input type="button" class="button" value="Update" onclick="updateCustomPropertyDiv('${g.resource(dir: 'images', file: 'cross.gif')}');"/>
            <input type="button" class="button simplemodal-close" value="Cancel" onclick="jQuery.modal.close();"/>
        </div>

    </div>
    <div id="close-add_contractor">
        <img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/>
    </div>
</div>
<script type="text/javascript">
    function updateCustomPropertyDiv(imagePath) {
        var propertyName = jQuery('#editCustomPropertyName').val();
        var editPropertyType = jQuery('#editPropertyType').val();
        var isRequiredEdit = jQuery('#isRequiredEdit').attr('checked') ? 1 : 0
        var customPropertySize = jQuery('#editCustomPropertySize').val();

        var divId = jQuery("input[name='divToRemove']").val();
        var notIncluded = jQuery("input[name='notIncludedCustomProperty']").val();

        jQuery('#' + divId).remove();
        var validationResult = validateNameOfCustomProperty(notIncluded);
        var validSize = validateCustomPropertySizeEdit(customPropertySize)
        if (validationResult && validSize) {
            var myHtml = '<input type="hidden" name="propertyName" value="' + propertyName + '">'
            myHtml += '<input type="hidden" name="customPropertyType" value=' + editPropertyType + '>'
            myHtml += '<input type="hidden" name="isCustomPropertyRequired" value=' + isRequiredEdit + '>'
            myHtml += '<input type="hidden" name="customPropertySize" value=' + customPropertySize + '>'
            jQuery('#custom-property-div').append('<div id="new-custom-property-' + propertyName + '"><strong>' + propertyName +
                                                  '<span>&nbsp;(</span>' + editPropertyType +
                                                  '<span>,&nbsp;</span>' + (isRequiredEdit ? 'Mandatory' : 'Optional') +
                                                  '<span>)</span></strong> &nbsp;&nbsp;<a onclick="removeCustomProperty(this);"><img src="' + imagePath + '"/></a><br/>' + myHtml + '</div>')
            jQuery.modal.close();
        }
    }
    function validateNameOfCustomProperty(notIncluded) {
        var propertyName = jQuery('#editCustomPropertyName').val();
        var propertyType = jQuery('#editPropertyType').val();
        var notToInclude = notIncluded;
        var result = true;
        if (propertyName == "" && propertyType == "") {
            result = false;
            jQuery('#editCustomPropertyName').css('border', '1px solid red').focus();
            jQuery('#editPropertyType').css('border', '1px solid red')
            jQuery('#edit-custom-property-error').show();
            jQuery('#custom-edit-unique-error-div').hide();
        }
        else if (propertyName == "") {
            result = false;
            jQuery('#editCustomPropertyName').css('border', '1px solid red').focus();
            jQuery('#edit-custom-property-error').show();
            jQuery('#custom-edit-unique-error-div').hide();
        }
        else if (propertyType == "") {
            jQuery('#editPropertyType').css('border', '1px solid red').focus();
            jQuery('#custom-edit-unique-error-div').show();
            jQuery('#edit-custom-property-error').hide();
            result = false;
        }
        else if (!isCustomPropertyNameUnique(propertyName, notToInclude)) {
            jQuery('#editCustomPropertyName').css('border', '1px solid red').focus();
            jQuery('#custom-edit-unique-error-div').show();
            jQuery('#edit-custom-property-error').hide();
            result = false;
        }
        return result;
    }

    function isCustomPropertyNameUnique(valueToCheck, notIncluded) {
        return jQuery('input[name=propertyName]').filter("[value='" + jQuery.trim(valueToCheck) + "'][value!='" + jQuery.trim(notIncluded) + "']").size() == 0
    }
    function showHideCustomPropertySizeDiv(value, divToUpdate) {
        jQuery('#editCustomPropertySize').val("")
        jQuery('#customPropertySize').css('border', 'none')
        if (value == "Text" || value == "Number") {
            jQuery('#' + divToUpdate).show();
        } else {
            jQuery('#' + divToUpdate).hide();
        }
    }
    function validateCustomPropertySizeEdit(customPropertySize) {
        var result = true;
        if (customPropertySize == "" || isInteger(customPropertySize)) {
            jQuery('#not-integer-error-div').hide()
        } else {
            jQuery('#not-integer-error-div').show()
            jQuery('#editCustomPropertySize').css('border', '1px solid red').focus();
            result = false;
        }
        return result;
    }
    function isInteger(a) {
        return (!(parseInt(a).toString() == 'NaN'))
    }
</script>