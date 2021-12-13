<%@ page import="com.force5solutions.care.ldap.Permission; com.force5solutions.care.cc.CcEntitlementRole" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <g:set var="entityName" value="${message(code: 'entitlementRole.label', default: 'Entitlement Role')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <g:render template="/permission/listButton" model="[permission: Permission.READ_ENTITLEMENT_ROLE, label: 'Entitlement Role List']"/>
</div>
<div class="body">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:render template="/shared/errors"  model="[instance: entitlementRole]"/>
    <g:form action="save" method="post">
        <div class="dialog">
            <g:render template="/entitlementRole/entitlementRoleForm" model="[entitlementRole: entitlementRole, statuses: statuses]"/>
        </div>
        <div class="buttons">
            <span class="button"><g:submitButton name="create" class="save"
                    value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
        </div>
    </g:form>
    <div class="requiredIndicator">
        &nbsp;<span style="color:red;">*</span><g:message code="required.field.text"/></div>
</div>
</body>
</html>
