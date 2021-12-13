<div id="breadcrumb" class="breadcrumb">
    <g:each var="p" in="${breadcrumbs}">
        <g:remoteLink
                controller="location"
                action="getLocationTree"
                id="${p.id}"
                rel="${p.id}"
                update="container_id"
                menuLevel="${p.locationType.type.replaceAll(' ', '')}">
            <span>${p}&gt;</span>
        </g:remoteLink>
    </g:each>
</div>
<br/>
<ul id="children" style="margin-left:20px;">
    <g:if test="${location?.isBusinessUnit()}">
        <g:each var="entitlementRole" in="${location.entitlementRoles}">
            <li>
                <g:link controller="entitlementRole" action="show" id="${entitlementRole.id}">${entitlementRole.name}</g:link>
            </li>
        </g:each>
    </g:if>
    <g:else>
        <g:each var="p" in="${location.childLocations.sort{it.name}}">
            <li>
                <g:if test="${(p.childLocations.size()<1 && !p.entitlementRoles)}">
                    <a rel="${p.id}" menuLevel="${p.locationType.type.replaceAll(' ', '')}">
                        <span>${p}</span>
                    </a>
                </g:if>
                <g:else>
                    <g:remoteLink
                            controller="location"
                            action="getLocationTree"
                            id="${p.id}"
                            rel="${p.id}"
                            update="container_id"
                            menuLevel="${p.locationType.type.replaceAll(' ', '')}">
                        <span>${p}</span>
                    </g:remoteLink>
                </g:else>
            </li>
        </g:each>
    </g:else>

</ul>

<script type="text/javascript">
    jQuery("a[menuLevel]").each(function() {
        var menuLevel = jQuery(this).attr('menuLevel');
        jQuery(this).contextMenu({
            menu: 'myMenu' + menuLevel
        }, function(locationTypeId, parentId, pos) {
            processContextMenu(locationTypeId, parentId);
        });
    });

    function processContextMenu(locationTypeId, parentId) {
        var selectedLocation = jQuery(parentId).attr('rel');
        if (locationTypeId == -1) {
            showLocation(selectedLocation, "${createLink(controller:'location', action:'showLocationWithId')}");
        } else if (locationTypeId == -2) {
            editLocation(selectedLocation, "${createLink(controller:'location', action:'editLocationWithId')}");
        } else if (locationTypeId == -3) {
            if (confirm('Are you sure you wish to remove this location')) {
                deleteLocation(selectedLocation, "${createLink(controller:'location', action:'deleteLocationWithId')}");
            }
        } else {
            createLocation(locationTypeId, selectedLocation, "${createLink(controller:'location', action:'createLocationWithId')}");
        }
    }

</script>
