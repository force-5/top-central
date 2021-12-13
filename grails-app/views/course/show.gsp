<%@ page import="com.force5solutions.care.ldap.Permission; com.force5solutions.care.cc.Course" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}"/>
    <title>Show Course</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></span>
    <g:if test="${care.hasPermission(permission: Permission.CREATE_COURSE)}">
        <span class="menuButton"><g:link class="create" action="create">New Course</g:link></span>
    </g:if>
    <g:else>
        <span class="menuButton"><a class="create-disabled">New Course</a></span>
    </g:else>
</div>
<div class="body">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">Course ${courseInstance.name} created</div>
    </g:if>
    <div class="dialog">
        <table>
            <tbody>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="course.name.label" default="Name"/></td>

                <td valign="top" class="value">${fieldValue(bean: courseInstance, field: "name")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="course.number.label" default="Number"/></td>

                <td valign="top" class="value">${fieldValue(bean: courseInstance, field: "number")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="course.startDate.label" default="Start Date"/></td>

                <td valign="top" class="value">${courseInstance?.startDate?.myFormat()}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="course.endDate.label" default="End Date"/></td>

                <td valign="top" class="value">${courseInstance?.endDate?.myFormat()}</td>

            </tr>

            </tbody>
        </table>
    </div>
    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${courseInstance?.id}"/>
            <g:if test="${care.hasPermission(permission: Permission.UPDATE_COURSE)}">
                <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/></span>
            </g:if>
            <g:else>
                <span class="button"><input type="button" class="edit" style="color:gray;" value="Edit"/></span>
            </g:else>
            <g:if test="${care.hasPermission(permission: Permission.DELETE_COURSE)}">
                <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
            </g:if>
            <g:else>
                <span class="button"><input type="button" class="delete" style="color:gray;" value="Delete"/></span>
            </g:else>
        </g:form>
    </div>
</div>
</body>
</html>
