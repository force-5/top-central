<%@ page import="com.force5solutions.care.ldap.Permission;" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor" />
    <g:set var="entityName" value="${message(code: 'entitlementPolicy.label', default: 'Entitlement Policy')}"/>
    <title>Entitlement Policy List</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <g:render template="/permission/createButton" model="[permission: Permission.CREATE_ENTITLEMENT_POLICY, label: 'New Entitlement Policy']" />
    <span class="menuButton"><a class="create" href="${createLink(action:'list')}">Approved Entitlement Policies</a></span>
</div>
<div class="body">
    <h1>Entitlement Policy List</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div id="list" class="list">
        <table>
            <thead>
            <tr>
                <g:sortableColumn property="name" title="${message(code: 'entitlementPolicy.name.label', default: 'Names')}"/>
                <g:sortableColumn property="name" title="${message(code: 'entitlementPolicy.standards.label', default: 'Standards')}"/>
                <g:sortableColumn property="name" title="${message(code: 'entitlementPolicy.customProperties.label', default: 'Custom Properties')}"/>

            </tr>
            </thead>
            <tbody>
            <g:each in="${entitlementPolicyList}" status="i" var="entitlementPolicy">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td><g:link action="showUnapprovedChanges" id="${entitlementPolicy.id}">${fieldValue(bean: entitlementPolicy, field: "name")}</g:link></td>
                    <td><g:link action="showUnapprovedChanges" id="${entitlementPolicy.id}">${entitlementPolicy.standards.join(", ")}</g:link></td>
                    <td><g:link action="showUnapprovedChanges" id="${entitlementPolicy.id}">${entitlementPolicy.customProperties*.name.join(", ")}</g:link></td>
                </tr>
            </g:each>
            </tbody>
        </table>

    </div>
    <div class="paginateButtons">
        <g:paginate total="${entitlementPolicyList.size()}"/>
    </div>
</div>
</body>
</html>
