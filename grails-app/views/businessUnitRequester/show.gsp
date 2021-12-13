<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder; com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Show Business Unit Requester</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><g:link class="home" url="${resource(dir:'')}">Home</g:link></span>
            <span class="menuButton"><g:link class="list" action="list">Business Unit Requester List</g:link></span>
            <g:if test="${care.hasPermission(permission: Permission.CREATE_BUR)}">
                <span class="menuButton"><g:link class="create" action="create">New Business Unit Requester</g:link></span>
            </g:if>
            <g:else>
                <span class="menuButton"><a class="create-disabled">New Business Unit Requester</a></span>
            </g:else>
            <span class="menuButton"><g:link class="create" action="certification" id="${instance.id}">Certifications</g:link></span>
        </div>
        <div class="body">
            <h1>Show Business Unit Requester</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    <tr class="prop">
                        <td valign="top" class="name">Business Unit:</td>

                        <td valign="top" class="value">${fieldValue(bean: instance, field: 'unit')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">First Name:</td>

                        <td valign="top" class="value">${fieldValue(bean: instance, field: 'firstName')}</td>

                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Middle Name:</td>

                        <td valign="top" class="value">${fieldValue(bean: instance, field: 'middleName')}</td>

                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Last Name:</td>

                        <td valign="top" class="value">${fieldValue(bean: instance, field: 'lastName')}</td>

                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Phone:</td>

                        <td valign="top" class="value">${fieldValue(bean: instance, field: 'phone')}</td>

                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">SLID:</td>

                        <td valign="top" class="value">${fieldValue(bean: instance, field: 'slid')}</td>

                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Notes:</td>
                        <td valign="top" class="value">${fieldValue(bean: instance, field: 'notes')}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:if test="${ConfigurationHolder.config.isEmployeeEditable != 'false'}">
                    <g:form action="edit" id="${instance?.id}">
                        <g:if test="${care.hasPermission(permission: Permission.UPDATE_BUR)}">
                            <span class="button"><input type="submit" class="edit editBurLink" value="Edit"/></span>
                        </g:if>
                        <g:else>
                            <span class="button"><input type="button" class="edit" style="color:gray;" value="Edit"/></span>
                        </g:else>
                        <g:if test="${care.hasPermission(permission: Permission.DELETE_BUR)}">
                            <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                        </g:if>
                        <g:else>
                            <span class="button"><input type="button" class="delete" style="color:gray;" value="Delete"/></span>
                        </g:else>
                    </g:form>
                </g:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
