<%@ page import="com.force5solutions.care.cc.EntitlementRoleAccessStatus" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Show Entitlement Role Access Status</title>

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
            <h1>Show Entitlement Role Access Status</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    <tr class="prop">
                        <td valign="top" class="name">Id:</td>

                        <td valign="top" class="value">${fieldValue(bean: entitlementRoleAccessStatusInstance, field: 'id')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">Name:</td>

                        <td valign="top" class="value">${fieldValue(bean: entitlementRoleAccessStatusInstance, field: 'name')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">Next Possible Statuses:</td>

                        <td valign="top" style="text-align:left;" class="value">
                            <ul>
                                <g:each var="n" in="${entitlementRoleAccessStatusInstance.nextPossibleStatuses}">
                                    <li><a href="#">${n} [${n.role}]</a></li>
                                </g:each>
                            </ul>
                        </td>

                    </tr>

                    </tbody>

                </table>
            </div>
            <div class="buttons">
                <g:form action="edit" id="${entitlementRoleAccessStatusInstance?.id}">
                    <span class="button"><input type="submit" class="edit" value="Edit"/></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                </g:form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
