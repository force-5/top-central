<%@ page import="com.force5solutions.care.common.CustomPropertyType" %>
<div id="addEntitlementAccessPopup" style="width:400px;">
    <div id="head-add_contractor">Add Custom Property</div>
    <div class="contractor_cert-dept">
        <div class="contract_cert clearfix">
            <div id="new-validation-error-div" class="error-status" style="margin-top:0; padding-top:0; display:none;"><span>Field can not be left blank.</span></div>
            <div id="new-custom-unique-error-div" class="error-status" style="margin-top:0; padding-top:0; display:none;"><span>Please enter a unique value.</span></div>
            <div id="not-integer-error" class="error-status" style="margin-top:0; padding-top:0; display:none;"><span>Please enter a valid number.</span></div>

            <div class="alert-con-popup clearfix">
                <div class="custom-property-name"><g:message code="customProperty.name.label" default="Name"/>&nbsp;<span class="asterisk">*</span></div>
                <div id='custom-property-name' class="custom-property-input">
                    <g:textField name="customPropertyName" id="customPropertyName"/>
                </div>
            </div>
            <div class="alert-con-popup clearfix">
                <div class="custom-property-name"><g:message code="customProperty.propertyType.label" default="Property Type"/>&nbsp;<span class="asterisk">*</span></div>
                <div id='' class="custom-property-input"><label>
                    <g:select name="customPropertyType" id="customPropertyType"
                            noSelection="['':'(Select One)']"
                            onChange="showHideSizeDiv(this.value);"
                            from="${CustomPropertyType?.values()}"/>
                </label></div>
            </div>
            <div class="alert-con-popup clearfix" id='custom-property-size' style="display:none">
                <div class="custom-property-name">Size</div>
                <div class="custom-property-input"><label>
                    <g:textField name="customPropertySize" id="customPropertySize" style="width:60px;"/>
                </label></div>
            </div>
            <div class="alert-con-popup clearfix">
                <div class="custom-property-name"><g:checkBox name="isCustomPropertyRequired" id="isCustomPropertyRequired" value="${customProperty?.isRequired?'1':'0'}"/> <g:message code="customProperty.isRequired.label" default="Mandatory"/></div>
            </div>
        </div>
        <div class="department1">
            <input type="button" class="button" value="Add" onclick="addNewCustomProperty('${g.resource(dir: 'images', file: 'cross.gif')}');"/>
            <input type="button" class="button simplemodal-close" value="Cancel" onclick="jQuery.modal.close();"/>
        </div>
    </div>
    <div id="close-add_contractor">
        <img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/>
    </div>
</div>
<script type="text/javascript">
    function addNewCustomProperty(imagePath) {
        var propertyName = jQuery('#customPropertyName').val()
        var propertyType = jQuery('#customPropertyType').val()
        var isRequired = jQuery('#isCustomPropertyRequired').attr('checked') ? 1 : 0
        var customPropertySize = jQuery('#customPropertySize').val();
        var validationResult = validateNameOfCustomProperty()
        var validateSize = validateCustomPropertySize(customPropertySize)
        if (validationResult && validateSize) {
            var myHtml = '<input type="hidden" name="propertyName" value="' + propertyName + '">'
            myHtml += '<input type="hidden" name="customPropertyType" value=' + propertyType + '>'
            myHtml += '<input type="hidden" name="isCustomPropertyRequired" value=' + isRequired + '>'
            myHtml += '<input type="hidden" name="customPropertySize" value=' + customPropertySize + '>'
            jQuery('#custom-property-div').append('<div id="new-custom-property-' + propertyName + '"><strong>' + propertyName +
                                                  '<span>&nbsp;(</span>' + propertyType +
                                                  '<span>,&nbsp;</span>' + (isRequired ? 'Mandatory' : 'Optional') +
                                                  '<span>)</span></strong> &nbsp;&nbsp;<a onclick="removeCustomProperty(this);"><img src="' + imagePath + '"/></a><br/>' + myHtml + '</div>')
            jQuery.modal.close();
        }
    }
    function validateNameOfCustomProperty() {
        var propertyName = jQuery('#customPropertyName').val();
        var propertyType = jQuery('#customPropertyType').val();
        var result = true;
        if (propertyName == "" && propertyType == "") {
            result = false;
            jQuery('#customPropertyName').css('border', '1px solid red').focus();
            jQuery('#customPropertyType').css('border', '1px solid red')
            jQuery('#new-validation-error-div').show();
            jQuery('#new-custom-unique-error-div').hide();

        } else if (propertyName == "") {
            result = false;
            jQuery('#customPropertyName').css('border', '1px solid red').focus();
            jQuery('#new-validation-error-div').show();
            jQuery('#new-custom-unique-error-div').hide();

        } else if (propertyType == "") {
            jQuery('#customPropertyType').css('border', '1px solid red').focus();
            jQuery('#new-validation-error-div').show();
            jQuery('#new-custom-unique-error-div').hide();
            result = false;
        }
        else if (!isCustomPropertyNameUnique(propertyName)) {
            result = false;
            jQuery('#customPropertyName').css('border', '1px solid red').focus();
            jQuery('#new-custom-unique-error-div').show();
            jQuery('#new-validation-error-div').hide();
        }
        return result;
    }
    function isCustomPropertyNameUnique(valueToCheck) {
        return jQuery('input[name=propertyName]').filter("[value='" + valueToCheck + "']").size() == 0
    }

    function showHideSizeDiv(value) {
        if (value == "Text" || value == "Number") {
            jQuery('#customPropertySize').val("")
            jQuery('#customPropertySize').css('border', '1px solid gray')
            jQuery('#custom-property-size').show();
        } else {
            jQuery('#custom-property-size').hide();
        }
    }
    function validateCustomPropertySize(customPropertySize) {
        var result = true;
        if (customPropertySize == "" || isInteger(customPropertySize)) {
            jQuery('#not-integer-error').hide()
        } else {
            jQuery('#not-integer-error').show()
            jQuery('#customPropertySize').css('border', '1px solid red').focus();
            result = false;
        }
        return result;
    }
    function isInteger(a) {
        return (!(parseInt(a).toString() == 'NaN'))
    }
</script>