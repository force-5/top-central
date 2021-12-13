<div class="vendor">
    <span>Admin Locations - System Node</span>
    <ul>
        <g:render template="/securityRole/permissionCheckbox" model="[permission: 'CREATE_COMPANY_NODE', title: 'Create Company Node', role: role]"/>
        <g:render template="/securityRole/permissionCheckbox" model="[permission: 'CREATE_LOCATION', title: 'Create Location', role: role]"/>
        <g:render template="/securityRole/permissionCheckbox" model="[permission: 'READ_LOCATION', title: 'Read ', role: role]"/>
        <g:render template="/securityRole/permissionCheckbox" model="[permission: 'UPDATE_LOCATION', title: 'Update Attributes', role: role]"/>
        <g:render template="/securityRole/permissionCheckbox" model="[permission: 'DELETE_LOCATION', title: 'Delete', role: role]"/>
    </ul>
    <div class="clr"></div>
</div>
