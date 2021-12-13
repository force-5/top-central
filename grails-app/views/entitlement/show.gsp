<%@ page import="com.force5solutions.care.ldap.Permission;" %>
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
    <g:render template="/permission/listButton" model="[permission: Permission.READ_ENTITLEMENT, label: 'Entitlement List']"/>
    <g:render template="/permission/createButton" model="[permission: Permission.CREATE_ENTITLEMENT, label: 'New Entitlement']"/>
</div>
<div class="body">
    <h1>Entitlement List</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
        <table>
            <tbody>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="entitlement.alias.label" default="alias"/></td>

                <td valign="top" class="value">${fieldValue(bean: entitlement, field: "alias")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="entitlement.alias.label" default="Alias"/></td>

                <td valign="top" class="value">${fieldValue(bean: entitlement, field: "alias")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="entitlement.status.label" default="Status"/></td>

                <td valign="top" class="value">${entitlement?.status?.encodeAsHTML()}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="entitlement.origin.label" default="Origin"/></td>

                <td valign="top" class="value"><g:link controller="origin" action="show" id="${entitlement?.origin?.id}">${entitlement?.origin?.encodeAsHTML()}</g:link></td>

            </tr>


            <tr class="prop">
                <td valign="top" class="name"><g:message code="entitlement.type.label" default="Entitlement Policy"/></td>

                <td valign="top" class="value"><g:link controller="entitlementPolicy" action="show" id="${entitlement?.type?.id}">${entitlement?.type?.encodeAsHTML()}</g:link></td>

            </tr>
            <g:if test="${entitlement?.customPropertyValues}">
                <g:each in="${entitlement?.customPropertyValues}" var="customPropertyValue">
                    <tr>
                        <td valign="top" class="name">${customPropertyValue.customProperty.name}</td>

                        <td valign="top" class="value">${customPropertyValue.value}</td>
                    </tr>
                </g:each>
            </g:if>
            <tr class="prop">
                <td valign="top" class="name"><g:message code="entitlement.standards.label" default="Standards"/></td>

                <td valign="top" style="text-align: left;" class="value">${entitlement?.type?.standards?.join(', ')}</td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="entitlement.notes.label" default="Notes"/></td>

                <td valign="top" class="value">${fieldValue(bean: entitlement, field: "notes")}</td>

            </tr>

            </tbody>
        </table>
    </div>
    <div class="buttons">
        <g:form method="post">
            <g:hiddenField name="id" value="${entitlement?.id}"/>
            <g:render template="/permission/editButton" model="[permission: Permission.UPDATE_ENTITLEMENT]"/>
            <g:render template="/permission/deleteButton" model="[permission: Permission.DELETE_ENTITLEMENT]"/>
        </g:form>
    </div>

    %{--<care:entitlementHistory entitlementId="${entitlement.id}"/>--}%
</div>
</body>
</html>
