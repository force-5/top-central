<div class="vendor">
    <span>Employee Profile</span>
    <div class="vendor-sub-text">Sub-Boxes Restrict access based on association with Employee</div>
    <ul>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'CREATE_EMPLOYEE_PROFILE', permissionTitle: 'Create', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'READ_EMPLOYEE_PROFILE', permissionTitle: 'Read', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'UPDATE_EMPLOYEE_PROFILE', permissionTitle: 'Update', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'DELETE_EMPLOYEE_PROFILE', permissionTitle: 'Delete', role: role]"/>
    </ul>
    <div class="clr"></div>
</div>
