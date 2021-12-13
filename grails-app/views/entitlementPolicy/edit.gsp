<%@ page import="com.force5solutions.care.cc.Certification; com.force5solutions.care.ldap.Permission;" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <g:set var="entityName" value="${message(code: 'entitlementPolicy.label', default: 'Entitlement Policy')}"/>
    <title><g:message code="default.edit.label" args="[entityName]" default="Edit Entitlement Policy"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <g:render template="/permission/listButton"
              model="[permission: Permission.READ_ENTITLEMENT_POLICY, label: 'Entitlement Policy List']"/>
    <g:render template="/permission/createButton"
              model="[permission: Permission.CREATE_ENTITLEMENT_POLICY, label: 'New Entitlement Policy']"/>
</div>

<div class="body">
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:render template="/shared/errors" model="[instance: entitlementPolicy]"/>
    <g:form method="post">
        <g:hiddenField name="id" value="${entitlementPolicy?.id}"/>
        <g:hiddenField name="version" value="${entitlementPolicy?.version}"/>
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="entitlementPolicy.name.label" default="Name"/>&nbsp;<span
                                class="asterisk">*</span></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: entitlementPolicy, field: 'name', 'errors')}">
                        <g:textField name="name" value="${entitlementPolicy?.name}"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="entitlementPolicy.standards.label"
                                                     default="Standards"/></label>
                    </td>
                    <ul>
                        <td valign="top" class="value">
                            <g:each in="${entitlementPolicy.standards}" var="standard">
                                <g:render template="/entitlementPolicy/standardValues" model="[standard:standard]"/>
                            </g:each>
                            <div id='standard-div'>
                            </div>
                        </td>
                    </ul>
                </tr>
                <tr class="prop">
                    <td valign="top">
                    </td>
                    <td valign="top" class="name">
                        <a href="#"
                           onclick="showStandardPopUp();">${message(code: 'default.add.label', args: [message(code: 'entitlementPolicy.standards.label', default: 'Standards')])}</a>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                    </td>
                    <td valign="top" class="value">
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="entitlementPolicy.customProperty.label"
                                                     default="Custom Properties"/></label>
                    </td>
                    <ul>
                        <td valign="top" class="value">
                            <g:each in="${entitlementPolicy.customProperties}" var="customProperty">
                                <g:render template="/customProperty/customPropertyValues"
                                          model="[customProperty:customProperty]"/>
                            </g:each>
                            <div id='custom-property-div'>
                            </div>
                        </td>
                    </ul>
                </tr>
                <tr class="prop">
                    <td valign="top">
                    </td>
                    <td valign="top" class="name">
                        <a href="#"
                           onclick="showCustomPropertyPopUp();">${message(code: 'default.add.label', args: [message(code: 'customPropery.label', default: 'Custom Property')])}</a>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name">Required Certifications&nbsp;<span class="asterisk">*</span></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: entitlementPolicy, field: 'name', 'errors')}">
                        <div class="certificationDiv">
                            <ui:multiSelect
                                    name="requiredCertifications"
                                    from="${Certification.list()}"
                                    value="${entitlementPolicy?.requiredCertifications}"
                                    optionKey="id"
                                    noSelection="['':'(Select One)']"
                                    class="listbox"
                                    style="width:200px;"/>
                        </div>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name">Certifications for Employee&nbsp;<span class="asterisk">*</span></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: entitlementPolicy, field: 'name', 'errors')}">
                        <div class="certificationDiv">
                            <ui:multiSelect
                                    name="requiredCertificationsForEmployee"
                                    from="${Certification.list()}"
                                    value="${entitlementPolicy?.requiredCertificationsForEmployee}"
                                    optionKey="id"
                                    noSelection="['':'(Select One)']"
                                    class="listbox"
                                    style="width:200px;"/>
                        </div>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name">Certifications for Contractor&nbsp;<span class="asterisk">*</span></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: entitlementPolicy, field: 'name', 'errors')}">
                        <div class="certificationDiv">
                            <ui:multiSelect
                                    name="requiredCertificationsForContractor"
                                    from="${Certification.list()}"
                                    value="${entitlementPolicy?.requiredCertificationsForContractor}"
                                    optionKey="id"
                                    noSelection="['':'(Select One)']"
                                    class="listbox"
                                    style="width:200px;"/>
                        </div>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:actionSubmit class="save" action="update"
                                                 value="${message(code: 'default.button.update.label', default: 'Update')}"/></span>
            <g:if test="${isEditDisabled}">
                <g:render template="/permission/deleteButton"
                          model="[permission: Permission.DELETE_ENTITLEMENT_POLICY]"/>
            </g:if>
            <g:else>
                <input type="button" class="delete" style="color:gray;" value="Delete"/>
            </g:else>
        </div>
    </g:form>
    <div class="requiredIndicator">
        &nbsp;<span style="color:red;">*</span><g:message code="required.field.text"/></div>
</div>

<div id="custom-property-popup" style="display:none">
    <g:render template="/customProperty/addCustomProperty"/>
</div>

<div id="standard-popup" style="display:none">
    <g:render template="/entitlementPolicy/addStandard"/>
</div>

<div id="edit-custom-property-popup" style="display:none">
    <g:render template="/customProperty/editCustomProperty"/>
</div>

<div id="edit-standard-popup" style="display:none">
    <g:render template="/entitlementPolicy/editStandard"/>
</div>
<script type="text/javascript">
    function editCustomPropertyValue(divToUpdate, name, property, isRequired, customPropertyId, size, notToInclude) {
        showModalDialog('edit-custom-property-popup', true);
        jQuery('#editCustomPropertyName').css('border', '1px solid gray').focus();
        jQuery('#edit-custom-property-error').hide();
        jQuery('#edit-custom-property-size').hide()
        jQuery('#editCustomPropertySize').val("")
        jQuery("input[name='editCustomPropertyName']").val(name);
        jQuery("select[name='editPropertyType']").val(property);
        if (isRequired == 'true') {
            jQuery("#isRequiredEdit").attr('checked', true)
        } else {
            jQuery("#isRequiredEdit").attr('checked', false)
        }
        jQuery("input[name='customPropertyId']").val(customPropertyId);
        jQuery("input[name='divToRemove']").val(divToUpdate);
        jQuery("input[name='notIncludedCustomProperty']").val(notToInclude);
        if (size != "") {
            jQuery('#edit-custom-property-size').show()
            jQuery('#editCustomPropertySize').val(size);
        }
    }

    function editStandard(divToUpdate, name, notToInclude) {
        showModalDialog('edit-standard-popup', true);
        jQuery('#editStandardName').val(name);
        jQuery('#divToRemove').val(divToUpdate);
        jQuery("input[name='notIncluded']").val(notToInclude);
        jQuery('#editStandardName').css('border', '1px solid gray').focus();
        jQuery('#editStandard-validation-error-div').hide();
        jQuery('#editStandard-unique-error-div').hide();
    }
</script>
</body>
</html>
