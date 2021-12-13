<table>
    <thead>
    <tr>
        <g:sortableColumn property="companyName" title="Company Name" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="addressLine1" title="Address Line 1" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="addressLine2" title="Address Line 2" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="city" title="City" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="state" title="State" defaultOrder="desc" params="[max: max, offset: offset]"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${vendorInstanceList}" status="i" var="vendorInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td>
                <g:link action="show" id="${vendorInstance.id}">
                    ${fieldValue(bean: vendorInstance, field: 'companyName')}
                </g:link>
            </td>
            <td>
                <g:link action="show" id="${vendorInstance.id}">
                    ${fieldValue(bean: vendorInstance, field: 'addressLine1')}
                </g:link>
            </td>
            <td>
                <g:link action="show" id="${vendorInstance.id}">
                    ${fieldValue(bean: vendorInstance, field: 'addressLine2')}
                </g:link>
            </td>
            <td>
                <g:link action="show" id="${vendorInstance.id}">
                    ${fieldValue(bean: vendorInstance, field: 'city')}
                </g:link>
            </td>
            <td><g:link action="show" id="${vendorInstance.id}">
                ${stateMap.get(vendorInstance?.state)}</g:link></td>
        </tr>
    </g:each>
    </tbody>
</table>

<g:if test="${vendorInstanceTotal}">
    <div class="paginateButtons">
        <g:paginate total="${vendorInstanceTotal}" offset="${offset}" max="${max}" params="[order: order, sort: sort]"/>
    </div>
</g:if>

<g:render template="/shared/defaultListViewSizeScript" model="[controller: 'vendor']"/>