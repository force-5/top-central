<%@ page import="com.force5solutions.care.ldap.Permission;" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor" />
    <g:set var="entityName" value="${message(code: 'entitlementRole.label', default: 'Entitlement Role')}"/>
    <title>Entitlement Role List</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <g:render template="/permission/createButton" model="[permission: Permission.CREATE_ENTITLEMENT_ROLE, label: 'New Entitlement Role']"/>
</div>
<div class="body">
    <h1>Entitlement Role List</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
        <table>
            <thead>
            <tr>
                <g:sortableColumn property="name" title="${message(code: 'entitlementRole.name.label', default: 'Name')}"/>
                <g:sortableColumn property="status" title="${message(code: 'entitlementRole.status.label', default: 'Status')}"/>
                <g:sortableColumn property="origin" title="${message(code: 'entitlementRole.origin.label', default: 'Origin')}"/>
            </tr>
            </thead>
            <tbody>
            <g:each in="${entitlementRoleList}" status="i" var="entitlementRole">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td><g:link action="show" id="${entitlementRole.id}">${fieldValue(bean: entitlementRole, field: "name")}</g:link></td>
                    <td><g:link action="show" id="${entitlementRole.id}">${fieldValue(bean: entitlementRole, field: "status")}</g:link></td>
                    <td><g:link action="show" id="${entitlementRole.id}">${fieldValue(bean: entitlementRole, field: "origin")}</g:link></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${entitlementRoleTotal}"/>
    </div>
</div>
</body>
</html>
