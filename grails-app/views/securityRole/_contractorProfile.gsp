<div class="vendor">
    <span>Contractor Profile</span>
    <div class="vendor-sub-text">Sub-Boxes Restrict access based on association with Contractor</div>
    <ul>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'CREATE_CONTRACTOR_PROFILE', permissionTitle: 'Create', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'READ_CONTRACTOR_PROFILE', permissionTitle: 'Read', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'UPDATE_CONTRACTOR_PROFILE', permissionTitle: 'Update', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'DELETE_CONTRACTOR_PROFILE', permissionTitle: 'Delete', role: role]"/>
    </ul>
    <div class="clr"></div>
</div>
