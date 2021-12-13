<table>
    <thead>
    <tr>
        <g:sortableColumn property="name" title="${message(code: 'businessUnit.name.label', default: 'Name')}" params="[max: max, offset: offset]"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${businessUnitInstanceList}" status="i" var="businessUnitInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show"
                        id="${businessUnitInstance.id}">${fieldValue(bean: businessUnitInstance, field: "name")}</g:link></td>
        </tr>
    </g:each>
    </tbody>
</table>
<g:if test="${businessUnitInstanceTotal}">
    <div class="paginateButtons">
        <g:paginate total="${businessUnitInstanceTotal}" offset="${offset}" max="${max}" params="[order: order, sort: sort]"/>
    </div>
</g:if>

<g:render template="/shared/defaultListViewSizeScript" model="[controller: 'businessUnit']"/>
