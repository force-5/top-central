<%@ page import="com.force5solutions.care.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Create Entitlement Role Access Status</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">EntitlementRoleAccessStatus List</g:link></span>
        </div>
        <div class="body">
            <h1>Create EntitlementRoleAccessStatus</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${entitlementRoleAccessStatusInstance}">
                <div class="errors">
                    <g:renderErrors bean="${entitlementRoleAccessStatusInstance}" as="list"/>
                </div>
            </g:hasErrors>
            <g:form action="save" method="post">
                <div class="dialog">
                    <table>
                        <tbody>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="name">Name:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: entitlementRoleAccessStatusInstance, field: 'name', 'errors')}">
                                <input type="text" id="name" name="name" value="${fieldValue(bean: entitlementRoleAccessStatusInstance, field: 'name')}"/>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create"/></span>
                </div>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>
