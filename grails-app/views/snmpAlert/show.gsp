<%@ page import="com.force5solutions.care.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Show SNMP Alert</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><g:link class="home" url="${resource(dir:'')}">Home</g:link></span>
        </div>
        <div class="body">
            <h1>Show SNMP Alert</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="snmpAlert.id.label" default="Id"/></td>

                        <td valign="top" class="value">${fieldValue(bean: snmpAlert, field: "id")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="snmpAlert.exceptionName.label" default="Exception Name"/></td>

                        <td valign="top" class="value">${fieldValue(bean: snmpAlert, field: "exceptionMessage")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="snmpAlert.isSuccessful.label" default="Is Successful"/></td>

                        <td valign="top" class="value"><g:formatBoolean boolean="${snmpAlert?.isSuccessful}"/></td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="snmpAlert.dateCreated.label" default="Date Created"/></td>

                        <td valign="top" class="value"><g:formatDate date="${snmpAlert?.dateCreated}"/></td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="snmpAlert.errorString.label" default="Error"/></td>

                        <td valign="top" class="value">${fieldValue(bean: snmpAlert, field: "errorDescription")}</td>

                    </tr>

                    </tbody>
                </table>
            </div>
            <div class="buttons">
            </div>


        </div>
    </div>
</div>
</body>
</html>



































