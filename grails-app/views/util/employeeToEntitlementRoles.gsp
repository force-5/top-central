<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="contractor"/>
    <title>TOP By Force 5 : Worker To Entitlement Roles</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
</div>
<div id="wrapper">
    <g:if test="${flash.message}">
        <div align="center"><b>${flash.message}</b></div>
    </g:if>
</div>
<br/>

<div id="right-panel">
    <div class="nav">
        <span class="menuButton">
            <g:link url="${resource(dir:'')}" class="home">Home</g:link>
        </span>
    </div>
    <div class="body">
        <h1>Employee to Entitlement Role</h1>
        <div class="list">
            <table>
                <thead>
                <tr>
                    <td width="40%">Employee Name</td>
                    <td>Entitlement Roles</td>
                </tr>
                </thead>
                <tbody>
                <g:each in="${employeeToEntitlementRolesVOs}" status="i" var="employeeToEntitlementRolesVO">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${employeeToEntitlementRolesVO.employeeName}</td>
                        <td>
                            <ul>
                                <g:each in="${employeeToEntitlementRolesVO.entitlementRoles}" var="entitlementRole">
                                    <li>${entitlementRole.name}</li>
                                </g:each>
                            </ul>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>

        </div>
    </div>
</div>
</body>
</html>