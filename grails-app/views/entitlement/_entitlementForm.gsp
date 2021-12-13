<%@ page import="com.force5solutions.care.cc.CcOrigin; com.force5solutions.care.cc.EntitlementPolicy" %>
<table>
    <tbody>

    <tr>
        <td valign="top">
            <label for="status"><g:message code="entitlement.status.label" default="Status"/>&nbsp;<span class="asterisk">*</span></label>
        </td>
        <td valign="top" class=" ${hasErrors(bean: entitlement, field: 'status', 'errors')}">
            <g:each in="${statuses}" var="status">
                <g:radio name="status" value="${status.name()}" checked="${status == entitlement.status}"/>
                <span>${status}</span>
            </g:each>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <label for="origin"><g:message code="entitlement.origin.label" default="Origin"/>&nbsp;<span class="asterisk">*</span></label>
        </td>
        <td valign="top" class=" ${hasErrors(bean: entitlement, field: 'origin', 'errors')}">
            <g:select class="listbox" name="origin.id" from="${CcOrigin.list()}"
                    noSelection="['':'(Select One)']"
                    optionKey="id" value="${entitlement?.origin?.id}"/>
        </td>
    </tr>

    <tr>
        <td valign="top">
            <label for="type"><g:message code="entitlement.type.label" default="Entitlement Policy"/>&nbsp;<span class="asterisk">*</span></label>
        </td>
        <td valign="top" class=" ${hasErrors(bean: entitlement, field: 'type', 'errors')}">
            <g:select class="listbox" name="type.id" id="entitlementPolicySelectBox"
                    from="${entitlementPolicy.list()}"
                    optionKey="id"
                    noSelection="['':'(Select One)']"
                    onChange="showHideCustomPropertyDiv(this.value,'${createLink(action:'getCustomProperties', controller:'entitlementPolicy')}');"
                    value="${entitlement?.type?.id}"/>
        </td>
    </tr>
    <tr>
        <td id="customPropertiesRow" colspan="2">
            <g:if test="${entitlement.customPropertyValues}">
                <g:render template="/customProperty/customPropertiesByEntitlement" model="[customPropertyValues: entitlement.customPropertyValues]"/>
            </g:if>
        </td>
    </tr>

    <tr>
        <td valign="top">
            <label for="notes"><g:message code="entitlement.notes.label" default="Notes"/></label>
        </td>
        <td valign="top" class=" ${hasErrors(bean: entitlement, field: 'notes', 'errors')}">
            <textarea name="notes" cols="" class="area" style="width:300px; height:50px; " rows="3">${fieldValue(bean: entitlement, field: 'notes')}</textarea>
        </td>
    </tr>
    </tbody>
</table>
<div id="entitlement-custom-properties">

</div>

<script type="text/javascript">
    function showHideCustomPropertyDiv(entitlementPolicyId, ajaxUrl) {
        jQuery.get(ajaxUrl,
        { ajax: 'true', entitlementPolicyId:entitlementPolicyId}, function(data) {
            jQuery('#customPropertiesRow').html(data);

        });
    }
</script>
