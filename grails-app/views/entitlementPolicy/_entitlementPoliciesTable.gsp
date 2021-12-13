<table>
    <thead>
    <tr>
        <g:sortableColumn property="name" title="${message(code: 'entitlementPolicy.name.label', default: 'Name')}" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="name"
                          title="${message(code: 'entitlementPolicy.standards.label', default: 'Standards')}" params="[max: max, offset: offset]"/>
        <g:sortableColumn property="name"
                          title="${message(code: 'entitlementPolicy.customProperties.label', default: 'Custom Properties')}" params="[max: max, offset: offset]"/>

    </tr>
    </thead>
    <tbody>
    <g:each in="${entitlementPolicyList}" status="i" var="entitlementPolicy">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><versionable:hasUnapprovedChanges object="${entitlementPolicy}"><span
                    class="asterisk">*</span></versionable:hasUnapprovedChanges>
                <g:link action="show"
                        id="${entitlementPolicy.id}">${fieldValue(bean: entitlementPolicy, field: "name")}</g:link></td>
            <td><g:link action="show"
                        id="${entitlementPolicy.id}">${entitlementPolicy.standards.join(", ")}</g:link></td>
            <td><g:link action="show"
                        id="${entitlementPolicy.id}">${entitlementPolicy.customProperties*.name.join(", ")}</g:link></td>
        </tr>
    </g:each>
    </tbody>
</table>
<g:if test="${entitlementPolicyTotal}">
    <div class="paginateButtons">
        <g:paginate total="${entitlementPolicyTotal}" offset="${offset}" max="${max}" params="[order: order, sort: sort]"/>
    </div>
</g:if>

<g:render template="/shared/defaultListViewSizeScript" model="[controller: 'entitlementPolicy']"/>
