<table>
    <thead>
    <tr>
        <g:sortableColumn property="unit" title="Business Unit" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="firstName" title="First Name" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="middleName" title="Middle Name" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="lastName" title="Last Name" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="phone" title="Phone" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="slid" title="SLID" defaultOrder="desc" params="[max: max, offset: offset]"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${burInstanceList}" status="i" var="instance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td>
                <g:link action="show" id="${instance.id}">
                    ${fieldValue(bean: instance, field: 'unit')}
                </g:link>
            </td>
            <td>
                <g:link action="show" id="${instance.id}">
                    ${fieldValue(bean: instance, field: 'firstName')}
                </g:link>
            </td>
            <td>
                <g:link action="show" id="${instance.id}">
                    ${fieldValue(bean: instance, field: 'middleName')}
                </g:link>
            </td>
            <td>
                <g:link action="show" id="${instance.id}">
                    ${fieldValue(bean: instance, field: 'lastName')}
                </g:link>
            </td>
            <td>
                <g:link action="show" id="${instance.id}">
                    ${fieldValue(bean: instance, field: 'phone')}
                </g:link>
            </td>
            <td>
                <g:link action="show" id="${instance.id}">
                    ${fieldValue(bean: instance, field: 'slid')}
                </g:link>
            </td>
        </tr>
    </g:each>
    </tbody>
</table>

<g:if test="${burInstanceTotal}">
    <div class="paginateButtons">
        <g:paginate total="${burInstanceTotal}" offset="${offset}" max="${max}"  params="[order: order, sort: sort]"/>
    </div>
</g:if>

<g:render template="/shared/defaultListViewSizeScript" model="[controller: 'businessUnitRequester']"/>