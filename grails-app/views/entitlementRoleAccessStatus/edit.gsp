<%@ page import="com.force5solutions.care.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Edit Entitlement Role Access Status</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">EntitlementRoleAccessStatus List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New EntitlementRoleAccessStatus</g:link></span>
        </div>
        <div class="body">
            <h1>Edit EntitlementRoleAccessStatus</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${entitlementRoleAccessStatusInstance}">
                <div class="errors">
                    <g:renderErrors bean="${entitlementRoleAccessStatusInstance}" as="list"/>
                </div>
            </g:hasErrors>
            <g:form method="post">
                <input type="hidden" name="id" value="${entitlementRoleAccessStatusInstance?.id}"/>
                <input type="hidden" name="version" value="${entitlementRoleAccessStatusInstance?.version}"/>
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

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="nextPossibleStatuses">Next Possible Statuses:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: entitlementRoleAccessStatusInstance, field: 'nextPossibleStatuses', 'errors')}">

                                <ul>
                                    <g:each var="n" in="${entitlementRoleAccessStatusInstance?.nextPossibleStatuses}">
                                        <li><g:link controller="entitlementRoleAccessStatusConfig" action="show" id="${n.id}">${n?.encodeAsHTML()}</g:link></li>
                                    </g:each>
                                </ul>
                                <g:link controller="entitlementRoleAccessStatusConfig" params="['entitlementRoleAccessStatus.id':entitlementRoleAccessStatusInstance?.id]" action="create">Add EntitlementRoleAccessStatusConfig</g:link>

                            </td>
                        </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update"/></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                </div>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>
