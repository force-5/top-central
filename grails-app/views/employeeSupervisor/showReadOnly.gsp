<%@ page import="com.force5solutions.care.*" %>
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
                        <td valign="top" class="name">First Name:</td>

                        <td valign="top" class="value">${fieldValue(bean: employeeSupervisor, field: 'firstName')}</td>

                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Last Name:</td>

                        <td valign="top" class="value">${fieldValue(bean: employeeSupervisor, field: 'lastName')}</td>

                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">Middle Name:</td>

                        <td valign="top" class="value">${fieldValue(bean: employeeSupervisor, field: 'middleName')}</td>

                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">SLID:</td>

                        <td valign="top" class="value">${fieldValue(bean: employeeSupervisor, field: 'slid')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">Phone:</td>

                        <td valign="top" class="value">${fieldValue(bean: employeeSupervisor, field: 'phone')}</td>

                    </tr>


                    <tr class="prop">
                        <td valign="top" class="name">Notes:</td>

                        <td valign="top" class="value">${fieldValue(bean: employeeSupervisor, field: 'notes')}</td>

                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
