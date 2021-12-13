<table>
    <thead>
    <tr>
        <g:sortableColumn property="primeVendor" title="Vendor" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="workerNumber" title="Contractor Number" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="lastName" title="Name" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="badgeNumber" title="Badge Number" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="slid" title="SLID" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <th class="sortable"><strong>Status</strong></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${contractorInstanceList}" status="i" var="contractor">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show"
                        id="${contractor.id}">${fieldValue(bean: contractor, field: 'primeVendor')}</g:link></td>
            <td><g:link action="show"
                        id="${contractor.id}">${fieldValue(bean: contractor, field: 'workerNumber')}</g:link></td>
            <td><g:link action="show"
                        id="${contractor.id}">${fieldValue(bean: contractor, field: 'person')}</g:link></td>
            <td><g:link action="show"
                        id="${contractor.id}">${fieldValue(bean: contractor, field: 'badgeNumber')}</g:link></td>
            <td><g:link action="show" id="${contractor.id}">${fieldValue(bean: contractor, field: 'slid')}</g:link></td>
            <td><care:recentStatusWithoutDate contractorId="${contractor?.id}"/></td>
        </tr>
    </g:each>
    </tbody>
</table>

<g:if test="${contractorInstanceTotal}">
    <div class="paginateButtons">
        <g:paginate total="${contractorInstanceTotal}" offset="${offset}" max="${max}" params="[order: order, sort: sort]"/>
    </div>
</g:if>
<script type="text/javascript">
    jQuery(document).ready(function () {
        jQuery('#filterButton').click(function () {
            jQuery.post("${createLink(controller:'contractor', action:'filterDialog')}",
                    { ajax:'true'}, function (htmlText) {
                        jQuery('#filterDialog').html(htmlText);
                    });
            showModalDialog('filterDialog', true);
        });
    });
</script>
<g:render template="/shared/defaultListViewSizeScript" model="[controller: 'contractor']"/>
