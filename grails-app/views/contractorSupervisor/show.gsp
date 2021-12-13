<%@ page import="com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Show Supervisor</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><g:link class="home" url="${resource(dir:'')}">Home</g:link></span>
            <span class="menuButton"><g:link class="list" action="list">Supervisor List</g:link></span>
            <g:if test="${care.hasPermission(permission: Permission.CREATE_CONTRACTOR_SUP)}">
                <span class="menuButton"><g:link class="create" action="create">New Supervisor</g:link></span>
            </g:if>
            <g:else>
                <span class="menuButton"><a class="create-disabled">New Supervisor</a></span>
            </g:else>
        </div>
        <div class="body">
            <h1>Show Supervisor</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                        <tr class="prop">
                            <td valign="top" class="name">Vendor:</td>

                            <td valign="top" class="value">${fieldValue(bean: supervisor, field: 'vendorName')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">First Name:</td>

                            <td valign="top" class="value">${fieldValue(bean: supervisor, field: 'firstName')}</td>

                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Last Name:</td>

                            <td valign="top" class="value">${fieldValue(bean: supervisor, field: 'lastName')}</td>

                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Middle Name:</td>

                            <td valign="top" class="value">${fieldValue(bean: supervisor, field: 'middleName')}</td>

                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Email:</td>

                            <td valign="top" class="value">${fieldValue(bean: supervisor, field: 'email')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Phone:</td>

                            <td valign="top" class="value">${fieldValue(bean: supervisor, field: 'phone')}</td>

                        </tr>


                        <tr class="prop">
                            <td valign="top" class="name">Notes:</td>

                            <td valign="top" class="value">${fieldValue(bean: supervisor, field: 'notes')}</td>

                        </tr>

                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form action="edit" id="${supervisor?.id}">
                    <g:if test="${care.hasPermission(permission: Permission.UPDATE_CONTRACTOR_SUP)}">
                        <span class="button"><input type="submit" class="edit editSupervisorLink" value="Edit"/></span>
                    </g:if>
                    <g:else>
                        <span class="button"><input type="button" class="edit" style="color:gray;" value="Edit"/></span>
                    </g:else>
                    <g:if test="${care.hasPermission(permission: Permission.DELETE_CONTRACTOR_SUP)}">
                        <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                    </g:if>
                    <g:else>
                        <span class="button"><input type="button" class="delete" style="color:gray;" value="Delete"/></span>
                    </g:else>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
