<div class="vendor">
        <span>Employee Access - Associate Locations With a Employee</span>
        <div class="vendor-sub-text">Sub-Boxes Restrict access based on association with Employee</div>
    <ul>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'CREATE_EMPLOYEE_ACCESS', permissionTitle: 'Create', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'READ_EMPLOYEE_ACCESS', permissionTitle: 'Read', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'UPDATE_EMPLOYEE_ACCESS', permissionTitle: 'Update', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'DELETE_EMPLOYEE_ACCESS', permissionTitle: 'Delete', role: role]"/>
    </ul>
    <div class="clr"></div>
    <ul style="margin-top:15px;">

        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'REQUEST_EMPLOYEE_ACCESS', permissionTitle: 'Request Access', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'REQUEST_EMPLOYEE_REVOKE', permissionTitle: 'Request Revocation', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'REQUEST_EMPLOYEE_TERMINATE', permissionTitle: 'Request Termination', role: role]"/>
    </ul>
    <div class="clr"></div>
</div>
