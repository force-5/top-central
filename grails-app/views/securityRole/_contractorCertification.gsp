<div class="vendor">
        <span>Contractor Certifications - Associate Certifications With a Contractor</span>
        <div class="vendor-sub-text">Sub-Boxes Restrict access based on association with Contractor</div>
    <ul>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'CREATE_CONTRACTOR_CERTIFICATION', permissionTitle: 'Create', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'READ_CONTRACTOR_CERTIFICATION', permissionTitle: 'Read ', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'UPDATE_CONTRACTOR_CERTIFICATION', permissionTitle: 'Update', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'RENEW_CONTRACTOR_CERTIFICATION', permissionTitle: 'Renew', role: role]"/>
        <g:render template="/securityRole/contractorPermissionWithRoleCheckbox" model="[permission: 'DELETE_CONTRACTOR_CERTIFICATION', permissionTitle: 'Delete', role: role]"/>
    </ul>
    <div class="clr"></div>
</div>
