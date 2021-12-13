<%@ page import="com.force5solutions.care.common.CareConstants; com.force5solutions.care.common.CareConstants" %>
<li>
    <div class="checkbox-container" id="newDiv">
        <g:checkBox value="${CareConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL}" name="${permission}" class="checkbox7 parent-check-box"
                checked="${care.isPermissionChecked(role: role, permission: permission, value: CareConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL)}"/>
    </div>
    <div class="checkbox-text-container">${permissionTitle}</div>
    <div class="checkbox-sub-container">
        <g:render template="/securityRole/rolePermissionCheckbox"
                model="[permission: permission, permissionValue:CareConstants.ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL,
                 title: 'If Supervisor', role: role]"/>
        <g:render template="/securityRole/rolePermissionCheckbox"
                model="[permission: permission, permissionValue:CareConstants.ACCESS_IF_SELF_PERMISSION_LEVEL,
                 title: 'If Self', role: role]"/>
        <div class="clr"></div>
    </div>
</li>

