<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder; com.force5solutions.care.ldap.Permission;" %>
<html>
<head>
    <meta name="layout" content="contractor"/>
    <title>Employee List</title>
</head>
<body>
<br/>
<div id="wrapper">
    <g:if test="${flash.message}">
        <div align="center"><b>${flash.message}</b></div>
    </g:if>
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton">
                <g:link url="${resource(dir:'')}" class="home">Home</g:link>
                <span class="menuButton"><g:link class="create" controller="employee" action="addEmployee">Add Employee</g:link></span>
            </span>
            <g:if test="${(ConfigurationHolder.config.isEmployeeEditable=='true')}">
                <g:if test="${care.hasPermission(permission: Permission.CREATE_EMPLOYEE_PROFILE)}">
                    <span class="menuButton"><g:link class="create" controller="employee" action="create">New Employee</g:link></span>
                </g:if>
                <g:else>
                    <span class="menuButton"><a class="create-disabled">New Employee</a></span>
                </g:else>
            </g:if>
        </div>
        <div class="body">
            <h1>Employees
                <g:link class="filterbutton" name="viewButton" controller="employee" action="list">
                    <span>List View</span>
                </g:link>
            </h1>
            <div>
                <div style="margin-bottom: 5px;">
                    <br/>
                    <g:each in="${employeeHierarchyList}" var="worker" status='index'>
                        <g:link action="employeeOrgTreeView" params="[workerSlid: worker.slid]">${worker.name}</g:link>
                        <g:if test="${employeeHierarchyList[index+1]}">&gt;&nbsp;</g:if>
                    </g:each>
                </div>
            </div>
            <div class="list">
                <table>
                    <thead>
                    <tr>
                        <g:sortableColumn property="firstName" title="Name" defaultOrder="desc"/>
                        <g:sortableColumn property="title" title="Title" defaultOrder="desc"/>
                        <g:sortableColumn property="badgeNumber" title="Badge Number" defaultOrder="desc"/>
                        <g:sortableColumn property="slid" title="SLID" defaultOrder="desc"/>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${employees}" status="i" var="employee">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${employee.id}">${employee.name}</g:link></td>
                            <td><g:link action="show" id="${employee.id}">${employee.title}</g:link></td>
                            <td><g:link action="show" id="${employee.id}">${employee.badgeNumber}</g:link></td>
                            <td><g:link action="show" id="${employee.id}">${employee.person.slid}</g:link></td>
                            <td><g:render template="orgTreeMenu" model="[employee: employee]"/></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>