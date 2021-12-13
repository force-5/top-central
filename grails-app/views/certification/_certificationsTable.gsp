<table>
    <thead>
    <tr>
        <g:sortableColumn property="name" title="Name" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="description" title="Description" defaultOrder="desc"
                          params="[max: max, offset: offset]"/>
        <g:sortableColumn property="period" title="Period" defaultOrder="desc" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="periodUnit" title="Period Unit" defaultOrder="desc"
                          params="[max: max, offset: offset]"/>
        <g:sortableColumn property="trainingRequired" title="Training Required" defaultOrder="desc"
                          params="[max: max, offset: offset]"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${certificationInstanceList}" status="i" var="certificationInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td>
                <g:link action="show" id="${certificationInstance.id}">
                    ${fieldValue(bean: certificationInstance, field: 'name')}
                </g:link>
            </td>

            <td>
                <g:link action="show" id="${certificationInstance.id}">

                    ${fieldValue(bean: certificationInstance, field: 'description')}
                </g:link>
            </td>

            <td>
                <g:link action="show" id="${certificationInstance.id}">

                    ${fieldValue(bean: certificationInstance, field: 'period')}
                </g:link>
            </td>

            <td>
                <g:link action="show" id="${certificationInstance.id}">

                    ${fieldValue(bean: certificationInstance, field: 'periodUnit')}
                </g:link>
            </td>

            <td>
                <g:link action="show" id="${certificationInstance.id}">
                    ${certificationInstance.trainingRequired ? 'Yes' : 'No'}
                </g:link>
            </td>

        </tr>
    </g:each>
    </tbody>
</table>
<g:if test="${certificationInstanceTotal}">
    <div class="paginateButtons">
        <g:paginate total="${certificationInstanceTotal}" offset="${offset}" max="${max}"
                    params="[order: order, sort: sort]"/>
    </div>
</g:if>

<g:render template="/shared/defaultListViewSizeScript" model="[controller: 'certification']"/>
