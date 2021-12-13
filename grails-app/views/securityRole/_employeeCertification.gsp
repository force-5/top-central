<div class="vendor">
        <span>Employee Certifications - Associate Certifications With a Employee</span>
        <div class="vendor-sub-text">Sub-Boxes Restrict access based on association with Employee</div>
    <ul>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'CREATE_EMPLOYEE_CERTIFICATION', permissionTitle: 'Create', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'READ_EMPLOYEE_CERTIFICATION', permissionTitle: 'Read ', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'UPDATE_EMPLOYEE_CERTIFICATION', permissionTitle: 'Update', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'RENEW_EMPLOYEE_CERTIFICATION', permissionTitle: 'Renew', role: role]"/>
        <g:render template="/securityRole/employeePermissionWithRoleCheckbox" model="[permission: 'DELETE_EMPLOYEE_CERTIFICATION', permissionTitle: 'Delete', role: role]"/>
    </ul>
    <div class="clr"></div>
</div>
