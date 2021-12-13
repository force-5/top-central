<%@ page import="com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Show Business Unit</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Business Units</g:link></span>
            <g:if test="${care.hasPermission(permission: Permission.CREATE_BUSINESS_UNIT)}">
                <span class="menuButton"><g:link class="create" action="create">New Business Unit</g:link></span>
            </g:if>
            <g:else>
                <span class="menuButton"><a class="create-disabled createBusinessLink">New Business Unit</a></span>
            </g:else>
        </div>
        <div class="body">
            <h1>Show Business Unit</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="businessUnit.name.label" default="Name"/></td>
                        <td valign="top" class="value">${fieldValue(bean: businessUnitInstance, field: "name")}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${businessUnitInstance?.id}"/>
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
