<table>
    <thead>
    <tr>

        <g:sortableColumn property="name" title="Name" params="[max: max, offset: offset]"/>

        <g:sortableColumn property="displayValue" title="Display Value" params="[max: max, offset: offset]"/>

        <g:sortableColumn property="value" title="Value" params="[max: max, offset: offset]"/>

    </tr>
    </thead>
    <tbody>
    <g:each in="${customTagList}" status="i" var="customTagInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td><g:link action="show"
                        id="${customTagInstance.id}">${fieldValue(bean: customTagInstance, field: 'name')}</g:link></td>

            <td class="breakWord"><g:link action="show"
                        id="${customTagInstance.id}">${fieldValue(bean: customTagInstance, field: 'displayValue')}</g:link></td>

            <td class="breakWord"><g:link action="show"
                        id="${customTagInstance.id}">${fieldValue(bean: customTagInstance, field: 'value')}</g:link></td>

        </tr>
    </g:each>
    </tbody>
</table>

<g:if test="${customTagTotal}">
    <div class="paginateButtons">
        <g:paginate total="${customTagTotal}" offset="${offset}" max="${max}" params="[order: order, sort: sort]"/>
    </div>
</g:if>

<g:render template="/shared/defaultListViewSizeScript" model="[controller: 'customTag']"/>
