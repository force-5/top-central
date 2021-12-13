<table>
    <thead>
    <tr>

        <g:sortableColumn property="name" title="${message(code: 'course.name.label', default: 'Name')}"
                          params="[max: max, offset: offset]"/>

        <g:sortableColumn property="number" title="${message(code: 'course.number.label', default: 'Number')}"
                          params="[max: max, offset: offset]"/>

        <g:sortableColumn property="startDate"
                          title="${message(code: 'course.startDate.label', default: 'Start Date')}"
                          params="[max: max, offset: offset]"/>

        <g:sortableColumn property="endDate" title="${message(code: 'course.endDate.label', default: 'End Date')}"
                          params="[max: max, offset: offset]"/>

    </tr>
    </thead>
    <tbody>
    <g:each in="${courseInstanceList}" status="i" var="courseInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td><g:link action="show"
                        id="${courseInstance.id}">${fieldValue(bean: courseInstance, field: "name")}</g:link></td>


            <td><g:link action="show"
                        id="${courseInstance.id}">${fieldValue(bean: courseInstance, field: "number")}</g:link></td>


            <td><g:link action="show"
                        id="${courseInstance.id}">${fieldValue(bean: courseInstance, field: "startDate")}</g:link></td>


            <td><g:link action="show"
                        id="${courseInstance.id}">${fieldValue(bean: courseInstance, field: "endDate")}</g:link></td>

        </tr>
    </g:each>
    </tbody>
</table>

<g:if test="${courseInstanceTotal}">
    <div class="paginateButtons">
        <g:paginate total="${courseInstanceTotal}" offset="${offset}" max="${max}" params="[order: order, sort: sort]"/>
    </div>
</g:if>

<g:render template="/shared/defaultListViewSizeScript" model="[controller: 'course']"/>
