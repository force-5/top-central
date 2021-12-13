<%@ page import="com.force5solutions.care.cc.Certification; com.force5solutions.care.ldap.Permission;" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <g:set var="entityName" value="${message(code: 'entitlementPolicy.label', default: 'Entitlement Policy')}"/>
    <title>Create Entitlement Policy</title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <g:render template="/permission/listButton"
              model="[permission: Permission.READ_ENTITLEMENT_POLICY, label: 'Entitlement Policy List']"/>
</div>

<div class="body">
    <h1>Create Entitlement Policy</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:render template="/shared/errors" model="[instance: entitlementPolicy]"/>
    <g:form action="save" method="post">
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
                    <div id="standard-list">
                        <td valign="top" class="name">
                            <label for="name">Standards</label>
                        </td>
                        <td valign="top" class="name">
                            <div id='standard-div'>
                                <g:if test="${entitlementPolicy?.standards}">
                                    <g:each in="${entitlementPolicy?.standards}" var="standard">
                                        <div id="new-standard-${standard}">
                                            <input type="hidden" name="standardName" value="${standard}">
                                            <strong>${standard}</strong> &nbsp;&nbsp;<a onclick="removeStandard(this);">
                                            <img src="${createLinkTo(dir: 'images', file: 'cross.gif')}"/></a><br/>
                                        </div>
                                    </g:each>
                                </g:if>
                            </div>
                        </td>
                    </div>
                </tr>
                <tr class="prop">
                    <td valign="top">
                    </td>
                    <td valign="top" class="name">
                        <a href="#" onclick="showStandardPopUp();">Add Standards</a>
                    </td>
                </tr>
                <tr class="prop">
                    <div id="custom-property-list">
                        <td valign="top" class="name">
                            <label for="name"><g:message code="entitlementPolicy.customProperties.label"
                                                         default="Custom Properties"/></label>
                        </td>
                        <td valign="top" class="name">
                            <div id='custom-property-div'>
                                <g:if test="${entitlementPolicy?.customProperties}">
                                    <g:each in="${entitlementPolicy?.customProperties}" var="customProperty">
                                        <div id="new-custom-property-${customProperty?.name}">
                                            <input type="hidden" name="propertyName" value="${customProperty?.name}">
                                            <input type="hidden" name="customPropertyType"
                                                   value="${customProperty?.propertyType}">
                                            <input type="hidden" name="isCustomPropertyRequired"
                                                   value="${customProperty?.isRequired}">
                                            <strong>${customProperty?.name}&nbsp;(${customProperty?.propertyType},&nbsp;${customProperty?.isRequired ? 'Mandatory' : 'Optional'})</strong> &nbsp;&nbsp;<a
                                                onclick="removeCustomProperty(this);">
                                            <img src="${createLinkTo(dir: 'images', file: 'cross.gif')}"/></a><br/>
                                        </div>
                                    </g:each>
                                </g:if>
                            </div>
                        </td>
                    </div>
                </tr>

                <tr class="prop">
                    <td valign="top">
                    </td>
                    <td valign="top" class="name">
                        <a href="#" onclick="showCustomPropertyPopUp();">Add Custom Property</a>
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
                        <label for="name">Certifications for Employees&nbsp;<span class="asterisk">*</span></label>
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
                        <label for="name">Certifications for Contractors&nbsp;<span class="asterisk">*</span></label>
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
            <span class="button"><g:submitButton name="create" class="save"
                                                 value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
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

</body>
</html>
