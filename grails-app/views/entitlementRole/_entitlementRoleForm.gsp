<%@ page import="com.force5solutions.care.cc.Certification; com.force5solutions.care.cc.CcEntitlementRole; com.force5solutions.care.cc.CcEntitlement; com.force5solutions.care.cc.CcOrigin" %>
<table>
    <tbody>

    <tr>
        <td valign="top" width="35%">
            <label for="name"><g:message code="entitlementRole.name.label" default="Name"/>&nbsp;<span class="asterisk">*</span></label>
        </td>
        <td valign="top" class=" ${hasErrors(bean: entitlementRole, field: 'name', 'errors')}">
            <g:textField size="48" name="name" disabled="true" value="${entitlementRole?.name}"/>
        </td>
    </tr>

    <tr>
        <td valign="top" width="35%">
            <label>Standards&nbsp;<span class="asterisk">*</span></label>
        </td>
        <td valign="top" class=" ${hasErrors(bean: entitlementRole, field: 'standards', 'errors')}">${entitlementRole?.standards}</td>
    </tr>

    <tr>
        <td valign="top" width="35%">
            <label>Types&nbsp;<span class="asterisk">*</span></label>
        </td>
        <td valign="top" class=" ${hasErrors(bean: entitlementRole, field: 'types', 'errors')}">${entitlementRole?.types}</td>
    </tr>

    <tr>
        <td valign="top">
            <label for="requiredCertifications">Required Certifications</label>
        </td>
        <td valign="top" class=" ${hasErrors(bean: entitlementRole, field: 'certifications', 'errors')}">
            <div style="width:300px;">
                <ui:multiSelect name="requiredCertifications" from="${Certification.list()}"
                        noSelection="['':'(Select One)']"
                        class="listbox" style="width:300px;"
                        multiple="yes" optionKey="id" size="1" value="${entitlementRole?.requiredCertifications}"/>
            </div>
        </td>
    </tr>
    </tbody>
</table>
<div id="entitlementAccessValues" style="display:none;">
    <g:each in="${entitlementRole.entitlements}" var="entitlement">
        <div id="entitlementHtml-${entitlement.id}">
            <input type="hidden" value="${entitlement.id}" name="entitlementId"/>
        </div>
    </g:each>
</div>
