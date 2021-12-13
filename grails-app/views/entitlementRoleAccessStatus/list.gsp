<%@ page import="com.force5solutions.care.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Entitlement Role Access Status List</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New EntitlementRoleAccessStatus</g:link></span>
        </div>
        <div class="body">
            <h1>EntitlementRoleAccessStatus List</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                    <tr>

                        <g:sortableColumn property="id" title="Id"/>

                        <g:sortableColumn property="name" title="Name"/>

                        <th>Next Possible Statuses</th>

                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${entitlementRoleAccessStatusInstanceList}" status="i" var="entitlementRoleAccessStatusInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                            <td><g:link action="show" id="${entitlementRoleAccessStatusInstance.id}">${fieldValue(bean: entitlementRoleAccessStatusInstance, field: 'id')}</g:link></td>

                            <td><g:link action="show" id="${entitlementRoleAccessStatusInstance.id}">${fieldValue(bean: entitlementRoleAccessStatusInstance, field: 'name')}</g:link></td>

                            <td><g:link action="show" id="${entitlementRoleAccessStatusInstance.id}">${fieldValue(bean: entitlementRoleAccessStatusInstance, field: 'nextPossibleStatuses')}</g:link></td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${entitlementRoleAccessStatusInstanceTotal}"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
