<div class="vendor">
        <span>Contractor Access - Associate Locations With a Contractor</span>
        <div class="vendor-sub-text">Sub-Boxes Restrict access based on association with Contractor</div>
    <ul>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'CREATE_CONTRACTOR_ACCESS', permissionTitle: 'Create', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'READ_CONTRACTOR_ACCESS', permissionTitle: 'Read', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'UPDATE_CONTRACTOR_ACCESS', permissionTitle: 'Update', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'DELETE_CONTRACTOR_ACCESS', permissionTitle: 'Delete', role: role]"/>
    </ul>
    <div class="clr"></div>
    <ul style="margin-top:15px;">

        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'REQUEST_CONTRACTOR_ACCESS', permissionTitle: 'Request Access', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'REQUEST_CONTRACTOR_REVOKE', permissionTitle: 'Request Revocation', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'REQUEST_CONTRACTOR_TERMINATE', permissionTitle: 'Request Termination', role: role]"/>
    </ul>
    <div class="clr"></div>
</div>
