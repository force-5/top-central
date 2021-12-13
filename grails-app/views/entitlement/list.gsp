<%@ page import="com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor" />
    <g:set var="entityName" value="${message(code: 'entitlement.label', default: 'Entitlement')}"/>
    <title>Entitlement List</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <g:render template="/permission/createButton" model="[permission: Permission.CREATE_ENTITLEMENT, label: 'New Entitlement']"/>
</div>
<div class="body">
    <h1>Entitlement List
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
        <table>
            <thead>
            <tr>
                %{--<g:sortableColumn property="name" title="Name"/>--}%
                <g:sortableColumn property="id" title="ID"/>
                <g:sortableColumn property="status" title="Status"/>
                <g:sortableColumn property="origin" title="Origin"/>
                <g:sortableColumn property="entitlementPolicy" title="Entitlement Policy"/>
            </tr>
            </thead>
            <tbody>
            <g:each in="${entitlementList}" status="i" var="entitlement">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    %{--<td><g:link action="show" id="${entitlement.id}">${fieldValue(bean: entitlement, field: "name")}</g:link></td>--}%
                    <td><g:link action="show" id="${entitlement.id}">${fieldValue(bean: entitlement, field: "id")}</g:link></td>
                    <td><g:link action="show" id="${entitlement.id}">${fieldValue(bean: entitlement, field: "status")}</g:link></td>
                    <td><g:link action="show" id="${entitlement.id}">${fieldValue(bean: entitlement, field: "origin")}</g:link></td>
                    <td><g:link action="show" id="${entitlement.id}">${fieldValue(bean: entitlement, field: "type")}</g:link></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${entitlementTotal}"/>
    </div>
</div>
</body>
</html>
