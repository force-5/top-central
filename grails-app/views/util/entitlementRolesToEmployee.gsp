<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="contractor"/>
    <title>TOP By Force 5 : Entitlement Role to Employees</title>
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
        <h1>Entitlement Role to Employees</h1>
        <div class="list">
            <table>
                <thead>
                <tr>
                    <td width="40%">Entitlement Role</td>
                    <td>Employees</td>
                </tr>
                </thead>
                <tbody>
                <g:each in="${entitlementRolesToEmployeeVOs}" status="i" var="entitlementRolesToEmployeeVO">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${entitlementRolesToEmployeeVO.entitlementRole}</td>
                        <td>
                            <ul>
                                <g:each in="${entitlementRolesToEmployeeVO.employeeNames}" var="employeeName">
                                    <li>${employeeName.name}</li>
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