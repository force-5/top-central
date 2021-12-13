<table>
    <thead>
    <tr>
        <g:sortableColumn property="firstName" title="Name" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="title" title="Title" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="badgeNumber" title="Badge Number" defaultOrder="desc"
                          params="[max: max, offset: offset]"/>
        <g:sortableColumn property="slid" title="SLID" defaultOrder="desc" params="[max: max, offset: offset]"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${employees}" status="i" var="employee">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show" id="${employee.id}">${employee.name}</g:link></td>
            <td><g:link action="show" id="${employee.id}">${employee.title}</g:link></td>
            <td><g:link action="show" id="${employee.id}">${employee.badgeNumber}</g:link></td>
            <td><g:link action="show" id="${employee.id}">${employee.person.slid}</g:link></td>
        </tr>
    </g:each>
    </tbody>
</table>

<g:if test="${employeesTotal}">
    <div class="paginateButtons">
        <g:paginate total="${employeesTotal}" offset="${offset}" max="${max}" params="[order: order, sort: sort]"/>
    </div>
</g:if>

<script type="text/javascript">
    jQuery(document).ready(function () {
        jQuery('#filterButton').click(function () {
            jQuery.post("${createLink(controller:'employee', action:'filterDialog')}",
                    { ajax:'true'}, function (htmlText) {
                        jQuery('#filterDialog').html(htmlText);
                    });
            showModalDialog('filterDialog', true);
        });
    });
</script>
<g:render template="/shared/defaultListViewSizeScript" model="[controller: 'employee']"/>
