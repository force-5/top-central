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
                <g:link url="${resource(dir: '')}" class="home">Home</g:link>
                <span class="menuButton"><g:link class="create" controller="employee"
                                                 action="addEmployee">Add Employee</g:link></span>
            </span>
            <g:if test="${(ConfigurationHolder.config.isEmployeeEditable == 'true')}">
                <g:if test="${care.hasPermission(permission: Permission.CREATE_EMPLOYEE_PROFILE)}">
                    <span class="menuButton"><g:link class="create" controller="employee"
                                                     action="create">New Employee</g:link></span>
                </g:if>
                <g:else>
                    <span class="menuButton"><a class="create-disabled">New Employee</a></span>
                </g:else>
            </g:if>
        </div>
        <div class="body">
            <h1>Employees
                <a href="#" class="filterbutton" id="filterButton"><span>Filter</span></a>
                <g:if test="${session.filterEmployeeCommand}">
                    <g:link class="filterbutton" controller="employee" action="showAllEmployee">
                        <span>Show All</span></g:link>
                </g:if>
                <g:link class="filterbutton" name="viewButton" controller="employee" action="employeeOrgTreeView">
                    <span>Org View</span>
                </g:link> &nbsp;
                <span style="float: right;color: #666666;">Show: <g:select
                        from="[10, 25, 50, 100, 'Unlimited']"
                        name="rowCount"
                        id="rowCount" value="${max}"/></span>
            </h1>

            <div class="list" id="list" style="padding-top: 10px;">
                <g:render template="employeesTable"
                          model="[employees: employees, employeesTotal: employeesTotal, offset: offset, max: max, order: order, sort: sort]"/>

            </div>
        </div>

        <div id="filterDialog" class="popupWindowContractorFilter">
        </div>

    </div>
</div>
</body>
</html>
