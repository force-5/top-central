<table border="1px;">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="${message(code: 'centralWorkflowTaskTemplate.id.label', default: 'ID')}"
                          width="200px;" params="[max: max, offset: offset]"/>

        <g:sortableColumn property="messageTemplate"
                          title="${message(code: 'centralWorkflowTaskTemplate.messageTemplate.label', default: 'Message Template')}"
                          width="200px;" params="[max: max, offset: offset]"/>

        <g:sortableColumn property="period"
                          title="${message(code: 'centralWorkflowTaskTemplate.period.label', default: 'Period')}" params="[max: max, offset: offset]"/>

        <g:sortableColumn property="workflowTaskType"
                          title="${message(code: 'centralWorkflowTaskTemplate.workflowTaskType.label', default: 'Task Type')}"
                          width="20px;" params="[max: max, offset: offset]"/>

        <g:sortableColumn property="escalationTemplate"
                          title="${message(code: 'centralWorkflowTaskTemplate.escalationTemplate.label', default: 'Escalation Template')}" params="[max: max, offset: offset]"/>

    </tr>
    </thead>
    <tbody>
    <g:each in="${filteredTemplateList}" status="i" var="centralWorkflowTaskTemplateInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td><g:link action="show"
                        id="${centralWorkflowTaskTemplateInstance.id}">${centralWorkflowTaskTemplateInstance.id.replaceAll("_", " ")}</g:link></td>

            <td><g:link action="show"
                        id="${centralWorkflowTaskTemplateInstance.id}">${centralWorkflowTaskTemplateInstance?.messageTemplate?.name?.replaceAll("_", " ")}</g:link></td>

            <td><g:link action="show"
                        id="${centralWorkflowTaskTemplateInstance.id}">${centralWorkflowTaskTemplateInstance?.period ? centralWorkflowTaskTemplateInstance?.period + " " + centralWorkflowTaskTemplateInstance?.periodUnit : ""}</g:link></td>

            <td><g:link action="show"
                        id="${centralWorkflowTaskTemplateInstance.id}">${fieldValue(bean: centralWorkflowTaskTemplateInstance, field: "workflowTaskType")}</g:link></td>

            <td><g:link action="show"
                        id="${centralWorkflowTaskTemplateInstance.id}">${centralWorkflowTaskTemplateInstance?.escalationTemplate?.id?.replaceAll("_", " ")}</g:link></td>

        </tr>
    </g:each>
    </tbody>
</table>

<g:if test="${filteredTemplateListCount}">
    <div class="paginateButtons">
        <g:paginate total="${filteredTemplateListCount}" offset="${offset}" max="${max}" params="[order: order, sort: sort]"/>
    </div>
</g:if>

<g:render template="/shared/defaultListViewSizeScript" model="[controller: 'centralWorkflowTaskTemplate']"/>